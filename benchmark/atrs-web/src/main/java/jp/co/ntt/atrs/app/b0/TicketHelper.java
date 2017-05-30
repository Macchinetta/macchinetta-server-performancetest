/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.b0;

import jp.co.ntt.atrs.app.common.exception.BadRequestException;
import jp.co.ntt.atrs.domain.common.masterdata.BoardingClassProvider;
import jp.co.ntt.atrs.domain.common.masterdata.FareTypeProvider;
import jp.co.ntt.atrs.domain.common.masterdata.FlightMasterProvider;
import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.service.b0.TicketSharedService;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * チケット予約共通Helper。
 * @author NTT 電電次郎
 */
@Component
public class TicketHelper {

    /**
     * 日付、時刻取得インタフェース。
     */
    @Inject
    JodaTimeDateFactory dateFactory;

    /**
     * チケット予約共通サービス。
     */
    @Inject
    TicketSharedService ticketSharedService;

    /**
     * 搭乗クラス情報提供クラス。
     */
    @Inject
    BoardingClassProvider boardingClassProvider;

    /**
     * フライト基本情報提供クラス。
     */
    @Inject
    FlightMasterProvider flightMasterProvider;

    /**
     * 運賃種別情報提供クラス。
     */
    @Inject
    FareTypeProvider fareTypeProvider;

    /**
     * 選択フライト情報フォームのリストからフライト情報リストを生成する。
     * @param selectFlightFormList 選択フライト情報フォームのリスト
     * @return フライト情報のリスト
     */
    public List<Flight> createFlightList(
            List<SelectFlightForm> selectFlightFormList) {

        List<Flight> flightList = new ArrayList<>();

        for (SelectFlightForm selectFlightForm : selectFlightFormList) {
            Flight flight = new Flight();
            flight.setDepartureDate(selectFlightForm.getDepartureDate());
            flight.setFlightMaster(flightMasterProvider
                    .getFlightMaster(selectFlightForm.getFlightName()));
            flight.setFareType(fareTypeProvider.getFareType(selectFlightForm
                    .getFareTypeCd()));
            flight.setBoardingClass(boardingClassProvider
                    .getBoardingClass(selectFlightForm.getBoardingClassCd()));
            flightList.add(flight);
        }

        return flightList;
    }

    /**
     * フライト情報のリストをチェックする。
     * <p>
     * 業務チェックエラーの場合、業務例外をスローする。 他のエラーの場合、不正リクエスト例外をスローする。
     * </p>
     * @param flightList フライト情報のリスト
     * @throws BusinessException 業務例外
     * @throws BadRequestException 不正リクエスト例外
     */
    public void validateFlightList(List<Flight> flightList) throws BusinessException, BadRequestException {

        // 改竄チェック
        validateFlightListForFalsification(flightList);

        // 業務ロジックチェック
        ticketSharedService.validateFlightList(flightList);
    }

    /**
     * フライト情報のリストの改ざんチェックを行う。
     * @param flightList フライト情報のリスト
     */
    private void validateFlightListForFalsification(List<Flight> flightList) {
        if (CollectionUtils.isEmpty(flightList)) {
            throw new BadRequestException("flightList is empty.");
        }
        try {
            for (Flight flight : flightList) {

                // フライト名存在チェック
                if (flight.getFlightMaster() == null) {
                    throw new BadRequestException("flight is null.");
                }

                // 搭乗日有効性チェック
                ticketSharedService.validateDepatureDate(flight
                        .getDepartureDate());

                // フライト情報存在チェック
                if (!ticketSharedService.existsFlight(flight)) {
                    throw new BadRequestException("flight is not exists."
                            + flight);
                }
            }

        } catch (BusinessException e) {
            throw new BadRequestException(e);
        }

        // 往復の場合、往路の改竄チェック
        if (flightList.size() == 2) {
            FareTypeCd outwardFareTypeCd = flightList.get(0).getFareType()
                    .getFareTypeCd();
            if (!FareTypeCd.RT.equals(outwardFareTypeCd)
                    && !FareTypeCd.SRT.equals(outwardFareTypeCd)) {
                throw new BadRequestException("fare Type of outward flight is invalid. fareTypeCd is "
                        + outwardFareTypeCd + ".");
            }
        }

    }

    /**
     * 指定された搭乗日（月）より、搭乗日（年）を返却する。
     * @param depMonth 搭乗日（月）
     * @return 搭乗日（年）
     */
    public int getDepYear(int depMonth) {
        DateTime sysDate = dateFactory.newDateTime();
        int depYear = sysDate.getYear();

        // 搭乗日（月）がシステム日付の月よりも値が小さい場合、一年加算
        if (sysDate.getMonthOfYear() > depMonth) {
            depYear++;
        }
        return depYear;
    }

}
