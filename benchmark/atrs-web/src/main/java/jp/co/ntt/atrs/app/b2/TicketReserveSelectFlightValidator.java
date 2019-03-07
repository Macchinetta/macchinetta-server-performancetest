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
