/*
 * Copyright(c) 2014-2017 NTT Corporation.
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
