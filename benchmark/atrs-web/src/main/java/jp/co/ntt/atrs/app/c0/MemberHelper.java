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
package jp.co.ntt.atrs.app.c0;

import jp.co.ntt.atrs.domain.model.CreditType;
import jp.co.ntt.atrs.domain.model.Member;

import com.github.dozermapper.core.Mapper;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.inject.Inject;

/**
 * 会員情報Helper。
 * @author NTT 電電花子
 */
@Component
public class MemberHelper {

    /**
     * Beanマッパー。
     */
    @Inject
    Mapper beanMapper;

    /**
     * 会員情報フォームをカード会員情報に変換する。
     * @param memberForm 会員情報フォーム
     * @return カード会員情報
     */
    public Member formToMember(MemberForm memberForm) {

        // MemberFormからmemberへ詰め替える
        Member member = beanMapper.map(memberForm, Member.class);

        // 誕生日
        LocalDate dateOfBirth = new LocalDate(memberForm
                .getYearOfBirth(), memberForm.getMonthOfBirth(), memberForm
                        .getDayOfBirth());
        member.setBirthday(dateOfBirth.toDate());

        // 電話番号
        String tel = memberForm.getTel1() + "-" + memberForm.getTel2() + "-"
                + memberForm.getTel3();
        member.setTel(tel);

        // 郵便番号
        String zipCode = memberForm.getZipCode1() + memberForm.getZipCode2();
        member.setZipCode(zipCode);

        // クレジットカード
        CreditType ct = new CreditType();
        ct.setCreditTypeCd(memberForm.getCreditTypeCd());
        member.setCreditType(ct);

        // クレジットカード期限
        String creditTerm = memberForm.getCreditMonth() + "/" + memberForm
                .getCreditYear();
        member.setCreditTerm(creditTerm);

        return member;

    }

    /**
     * カード会員情報を会員情報フォームに変換する。
     * @param member カード会員情報
     * @return 会員情報フォーム
     */
    public MemberForm memberToForm(Member member) {

        MemberForm memberForm = beanMapper.map(member, MemberForm.class);

        // Gender
        memberForm.setGender(member.getGender());

        // Tel
        String[] tel = member.getTel().split("-");
        if (tel.length == 3) {
            memberForm.setTel1(tel[0]);
            memberForm.setTel2(tel[1]);
            memberForm.setTel3(tel[2]);
        }

        // Zipcode
        if (StringUtils.hasLength(member.getZipCode()) && member.getZipCode()
                .length() >= 7) {
            memberForm.setZipCode1(member.getZipCode().substring(0, 3));
            memberForm.setZipCode2(member.getZipCode().substring(3, 7));
        }

        // クレジットカード
        memberForm.setCreditTypeCd(member.getCreditType().getCreditTypeCd());

        // Term
        String[] creditTerm = member.getCreditTerm().split("/");
        if (creditTerm.length == 2) {
            memberForm.setCreditMonth(creditTerm[0]);
            memberForm.setCreditYear(creditTerm[1]);
        }

        // Birthdayを分解
        LocalDate dateOfBirth = new LocalDate(member.getBirthday());
        memberForm.setYearOfBirth(dateOfBirth.getYear());
        memberForm.setMonthOfBirth(dateOfBirth.getMonthOfYear());
        memberForm.setDayOfBirth(dateOfBirth.getDayOfMonth());

        return memberForm;
    }

}
