/*
 * Copyright(c) 2015 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package jp.co.ntt.atrs.domain.service.b1;

import jp.co.ntt.atrs.domain.common.exception.AtrsBusinessException;
import jp.co.ntt.atrs.domain.common.masterdata.BoardingClassProvider;
import jp.co.ntt.atrs.domain.common.masterdata.FareTypeProvider;
import jp.co.ntt.atrs.domain.common.masterdata.FlightMasterProvider;
import jp.co.ntt.atrs.domain.common.masterdata.RouteProvider;
import jp.co.ntt.atrs.domain.common.util.DateTimeUtil;
import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.FareType;
import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.model.Flight;
import jp.co.ntt.atrs.domain.model.FlightMaster;
import jp.co.ntt.atrs.domain.model.Route;
import jp.co.ntt.atrs.domain.repository.flight.FlightRepository;
import jp.co.ntt.atrs.domain.repository.flight.VacantSeatSearchCriteriaDto;
import jp.co.ntt.atrs.domain.service.b0.TicketSharedService;

import org.joda.time.LocalDate;
import org.joda.time.Days;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.terasoluna.gfw.common.codelist.CodeList;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;
import org.terasoluna.gfw.common.exception.BusinessException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * 空席照会サービス実装クラス。
 * @author NTT 電電次郎
 */
@Service
@Transactional
public class TicketSearchServiceImpl implements TicketSearchService {

    /**
     * 運賃種別コードリスト。
     */
    @Inject
    @Named("CL_FARETYPE")
    CodeList fareTypeCodeList;

    /**
     * 日付、時刻取得インターフェース。
     */
    @Inject
    JodaTimeDateFactory dateFactory;

    /**
     * フライト情報リポジトリ。
     */
    @Inject
    FlightRepository flightRepository;

    /**
     * 区間情報提供クラス。
     */
    @Inject
    RouteProvider routeProvider;

    /**
     * 運賃種別情報提供クラス。
     */
    @Inject
    FareTypeProvider fareTypeProvider;

    /**
     * フライト基本情報提供クラス。
     */
    @Inject
    FlightMasterProvider flightMasterProvider;

    /**
     * 搭乗クラス情報提供クラス。
     */
    @Inject
    BoardingClassProvider boardingClassProvider;

    /**
     * チケット予約共通サービス。
     */
    @Inject
    TicketSharedService ticketSharedService;

