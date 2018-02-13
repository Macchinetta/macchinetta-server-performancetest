/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.c0;

import jp.co.ntt.atrs.domain.common.util.DateTimeUtil;
import jp.co.ntt.atrs.domain.common.validate.ValidationUtil;
import jp.co.ntt.atrs.domain.service.a0.CommonErrorCode;
import jp.co.ntt.atrs.domain.service.c0.MemberErrorCode;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * 会員情報フォームの入力チェックを行うバリデータ。
 * @author NTT 電電花子
 */
@Component
public class MemberValidator implements Validator {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return MemberForm.class.isAssignableFrom(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Object target, Errors errors) {

        MemberForm memberForm = (MemberForm) target;

        // メールアドレスチェック
        String mail = memberForm.getMail();
        String reEnterMail = memberForm.getReEnterMail();

        if (StringUtils.hasLength(mail) && StringUtils.hasLength(reEnterMail)) {
            if (!mail.equals(reEnterMail)) {
                // メールアドレスと再入力したメールアドレスが一致しない場合にエラー
                errors.reject(MemberErrorCode.E_AR_C0_5001.code());
            }
        }

        // 電話番号チェック
        if (StringUtils.hasLength(memberForm.getTel1()) && StringUtils
                .hasLength(memberForm.getTel2())) {

            if (!ValidationUtil.isValidTelNum(memberForm.getTel1(), memberForm
                    .getTel2())) {
                // 市外局番と市内局番の合計桁数が6～7桁でなければエラー
                errors.reject(MemberErrorCode.E_AR_C0_5002.code());
            }
        }

        // 生年月日の日付チェックする。
        if (memberForm.getMonthOfBirth() != null && memberForm
                .getDayOfBirth() != null && memberForm
                        .getYearOfBirth() != null) {
            Integer birthYear = memberForm.getYearOfBirth();
            Integer birthMonth = memberForm.getMonthOfBirth();
            Integer birthDay = memberForm.getDayOfBirth();
            if (!DateTimeUtil.isValidDate(birthYear, birthMonth, birthDay)) {
                errors.rejectValue("dayOfBirth", CommonErrorCode.E_AR_FW_5001
                        .code(), new Object[] {
                                new DefaultMessageSourceResolvable("memberForm.dayOfBirth") },
                        null);
            }
        }

    }
}
