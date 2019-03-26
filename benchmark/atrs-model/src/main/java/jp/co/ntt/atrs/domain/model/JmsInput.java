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
package jp.co.ntt.atrs.domain.model;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.terasoluna.gfw.common.codelist.ExistInCodeList;

public class JmsInput implements Serializable {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = -1380590854883003229L;

    /**
     * 搭乗日(月)。
     */
    @NotNull
    @Min(1)
    @Max(12)
    private Integer month;

    /**
     * 搭乗日（日）。
     */
    @NotNull
    @Min(1)
    @Max(31)
    private Integer day;

    /**
     * 搭乗時刻。
     */
    @ExistInCodeList(codeListId = "CL_DEPTIME")
    private String time;

    /**
     * 出発空港コード。
     */
    @NotNull
    @ExistInCodeList(codeListId = "CL_AIRPORT")
    private String depAirportCd;

    /**
     * 到着空港コード。
     */
    @NotNull
    @ExistInCodeList(codeListId = "CL_AIRPORT")
    private String arrAirportCd;

    /**
     * 搭乗クラスコード。
     */
    @NotNull
    private BoardingClassCd boardingClassCd;

    /**
     * お客様番号。
     */
    private String customerNo;

    /**
     * 搭乗日（月）を取得する。
     * @return 搭乗日（月）
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * 搭乗日（月）を設定する。
     * @param month 搭乗日（月）
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * 搭乗日（日）を取得する。
     * @return 搭乗日（日）
     */
    public Integer getDay() {
        return day;
    }

    /**
     * 搭乗日（日）を設定する。
     * @param day 搭乗日（日）
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * 搭乗時刻を取得する。
     * @return 搭乗時刻
     */
    public String getTime() {
        return time;
    }

    /**
     * 搭乗時刻を設定する。
     * @param time 搭乗時刻
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 出発空港コードを取得する。
     * @return 出発空港コード
     */
    public String getDepAirportCd() {
        return depAirportCd;
    }

    /**
     * 出発空港コードを設定する。
     * @param depAirportCd 出発空港コード
     */
    public void setDepAirportCd(String depAirportCd) {
        this.depAirportCd = depAirportCd;
    }

    /**
     * 到着空港コードを取得する。
     * @return arrAirport 到着空港コード
     */
    public String getArrAirportCd() {
        return arrAirportCd;
    }

    /**
     * 到着空港コードを設定する。
     * @param arrAirportCd 到着空港コード
     */
    public void setArrAirportCd(String arrAirportCd) {
        this.arrAirportCd = arrAirportCd;
    }

    /**
     * 搭乗クラスコードを取得する。
     * @return 搭乗クラスコード
     */
    public BoardingClassCd getBoardingClassCd() {
        return boardingClassCd;
    }

    /**
     * 搭乗クラスコードを設定する。
     * @param boardingClassCd 搭乗クラスコード
     */
    public void setBoardingClassCd(BoardingClassCd boardingClassCd) {
        this.boardingClassCd = boardingClassCd;
    }

    /**
     * お客様番号を取得する。
     * @return お客様番号
     */
    public String getCustomerNo() {
        return customerNo;
    }

    /**
     * お客様番号を設定する。
     * @param customerNo お客様番号
     */
    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

}
