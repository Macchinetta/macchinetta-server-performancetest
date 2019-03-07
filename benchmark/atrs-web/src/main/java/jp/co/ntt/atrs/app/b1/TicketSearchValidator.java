/*
 * Copyright(c) 2014 NTT Corporation.
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
