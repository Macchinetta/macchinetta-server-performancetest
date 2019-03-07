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

import jp.co.ntt.atrs.domain.common.validate.ValidationUtil;
import jp.co.ntt.atrs.domain.model.Gender;
import jp.co.ntt.atrs.domain.service.b2.TicketReserveErrorCode;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * 予約情報フォームのバリデータ。
 * <p>
 * 以下のチェックを行う。
 * </p>
 * <ul>
 * <li>予約代表者電話番号1と予約代表者電話番号2の指定合計文字数チェック。</li>
 * <li>搭乗者情報の必須チェック。</li>
 * <li>搭乗者情報が少なくとも1件以上入力されていることのチェック。</li>
 * </ul>
 * @author NTT 電電三郎
 */
@Component
public class ReservationFormValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        // assert form to be validated
        return ReservationForm.class.isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {

        ReservationForm reservationForm = (ReservationForm) target;

        // 予約代表者情報入力値の相関チェックを行う。
        if (!(errors.hasFieldErrors("repTel1") || errors.hasFieldErrors(
                "repTel2"))) {
            checkRepresentativeTel(errors, reservationForm.getRepTel1(),
                    reservationForm.getRepTel2());
        }

        List<PassengerForm> passengerFormList = reservationForm
                .getPassengerFormList();
        int inputtedPassengerCount = 0;
        // 搭乗者情報一覧リストの必須入力チェックを行う。ただし、入力値が1つもない搭乗者情報はチェックしない。
        for (int i = 0; i < passengerFormList.size(); i++) {
            PassengerForm passengerForm = passengerFormList.get(i);
            String familyName = passengerForm.getFamilyName();
            String givenName = passengerForm.getGivenName();
            Integer age = passengerForm.getAge();
            Gender gender = passengerForm.getGender();
            String customerNo = passengerForm.getCustomerNo();

            if (StringUtils.hasLength(familyName) || StringUtils.hasLength(
                    givenName) || age != null || gender != null || StringUtils
                            .hasLength(customerNo)) {
                inputtedPassengerCount++;
                checkRequired(errors, "familyName", i);
                checkRequired(errors, "givenName", i);
                checkRequired(errors, "age", i);
                checkRequired(errors, "gender", i);
            }
        }

        // 搭乗者情報が少なくとも1件以上入力されていることを確認する。
        if (inputtedPassengerCount == 0) {
            errors.reject(TicketReserveErrorCode.E_AR_B2_5001.code());
            return;
        }
    }

    /**
     * 予約代表者電話番号の相関チェックを行う。
     * @param errors エラーメッセージを保持するクラス
     * @param tel1 予約代表者電話番号1
     * @param tel2 予約代表者電話番号2
     */
    private void checkRepresentativeTel(Errors errors, String tel1,
            String tel2) {
        if (!ValidationUtil.isValidTelNum(tel1, tel2)) {
            Object[] errorArgs = new Object[] {
                    new DefaultMessageSourceResolvable("reservationForm.repTel1"),
                    new DefaultMessageSourceResolvable("reservationForm.repTel2"),
                    ValidationUtil.TEL1_AND_TEL2_MIN_LENGTH,
                    ValidationUtil.TEL1_AND_TEL2_MAX_LENGTH };
            errors.reject(TicketReserveErrorCode.E_AR_B2_5002.code(), errorArgs,
                    "");
        }
    }

    /**
     * 搭乗者情報フォームリストのフィールド必須チェックを行う。
     * @param errors エラーメッセージを保持するクラス。
     * @param itemName チェック項目名
     * @param index リストのインデックス値
     */
    private void checkRequired(Errors errors, String itemName, int index) {

        String target = "passengerFormList[" + index + "]." + itemName;

        ValidationUtils.rejectIfEmpty(errors, target, "NotNull", new Object[] {
                new DefaultMessageSourceResolvable("reservationForm."
                        + target) });
    }

}
