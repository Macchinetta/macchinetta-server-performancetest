/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.b2;

import jp.co.ntt.atrs.app.a0.AuthenticationHelper;
import jp.co.ntt.atrs.app.b0.LineType;
import jp.co.ntt.atrs.app.b0.SelectFlightDto;
import jp.co.ntt.atrs.app.b0.SelectFlightForm;
import jp.co.ntt.atrs.app.b0.TicketHelper;
import jp.co.ntt.atrs.app.common.exception.BadRequestException;
import jp.co.ntt.atrs.domain.common.masterdata.BoardingClassProvider;
import jp.co.ntt.atrs.domain.common.masterdata.FareTypeProvider;
import jp.co.ntt.atrs.domain.common.masterdata.FlightMasterProvider;
import jp.co.ntt.atrs.domain.model.BoardingClass;
import jp.co.ntt.atrs.domain.model.FareType;
import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.FlightMaster;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.model.Passenger;
import jp.co.ntt.atrs.domain.model.Reservation;
import jp.co.ntt.atrs.domain.model.ReserveFlight;
import jp.co.ntt.atrs.domain.model.Route;
import jp.co.ntt.atrs.domain.service.a1.AtrsUserDetails;
import jp.co.ntt.atrs.domain.service.b0.TicketSharedService;
import jp.co.ntt.atrs.domain.service.b2.TicketReserveDto;
import jp.co.ntt.atrs.domain.service.b2.TicketReserveService;

import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

/**
 * チケット予約Helper。
 * @author NTT 電電三郎
 */
@Component
public class TicketReserveHelper {

    /**
     * Beanマッパー。
     */
    @Inject
    Mapper beanMapper;

    /**
     * 日付、時刻取得インターフェース。
     */
    @Inject
    JodaTimeDateFactory dateFactory;

    /**
     * 認証共通Helper。
     */
    @Inject
    AuthenticationHelper authenticationHelper;

    /**
     * チケット予約共通Helper。
     */
    @Inject
    TicketHelper ticketHelper;

    /**
     * チケット予約サービス。
     */
    @Inject
    TicketReserveService ticketReserveService;

    /**
     * チケット予約共通サービス。
     */
    @Inject
    TicketSharedService ticketSharedService;

    /**
     * 運賃種別情報提供クラス。
     */
    @Inject
    FareTypeProvider fareTypeProvider;

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
     * チケット情報の妥当性をチェックし、申し込み内容確認画面に表示するデータを生成する。
     * @param reservationForm 予約情報フォーム
     * @param flightList フライト情報リスト
     * @param userDetails ログイン情報を保持するオブジェクト
     * @return 申し込み内容確認画面出力用DTO
     */
    public ReserveConfirmOutputDto reserveConfirm(
            ReservationForm reservationForm, List<Flight> flightList,
            AtrsUserDetails userDetails) {

        // ログインの場合、予約者情報にログインユーザー情報を設定する。
        if (authenticationHelper.isAuthenticatedPrincipal(userDetails)) {
            addRepInfo(reservationForm, userDetails);
        }

        // 搭乗者情報一覧リストから、入力値が1つもない搭乗者情報を削除する。
        preparePassengerFormList(reservationForm.getPassengerFormList());

        // 業務ロジックチェックを行うために、予約情報を作成する。
        Reservation reservation = createReservationFromForm(reservationForm,
                flightList);

        // 予約情報の業務ロジックチェックを行う。
        ticketReserveService.validateReservation(reservation);

        // 予約チケットの合計金額を取得する
        int totalFare = calculateTotalFare(flightList, reservation);

        List<SelectFlightDto> selectFlightDtoList = createSelectFlightDtoList(flightList);

        // 画面出力DTOを作成する。
        ReserveConfirmOutputDto reserveConfirmOutputDto = new ReserveConfirmOutputDto();
        reserveConfirmOutputDto.setSelectFlightDtoList(selectFlightDtoList);
        reserveConfirmOutputDto.setTotalFare(totalFare);
        return reserveConfirmOutputDto;
    }

