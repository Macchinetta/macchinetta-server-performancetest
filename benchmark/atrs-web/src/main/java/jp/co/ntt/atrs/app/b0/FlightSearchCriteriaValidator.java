/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.b0;

import jp.co.ntt.atrs.domain.common.util.DateTimeUtil;
import jp.co.ntt.atrs.domain.service.a0.CommonErrorCode;
import jp.co.ntt.atrs.domain.service.b1.TicketSearchErrorCode;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.inject.Inject;

/**
 * 空席照会条件フォームのバリデータ。
 * <p>
 * 下記の場合をエラーとする。
 * </p>
 * <ul>
 * <li>出発空港と到着空港が同じ場合。</li>
 * <li>搭乗日が実在する日付ではない場合。(例：11月31日)</li>
 * </ul>
 * @author NTT 電電次郎
 */
@Component
public class FlightSearchCriteriaValidator implements Validator {

    /**
     * チケット予約共通Helper。
     */
    @Inject
    TicketHelper ticketHelper;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return (FlightSearchCriteriaForm.class).isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {

        FlightSearchCriteriaForm flightSearchCriteriaForm = (FlightSearchCriteriaForm) target;

        // 出発空港と到着空港が同じでないかチェックする。
        if (!errors.hasFieldErrors("depAirportCd") && !errors.hasFieldErrors(
                "arrAirportCd")) {
            String depAirport = flightSearchCriteriaForm.getDepAirportCd();
            String arrAirport = flightSearchCriteriaForm.getArrAirportCd();
            if (depAirport.equals(arrAirport)) {
                errors.reject(TicketSearchErrorCode.E_AR_B1_5001.code());
            }
        }

        // 搭乗日(月)と搭乗日(日)が有効の場合、搭乗日をチェックする。
        if (!errors.hasFieldErrors("month") && !errors.hasFieldErrors("day")) {
            int depMonth = flightSearchCriteriaForm.getMonth();
            int depDay = flightSearchCriteriaForm.getDay();
            int depYear = ticketHelper.getDepYear(depMonth);
            if (!DateTimeUtil.isValidDate(depYear, depMonth, depDay)) {
                errors.rejectValue("day", CommonErrorCode.E_AR_FW_5001.code(),
                        new Object[] {
                                new DefaultMessageSourceResolvable("flightSearchCriteriaForm.day") },
                        null);
            }
        }

    }

}
