/*
 * Copyright 2014-2018 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package jp.co.ntt.atrs.app.b1;

import jp.co.ntt.atrs.app.b0.FlightSearchCriteriaForm;
import jp.co.ntt.atrs.app.b0.TicketHelper;
import jp.co.ntt.atrs.domain.common.masterdata.BoardingClassProvider;
import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.service.b0.TicketSharedService;
import jp.co.ntt.atrs.domain.service.b1.TicketSearchResultDto;
import jp.co.ntt.atrs.domain.service.b1.TicketSearchService;
import jp.co.ntt.atrs.domain.service.b1.TicketSearchCriteriaDto;

import org.dozer.Mapper;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;

import javax.inject.Inject;

/**
 * 空席照会Helper。
 * @author NTT 電電次郎
 */
@Component
public class TicketSearchHelper {

    /**
     * チケット予約共通Helper。
     */
    @Inject
    TicketHelper ticketHelper;

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
     * 空席照会サービス。
     */
    @Inject
    TicketSearchService ticketSearchService;

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
     * デフォルト出発空港コード。
     */
    @Value("${default.depAirportCd}")
    String defaultDepAirportCd;

    /**
     * デフォルト到着空港コード。
     */
    @Value("${default.arrAirportCd}")
    String defaultArrAirportCd;

    /**
     * デフォルト搭乗クラスコード。
     */
    @Value("${default.boardingClassCd}")
    BoardingClassCd defaultBoardingClassCd;

    /**
     * デフォルト出発時刻。
     */
    @Value("${default.time}")
    String defaultTime;

    /**
     * デフォルトの空席照会フォームを作成する。
     * @return 空席照会フォーム
     */
    public TicketSearchForm createDefaultTicketSearchForm() {

        FlightSearchCriteriaForm flightSearchCriteriaForm = new FlightSearchCriteriaForm();
        DateTime sysDate = dateFactory.newDateTime();
        flightSearchCriteriaForm.setMonth(sysDate.getMonthOfYear());
        flightSearchCriteriaForm.setDay(sysDate.getDayOfMonth());
        flightSearchCriteriaForm.setDepAirportCd(defaultDepAirportCd);
        flightSearchCriteriaForm.setArrAirportCd(defaultArrAirportCd);
        flightSearchCriteriaForm.setBoardingClassCd(defaultBoardingClassCd);
        flightSearchCriteriaForm.setTime(defaultTime);

        TicketSearchForm ticketSearchForm = new TicketSearchForm();
        ticketSearchForm.setFlightSearchCriteriaForm(flightSearchCriteriaForm);
        return ticketSearchForm;
    }

    /**
     * 空席照会画面の表示情報を作成する。
     * @return 空席照会画面の表示情報
     */
    public FlightSearchFormOutputDto createSearchFlightFormOutputDto() {
        FlightSearchFormOutputDto flightSearchFormOutputDto = new FlightSearchFormOutputDto();
        DateTime sysDate = dateFactory.newDateTime();
        flightSearchFormOutputDto.setDefaultMonth(sysDate.getMonthOfYear());
        flightSearchFormOutputDto.setDefaultDay(sysDate.getDayOfMonth());
        flightSearchFormOutputDto.setDefaultDepAirportCd(defaultDepAirportCd);
        flightSearchFormOutputDto.setDefaultArrAirportCd(defaultArrAirportCd);
        flightSearchFormOutputDto.setDefaultBoardingClassCd(
                defaultBoardingClassCd);
        flightSearchFormOutputDto.setDefaultTime(defaultTime);
        flightSearchFormOutputDto.setBeginningPeriod(dateFactory.newDate());
        flightSearchFormOutputDto.setEndingPeriod(ticketSharedService
                .getSearchLimitDate().toDate());
        return flightSearchFormOutputDto;
    }

