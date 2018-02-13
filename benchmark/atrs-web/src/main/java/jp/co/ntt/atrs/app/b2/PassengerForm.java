/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.b2;

import jp.co.ntt.atrs.domain.common.validate.FixedLength;
import jp.co.ntt.atrs.domain.common.validate.FullWidthKatakana;
import jp.co.ntt.atrs.domain.common.validate.HalfWidthNumber;
import jp.co.ntt.atrs.domain.model.Gender;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.io.Serializable;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * 搭乗者情報フォーム。
 * @author NTT 電電三郎
 */
public class PassengerForm implements Serializable {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 1195415988743042599L;

    /**
     * 搭乗者姓。
     */
    @Size(min = 1, max = 10)
    @FullWidthKatakana
    private String familyName;

    /**
     * 搭乗者名。
     */
    @Size(min = 1, max = 10)
    @FullWidthKatakana
    private String givenName;

    /**
     * 搭乗者年齢。
     */
    @Min(0)
    @Digits(integer = 3, fraction = 1)
    private Integer age;

    /**
     * 搭乗者性別。
     */
    private Gender gender;

    /**
     * 搭乗者のお客様番号。
     */
    @FixedLength(10)
    @HalfWidthNumber
    private String customerNo;

    /**
     * 搭乗者姓を取得する。
     * @return 搭乗者姓
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * 搭乗者姓を設定する。
     * @param familyName 搭乗者姓
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * 搭乗者名を取得する。
     * @return 搭乗者名
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * 搭乗者名を設定する。
     * @param givenName 搭乗者名
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * 搭乗者年齢を取得する。
     * @return 搭乗者年齢
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 搭乗者年齢を設定する。
     * @param age 搭乗者年齢
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 搭乗者性別を取得する。
     * @return 搭乗者性別
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * 搭乗者性別を設定する。
     * @param gender 搭乗者性別
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * 搭乗者のお客様番号を取得する。
     * @return 搭乗者のお客様番号
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * 搭乗者のお客様番号を設定する。
     * @param customerNo 搭乗者のお客様番号
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this,
                ToStringStyle.MULTI_LINE_STYLE);
    }
}
