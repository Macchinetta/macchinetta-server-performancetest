/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.b1;

import javax.inject.Inject;

import jp.co.ntt.atrs.app.b0.FlightSearchCriteriaForm;
import jp.co.ntt.atrs.app.b0.FlightSearchCriteriaValidator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 空席照会フォームのバリデータ。
 * @author NTT 電電次郎
 */
@Component
public class TicketSearchValidator implements Validator {

    /**
     * 空席照会条件バリデータ。
     */
    @Inject
    FlightSearchCriteriaValidator flightSearchCriteriaFormValidator;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return (TicketSearchForm.class).isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {

        TicketSearchForm ticketSearchForm = (TicketSearchForm) target;

        // 空席照会条件フォームを取得する。
        FlightSearchCriteriaForm flightSearchCriteriaForm = ticketSearchForm
                .getFlightSearchCriteriaForm();

        try {
            errors.pushNestedPath("flightSearchCriteriaForm");
            // 空席照会条件フォームをチェックする。
            ValidationUtils.invokeValidator(
                    this.flightSearchCriteriaFormValidator,
                    flightSearchCriteriaForm, errors);
        } finally {
            errors.popNestedPath();
        }
    }

}