    /**
     * 空席照会を行う。
     * @param ticketSearchForm 空席照会フォーム
     * @param pageable ページネーション検索条件
     * @return 空席照会結果画面出力情報
     * @throws BusinessException 業務例外
     */
    public FlightSearchResultOutputDto searchFlight(
            TicketSearchForm ticketSearchForm,
            Pageable pageable) throws BusinessException {

        FlightSearchCriteriaForm flightSearchCriteriaForm = ticketSearchForm
                .getFlightSearchCriteriaForm();

        // 搭乗日の取得
        int depMonth = flightSearchCriteriaForm.getMonth();
        int depDay = flightSearchCriteriaForm.getDay();
        int depYear = ticketHelper.getDepYear(depMonth);
        LocalDate depDate = new LocalDate(depYear, depMonth, depDay);

        // 搭乗日有効性チェック
        ticketSharedService.validateDepatureDate(depDate.toDate());

        // 空席照会をServiceに依頼して、結果を取得する。
        TicketSearchCriteriaDto ticketSearchCriteriaDto = beanMapper.map(
                flightSearchCriteriaForm, TicketSearchCriteriaDto.class);
        ticketSearchCriteriaDto.setDepDate(depDate.toDate());
        TicketSearchResultDto ticketSearchResultDto = ticketSearchService
                .searchFlight(ticketSearchCriteriaDto, pageable);

        // 往路を検索する場合、検索条件を退避する。
        if (CollectionUtils.isEmpty(ticketSearchForm
                .getSelectFlightFormList())) {
            ticketSearchForm.setOutwardLineSearchCriteriaForm(ticketSearchForm
                    .getFlightSearchCriteriaForm());
        }

        // 空席照会結果画面の表示用データの生成する。
        FlightSearchResultOutputDto flightSearchResultOutputDto = createFlightSearchResultOutputDto(
                flightSearchCriteriaForm, depDate, ticketSearchResultDto);

        return flightSearchResultOutputDto;
    }

    /**
     * 空席照会結果画面の表示用データの生成する。
     * @param flightSearchCriteriaForm 空席照会条件フォーム
     * @param depDate 搭乗日
     * @param ticketSearchResultDto 空席照会の検索結果
     * @return 空席照会結果画面の表示用データを保持するDTO
     */
    private FlightSearchResultOutputDto createFlightSearchResultOutputDto(
            FlightSearchCriteriaForm flightSearchCriteriaForm,
            LocalDate depDate, TicketSearchResultDto ticketSearchResultDto) {

        FlightSearchResultOutputDto flightSearchResultOutputDto = new FlightSearchResultOutputDto();

        // 空席照会の検索結果を設定する。
        flightSearchResultOutputDto.setTicketSearchResultDto(
                ticketSearchResultDto);

        // 逆の搭乗クラスを設定する。
        if (BoardingClassCd.N.equals(flightSearchCriteriaForm
                .getBoardingClassCd())) {
            flightSearchResultOutputDto.setOtherBoardingClassCd(
                    BoardingClassCd.S);
        } else {
            flightSearchResultOutputDto.setOtherBoardingClassCd(
                    BoardingClassCd.N);
        }

        // 搭乗日を設定する。
        flightSearchResultOutputDto.setDepDate(depDate.toDate());

        // 前日の検索ボタンを表示するかどうかを判定し、表示する場合は、搭乗日の前日(月と日)を設定する。
        boolean isDepDateAfterToday = depDate.isAfter(dateFactory.newDateTime()
                .toLocalDate());
        if (isDepDateAfterToday) {
            LocalDate previousDate = depDate.minusDays(1);
            flightSearchResultOutputDto.setMonthOfPreviousDate(previousDate
                    .getMonthOfYear());
            flightSearchResultOutputDto.setDayOfPreviousDate(previousDate
                    .getDayOfMonth());
        }
        flightSearchResultOutputDto.setIsDepDateAfterToday(isDepDateAfterToday);

        // 翌日の検索ボタンを表示するかどうかを判定し、表示する場合は、搭乗日の翌日(月と日)を設定する。
        boolean isDepDateBeforeLimitDate = depDate.isBefore(ticketSharedService
                .getSearchLimitDate());
        if (isDepDateBeforeLimitDate) {
            LocalDate nextDate = depDate.plusDays(1);
            flightSearchResultOutputDto.setMonthOfNextDate(nextDate
                    .getMonthOfYear());
            flightSearchResultOutputDto.setDayOfNextDate(nextDate
                    .getDayOfMonth());
        }
        flightSearchResultOutputDto.setIsDepDateBeforeLimitDate(
                isDepDateBeforeLimitDate);

        return flightSearchResultOutputDto;
    }

}
