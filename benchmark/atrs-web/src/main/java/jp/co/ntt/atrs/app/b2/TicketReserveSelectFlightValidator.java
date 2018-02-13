/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.b2;

import jp.co.ntt.atrs.app.b0.FlightSearchCriteriaForm;
import jp.co.ntt.atrs.app.b0.FlightSearchCriteriaValidator;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import javax.inject.Inject;

/**
 * チケット予約用選択フライト情報フォームのバリデータ。
 * @author NTT 電電次郎
 */
@Component
public class TicketReserveSelectFlightValidator implements Validator {

    /**
     * 空席照会条件フォームのバリデータ。
     */
    @Inject
    FlightSearchCriteriaValidator flightSearchCriteriaFormValidator;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return (TicketReserveSelectFlightForm.class).isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {

        TicketReserveSelectFlightForm ticketReserveSelectFlightForm = (TicketReserveSelectFlightForm) target;
        // 往路の空席情報検索条件を取得する。
        FlightSearchCriteriaForm outwardLineSearchCriteriaForm = ticketReserveSelectFlightForm
                .getOutwardLineSearchCriteriaForm();
        Assert.notNull(outwardLineSearchCriteriaForm);

        try {
            errors.pushNestedPath("outwardLineSearchCriteriaForm");
            // 往路の空席情報検索条件をチェックする。
            ValidationUtils.invokeValidator(
                    this.flightSearchCriteriaFormValidator,
                    outwardLineSearchCriteriaForm, errors);
        } finally {
            errors.popNestedPath();
        }
    }

}
