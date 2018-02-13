/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.b2;

import jp.co.ntt.atrs.domain.common.validate.FixedLength;
import jp.co.ntt.atrs.domain.common.validate.FullWidthKatakana;
import jp.co.ntt.atrs.domain.common.validate.HalfWidth;
import jp.co.ntt.atrs.domain.common.validate.HalfWidthNumber;
import jp.co.ntt.atrs.domain.model.Gender;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 予約情報フォーム。
 * <p>
 * 入力した搭乗者と予約代表者の情報を受け取る。
 * </p>
 * <p>
 * 以下のフォームを内包する。
 * </p>
 * <ul>
 * <li>搭乗者情報フォームのリスト</li>
 * </ul>
 * @author NTT 電電三郎
 */
public class ReservationForm implements Serializable {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 1259500249222486270L;

    /**
     * 予約代表者姓。
     */
    @NotNull
    @Size(min = 1, max = 10)
    @FullWidthKatakana
    private String repFamilyName;

    /**
     * 予約代表者名。
     */
    @NotNull
    @Size(min = 1, max = 10)
    @FullWidthKatakana
    private String repGivenName;

    /**
     * 予約代表者年齢。
     */
    @NotNull
    @Min(0)
    @Digits(integer = 3, fraction = 1)
    private Integer repAge;

    /**
     * 予約代表者性別。
     */
    @NotNull
    private Gender repGender;

    /**
     * 予約代表者お客様番号。
     */
    @FixedLength(10)
    @HalfWidthNumber
    private String repCustomerNo;

    /**
     * 予約代表者電話番号1。
     */
    @NotNull
    @Size(min = 2, max = 5)
    @HalfWidthNumber
    private String repTel1;

    /**
     * 予約代表者電話番号2。
     */
    @NotNull
    @Size(min = 1, max = 4)
    @HalfWidthNumber
    private String repTel2;

    /**
     * 予約代表者電話番号3。
     */
    @NotNull
    @FixedLength(4)
    @HalfWidthNumber
    private String repTel3;

    /**
     * 予約代表者メールアドレス。
     */
    @NotNull
    @Size(min = 1, max = 256)
    @HalfWidth
    @Email
    private String repMail;

    /**
     * 搭乗者情報フォームリスト。
     */
    @Valid
    private List<PassengerForm> passengerFormList = new ArrayList<>();

    /**
     * 予約代表者姓を取得する。
     * @return 予約代表者姓
     */
    public String getRepFamilyName() {
        return repFamilyName;
    }

    /**
     * 予約代表者姓を設定する。
     * @param repFamilyName 予約代表者姓
     */
    public void setRepFamilyName(String repFamilyName) {
        this.repFamilyName = repFamilyName;
    }

    /**
     * 予約代表者名を取得する。
     * @return 予約代表者名
     */
    public String getRepGivenName() {
        return repGivenName;
    }

    /**
     * 予約代表者名を設定する。
     * @param repGivenName 予約代表者名
     */
    public void setRepGivenName(String repGivenName) {
        this.repGivenName = repGivenName;
    }

    /**
     * 予約代表者年齢を取得する。
     * @return 予約代表者年齢
     */
    public Integer getRepAge() {
        return repAge;
    }

    /**
     * 予約代表者年齢を設定する。
     * @param repAge 予約代表者年齢
     */
    public void setRepAge(Integer repAge) {
        this.repAge = repAge;
    }

    /**
     * 予約代表者性別を取得する。
     * @return 予約代表者性別
     */
    public Gender getRepGender() {
        return repGender;
    }

    /**
     * 予約代表者性別を設定する。
     * @param repGender 予約代表者性別
     */
    public void setRepGender(Gender repGender) {
        this.repGender = repGender;
    }

    /**
     * 予約代表者お客様番号を取得する。
     * @return 予約代表者お客様番号
     */
    public String getRepCustomerNo() {
        return repCustomerNo;
    }

    /**
     * 予約代表者お客様番号を設定する。
     * @param repCustomerNo 予約代表者お客様番号
     */
    public void setRepCustomerNo(String repCustomerNo) {
        this.repCustomerNo = repCustomerNo;
    }

    /**
     * 予約代表者電話番号1を取得する。
     * @return 予約代表者電話番号1
     */
    public String getRepTel1() {
        return repTel1;
    }

    /**
     * 予約代表者電話番号1を設定する。
     * @param repTel1 予約代表者電話番号1
     */
    public void setRepTel1(String repTel1) {
        this.repTel1 = repTel1;
    }

    /**
     * 予約代表者電話番号2を取得する。
     * @return 予約代表者電話番号2
     */
    public String getRepTel2() {
        return repTel2;
    }

    /**
     * 予約代表者電話番号2を設定する。
     * @param repTel2 予約代表者電話番号2
     */
    public void setRepTel2(String repTel2) {
        this.repTel2 = repTel2;
    }

    /**
     * 予約代表者電話番号3を取得する。
     * @return 予約代表者電話番号3
     */
    public String getRepTel3() {
        return repTel3;
    }

    /**
     * 予約代表者電話番号3を設定する。
     * @param repTel3 予約代表者電話番号3
     */
    public void setRepTel3(String repTel3) {
        this.repTel3 = repTel3;
    }

    /**
     * 予約代表者メールアドレスを取得する。
     * @return 予約代表者メールアドレス
     */
    public String getRepMail() {
        return repMail;
    }

    /**
     * 予約代表者メールアドレスを設定する。
     * @param repMail 予約代表者メールアドレス
     */
    public void setRepMail(String repMail) {
        this.repMail = repMail;
    }

    /**
     * 搭乗者情報一覧リストを取得する。
     * @return 搭乗者情報一覧リスト
     */
    public List<PassengerForm> getPassengerFormList() {
        return passengerFormList;
    }

    /**
     * 搭乗者情報一覧リストを設定する。
     * @param passengerFormList 搭乗者情報一覧リスト
     */
    public void setPassengerFormList(List<PassengerForm> passengerFormList) {
        this.passengerFormList = passengerFormList;
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