    /**
     * チケットを予約する。
     * @param reservationForm 予約情報フォーム
     * @param flightList フライト情報リスト
     * @param userDetails ログイン情報を保持するオブジェクト
     * @return チケット予約完了画面出力用DTO
     */
    public ReserveCompleteOutputDto reserve(ReservationForm reservationForm,
            List<Flight> flightList, AtrsUserDetails userDetails) {

        // ログインの場合、予約者情報にログインユーザー情報を設定する。
        if (authenticationHelper.isAuthenticatedPrincipal(userDetails)) {
            addRepInfo(reservationForm, userDetails);
        }

        // 予約情報を作成する。
        Reservation reservation = createReservationFromForm(reservationForm,
                flightList);

        // 予約情報の業務ロジックチェックを行う。
        ticketReserveService.validateReservation(reservation);

        reservation.setReserveDate(dateFactory.newDate());
        reservation.setTotalFare(calculateTotalFare(flightList, reservation));

        // 予約情報を登録する。
        TicketReserveDto ticketReserveDto = ticketReserveService
                .registerReservation(reservation);

        List<SelectFlightDto> selectFlightDtoList = createSelectFlightDtoList(flightList);

        // 出力DTOを作成する。
        ReserveCompleteOutputDto reserveCompleteOutputDto = new ReserveCompleteOutputDto();
        reserveCompleteOutputDto.setReserveInfo(ticketReserveDto);
        reserveCompleteOutputDto.setTotalFare(reservation.getTotalFare());
        reserveCompleteOutputDto.setReservationForm(reservationForm);
        reserveCompleteOutputDto.setSelectFlightDtoList(selectFlightDtoList);

        return reserveCompleteOutputDto;
    }

    /**
     * フライト情報リストから画面表示に使用する選択フライト情報DTOのリストを作成する。
     * @param flightList フライト情報リスト
     * @return 選択フライト情報DTOのリスト
     */
    public List<SelectFlightDto> createSelectFlightDtoList(
            List<Flight> flightList) {

        List<SelectFlightDto> selectFlightDtoList = new ArrayList<>();
        for (int i = 0; i < flightList.size(); i++) {
            Flight flight = flightList.get(i);
            FlightMaster flightMaster = flight.getFlightMaster();
            Route route = flightMaster.getRoute();
            FareType fareType = flight.getFareType();
            BoardingClass boardingClass = flight.getBoardingClass();

            SelectFlightDto selectFlight = new SelectFlightDto();
            selectFlight.setArrAirportCd(route.getArrivalAirport().getCode());
            selectFlight.setArrivalTime(flightMaster.getArrivalTime());
            selectFlight.setBoardingClassCd(boardingClass.getBoardingClassCd());
            selectFlight.setDepAirportCd(route.getDepartureAirport().getCode());
            selectFlight.setDepartureDate(flight.getDepartureDate());
            selectFlight.setDepartureTime(flightMaster.getDepartureTime());
            selectFlight.setFareTypeCd(fareType.getFareTypeCd());
            selectFlight.setFlightName(flightMaster.getFlightName());

            if (i == 0) {
                selectFlight.setLineType(LineType.OUTWARD);
            } else {
                selectFlight.setLineType(LineType.HOMEWARD);
            }

            int baseFare = ticketSharedService.calculateBasicFare(route
                    .getBasicFare(), boardingClass.getBoardingClassCd(), flight
                    .getDepartureDate());
            int fare = ticketSharedService.calculateFare(baseFare, fareType
                    .getDiscountRate());
            selectFlight.setFare(fare);

            selectFlightDtoList.add(selectFlight);
        }
        return selectFlightDtoList;
    }

    /**
     * 予約入力画面に表示用情報を設定する。
     * @param reservationForm 予約情報フォーム
     * @param userDetails ログイン情報を保持するオブジェクト
     */
    public void prepareReservationForm(ReservationForm reservationForm,
            AtrsUserDetails userDetails) {

        List<PassengerForm> passengerFormList = reservationForm
                .getPassengerFormList();

        if (authenticationHelper.isAuthenticatedPrincipal(userDetails)) {
            // ログインの場合、予約者情報にログインユーザー情報を設定する。
            addRepInfo(reservationForm, userDetails);

            // ログイン及び搭乗者情報未入力の場合、一番目の搭乗者情報に予約者情報を設定する。
            if (passengerFormList.size() == 0) {
                PassengerForm passengerForm = beanMapper.map(reservationForm,
                        PassengerForm.class);
                passengerFormList.add(passengerForm);
            }
        }
    }