    /**
     * {@inheritDoc}
     */
    @Override
    public TicketSearchResultDto searchFlight(
            TicketSearchCriteriaDto searchCriteria,
            Pageable pageable) throws BusinessException {
        // 引数チェック
        Assert.notNull(searchCriteria);
        Assert.notNull(pageable);

        Date depDate = searchCriteria.getDepDate();
        String depTime = searchCriteria.getDepTime();
        BoardingClassCd boardingClassCd = searchCriteria.getBoardingClassCd();
        String depAirportCd = searchCriteria.getDepartureAirportCd();
        String arrAirportCd = searchCriteria.getArrivalAirportCd();

        Assert.notNull(depDate);
        Assert.notNull(boardingClassCd);
        Assert.hasText(depAirportCd);
        Assert.hasText(arrAirportCd);

        // 指定された出発空港・到着空港に該当する区間が存在するかどうかチェック
        Route route = routeProvider.getRouteByAirportCd(depAirportCd,
                arrAirportCd);
        if (route == null) {
            throw new AtrsBusinessException(TicketSearchErrorCode.E_AR_B1_2002);
        }

        // システム日付が搭乗日から何日前かを計算
        LocalDate sysLocalDate = dateFactory.newDateTime().toLocalDate();
        LocalDate depLocalDate = new LocalDate(depDate);
        int beforeDayNum = Days.daysBetween(sysLocalDate, depLocalDate)
                .getDays();

        VacantSeatSearchCriteriaDto criteria;
        if (StringUtils.hasLength(depTime)) {
            criteria = new VacantSeatSearchCriteriaDto(depDate, DateTimeUtil
                    .extractHourString(
                            depTime), route, boardingClassCd, beforeDayNum);
        } else {
            criteria = new VacantSeatSearchCriteriaDto(depDate, route, boardingClassCd, beforeDayNum);
        }

        // リポジトリから照会結果総件数を取得
        int totalCount = flightRepository.countByVacantSeatSearchCriteria(
                criteria);

        // 照会結果件数をチェック
        if (totalCount == 0) {
            throw new AtrsBusinessException(TicketSearchErrorCode.E_AR_B1_2003);
        }

        // リポジトリから照会結果を取得
        List<Flight> flightList = flightRepository
                .findAllPageByVacantSeatSearchCriteria(criteria, pageable);

        // 照会結果に含まれていた運賃種別
        Set<FareTypeCd> resultFareTypeSet = new HashSet<>();

        // 取得したフライトに関連するエンティティを設定
        for (Flight flight : flightList) {
            FareTypeCd fareTypeCd = flight.getFareType().getFareTypeCd();
            flight.setFareType(fareTypeProvider.getFareType(fareTypeCd));
            flight.setFlightMaster(flightMasterProvider.getFlightMaster(flight
                    .getFlightMaster().getFlightName()));
            flight.setBoardingClass(boardingClassProvider.getBoardingClass(
                    flight.getBoardingClass().getBoardingClassCd()));
            resultFareTypeSet.add(fareTypeCd);
        }

        // 照会結果に含まれていた運賃種別を表示順の昇順でソート
        List<FareTypeCd> sortedFareTypeCdList = createSortedFareTypeCdList(
                resultFareTypeSet);

        // 基本運賃の計算
        int basicFare = ticketSharedService.calculateBasicFare(route
                .getBasicFare(), boardingClassCd, depDate);

        // 照会結果のリストを作成(出発時刻昇順でソート)
        List<FlightVacantInfoDto> sortedFlightVacantInfoList = createSortedFlightVacantInfoList(
                flightList, basicFare);

        // pageオブジェクトを作成
        Page<FlightVacantInfoDto> flightVacantInfoPage = new PageImpl<>(sortedFlightVacantInfoList, pageable, totalCount);

        // 照会結果のDTOを作成し返却
        return new TicketSearchResultDto(sortedFareTypeCdList, flightVacantInfoPage);
    }

    /**
     * 運賃種別コードリストの表示順でソートした運賃種別リストを作成する。
     * @param fareTypeSet ソート対象の運賃種別セット
     * @return ソートした運賃種別リスト
     */
    private List<FareTypeCd> createSortedFareTypeCdList(
            Set<FareTypeCd> fareTypeSet) {
        List<FareTypeCd> sortedFareTypeCdList = new ArrayList<>();
        for (String fareTypeCode : fareTypeCodeList.asMap().keySet()) {
            FareTypeCd fareTypeCd = FareTypeCd.valueOf(fareTypeCode);
            if (fareTypeSet.contains(fareTypeCd)) {
                sortedFareTypeCdList.add(fareTypeCd);
            }
        }
        return sortedFareTypeCdList;
    }

    /**
     * フライトリストからソート済みの空席照会結果リストを作成する。
     * @param flightList 空席照会結果フライトリスト
     * @param basicFare 基本運賃
     * @return ソートした空席結果情報リスト
     */
    private List<FlightVacantInfoDto> createSortedFlightVacantInfoList(
            List<Flight> flightList, int basicFare) {

        // 照会結果を出発時刻昇順ソート用TreeMap
        Map<String, FlightVacantInfoDto> vacantInfoMap = new TreeMap<>();
        for (Flight flight : flightList) {

            FlightMaster flightMaster = flight.getFlightMaster();
            String departureTime = flightMaster.getDepartureTime();
            FlightVacantInfoDto vacantInfo = vacantInfoMap.get(departureTime);
            if (vacantInfo == null) {
                vacantInfo = new FlightVacantInfoDto();
                vacantInfo.setFlightMaster(flight.getFlightMaster());
                vacantInfoMap.put(departureTime, vacantInfo);
            }

            // 運賃種別情報を設定
            FareType fareType = flight.getFareType();
            int fare = ticketSharedService.calculateFare(basicFare, fareType
                    .getDiscountRate());

            vacantInfo.addFareTypeVacantInfo(fareType.getFareTypeCd(),
                    new FareTypeVacantInfoDto(fare, flight.getVacantNum()));
        }

        // 出発時刻昇順ソートしたリストを返却
        return new ArrayList<>(vacantInfoMap.values());
    }

}
