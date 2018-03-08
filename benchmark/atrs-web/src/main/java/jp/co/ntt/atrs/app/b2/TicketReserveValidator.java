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
package jp.co.ntt.atrs.app.b2;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.inject.Inject;

/**
 * チケット予約フォームのバリデータ。
 * @author NTT 電電三郎
 */
@Component
public class TicketReserveValidator implements Validator {

    /**
     * 予約情報フォームのバリデータ。
     */
    @Inject
    ReservationFormValidator reservationFormValidator;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return (TicketReserveForm.class).isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {

        TicketReserveForm ticketReserveForm = (TicketReserveForm) target;
        // 予約情報フォームを取得する。
        ReservationForm reservationForm = ticketReserveForm
                .getReservationForm();
        Assert.notNull(reservationForm);

        try {
            errors.pushNestedPath("reservationForm");
            // 予約情報フォームの検証を行う。
            ValidationUtils.invokeValidator(this.reservationFormValidator,
                    reservationForm, errors);
        } finally {
            errors.popNestedPath();
        }
    }
}