    /**
     * 「復路の空席照会」フィールド表示フラグを取得する。
     * @param selectFlightDtoList 選択フライト情報リスト
     * @return 「復路の空席照会」フィールド表示フラグ
     */
    public boolean hasHomeward(List<SelectFlightDto> selectFlightDtoList) {
        // 「復路の空席照会」フィールド表示フラグのデフォルト値を設定。
        boolean hasHomeward = false;

        // 選択フライト情報リストが1件の場合は往路として処理する。
        if (selectFlightDtoList.size() == 1) {
            // 運賃種別が"往復運賃"または"特別往復運賃"であり、且つ、路線種別が往路の場合、「復路の空席照会」フィールド表示フラグをtrueに設定する。
            FareTypeCd fareTypeCd = selectFlightDtoList.get(0).getFareTypeCd();
            if ((FareTypeCd.RT == fareTypeCd || FareTypeCd.SRT == fareTypeCd)
                    && LineType.OUTWARD == selectFlightDtoList.get(0)
                            .getLineType()) {
                hasHomeward = true;
            }
        }
        return hasHomeward;
    }

    /**
     * 選択フライト情報フォームのリストからフライト情報リストを生成する。
     * @param selectFlightFormList 選択フライト情報フォームのリスト
     * @return フライト情報のリスト
     */
    public List<Flight> createFlightList(
            List<SelectFlightForm> selectFlightFormList) {
        List<Flight> flightList = ticketHelper
                .createFlightList(selectFlightFormList);
        // 改竄チェック
        try {
            ticketHelper.validateFlightList(flightList);
        } catch (BusinessException e) {
            throw new BadRequestException(e);
        }
        return flightList;
    }

    /**
     * 搭乗者情報フォームのリストから搭乗者情報のリストを作成する。
     * @param passengerFormList 搭乗者情報フォームのリスト
     * @return passengerList 搭乗者情報のリスト
     */
    private List<Passenger> createPassengerList(
            List<PassengerForm> passengerFormList) {
        List<Passenger> passengerList = new ArrayList<>();
        for (PassengerForm passengerForm : passengerFormList) {
            Passenger passenger = new Passenger();
            passenger.setFamilyName(passengerForm.getFamilyName());
            passenger.setGivenName(passengerForm.getGivenName());
            passenger.setAge(passengerForm.getAge());
            passenger.setGender(passengerForm.getGender());
            Member member = new Member();
            member.setCustomerNo(passengerForm.getCustomerNo());
            passenger.setMember(member);
            passengerList.add(passenger);
        }
        return passengerList;
    }

    /**
     * 予約情報オブジェクトを生成する。
     * @param flightList フライト情報のリスト
     * @param reservationForm 予約情報フォーム
     * @return reservation 予約情報
     */
    private Reservation createReservationFromForm(
            ReservationForm reservationForm, List<Flight> flightList) {

        // 搭乗者情報リストを作成する。
        List<Passenger> passengerList = createPassengerList(reservationForm
                .getPassengerFormList());

        // 予約フライト情報のリストを作成する。
        List<ReserveFlight> reserveFlightList = new ArrayList<>();
        for (Flight flight : flightList) {
            ReserveFlight reserveFlight = new ReserveFlight();
            reserveFlight.setFlight(flight);
            reserveFlight.setPassengerList(passengerList);
            reserveFlightList.add(reserveFlight);
        }

        // 予約情報を作成する。
        Reservation reservation = formToReservation(reservationForm);
        reservation.setReserveFlightList(reserveFlightList);

        return reservation;
    }

    /**
     * 予約情報を作成する。
     * <p>
     * 入力された予約情報フォームから予約代表者情報の入力値を取得し、予約情報を作成する。
     * </p>
     * @param reservationForm 予約情報フォーム
     * @return reservation 予約情報
     */
    private Reservation formToReservation(ReservationForm reservationForm) {
        Reservation reservation = new Reservation();
        beanMapper.map(reservationForm, reservation);
        String repTel = reservationForm.getRepTel1() + "-"
                + reservationForm.getRepTel2() + "-"
                + reservationForm.getRepTel3();
        reservation.setRepTel(repTel);
        return reservation;
    }

    /**
     * 予約チケットの合計金額を計算する。
     * @param flightList フライト情報のリスト
     * @param reservation 予約情報
     * @return 予約チケットの合計金額
     */
    private int calculateTotalFare(List<Flight> flightList,
            Reservation reservation) {
        ReserveFlight reserveFlight = reservation.getReserveFlightList().get(0);
        return ticketReserveService.calculateTotalFare(flightList,
                reserveFlight.getPassengerList());
    }

    /**
     * 予約情報フォームの予約代表者情報にログインユーザーの情報を設定する。
     * @param reservationForm 予約情報フォーム
     * @param userDetails ログイン情報を保持するオブジェクト
     */
    private void addRepInfo(ReservationForm reservationForm,
            AtrsUserDetails userDetails) {
        Member member = ticketReserveService.findMember(userDetails
                .getUsername());
        beanMapper.map(member, reservationForm);
        String[] tel = member.getTel().split("-");
        if (tel.length == 3) {
            reservationForm.setRepTel1(tel[0]);
            reservationForm.setRepTel2(tel[1]);
            reservationForm.setRepTel3(tel[2]);
        }
        reservationForm.setRepAge(calculateAge(member.getBirthday()));
    }

    /**
     * 搭乗者情報リストから、入力値が1つもない搭乗者情報を削除する。
     * @param passengerFormList 搭乗者情報リスト
     */
    private void preparePassengerFormList(List<PassengerForm> passengerFormList) {

        // 全未入力の搭乗者情報を削除する。
        Iterator<PassengerForm> iterator = passengerFormList.iterator();
        while (iterator.hasNext()) {
            PassengerForm passengerform = iterator.next();
            if (StringUtils.isEmpty(passengerform.getFamilyName())
                    && StringUtils.isEmpty(passengerform.getGivenName())
                    && passengerform.getAge() == null
                    && passengerform.getGender() == null
                    && StringUtils.isEmpty(passengerform.getCustomerNo())) {
                iterator.remove();
            }
        }
    }

    /**
     * 生年月日より年齢を計算する。
     * @param birthday 生年月日
     * @return 年齢
     */
    private Integer calculateAge(Date birthday) {
        DateTime today = dateFactory.newDateTime();
        DateTime birthdayDateTime = new DateTime(birthday);
        return Years.yearsBetween(birthdayDateTime, today).getYears();
    }

    // JMS性能試験用
    public ReserveCompleteOutputDto reserveJms(Member member,
            List<Flight> flightList) {
        PassengerForm passengerForm = new PassengerForm();
        passengerForm.setFamilyName(member.getKanaFamilyName());
        passengerForm.setGivenName(member.getKanaGivenName());
        passengerForm.setAge(calculateAge(member.getBirthday()));
        passengerForm.setGender(member.getGender());
        passengerForm.setCustomerNo(member.getCustomerNo());

        List<PassengerForm> passengerFormList = new ArrayList<PassengerForm>();
        passengerFormList.add(passengerForm);

        ReservationForm reservationForm = new ReservationForm();
        reservationForm.setRepFamilyName(member.getKanaFamilyName());
        reservationForm.setRepGivenName(member.getKanaGivenName());
        reservationForm.setRepAge(calculateAge(member.getBirthday()));
        reservationForm.setRepGender(member.getGender());
        reservationForm.setRepCustomerNo(member.getCustomerNo());
        String[] tel = member.getTel().split("-");
        if (tel.length == 3) {
            reservationForm.setRepTel1(tel[0]);
            reservationForm.setRepTel2(tel[1]);
            reservationForm.setRepTel3(tel[2]);
        }
        reservationForm.setRepMail(member.getMail());
        reservationForm.setPassengerFormList(passengerFormList);

        return reserve(reservationForm, flightList, null);
    }

}
