/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.service.b1;

import jp.co.ntt.atrs.domain.model.BoardingClassCd;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 空席照会の検索条件を保持するクラス。
 * @author NTT 電電次郎
 */
public class TicketSearchCriteriaDto implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 491678816013840729L;

    /**
     * 搭乗日。
     */
    private Date depDate;

    /**
     * 出発時刻。
     */
    private String depTime;

    /**
     * 到着空港コード。
     */
    private String arrivalAirportCd;

    /**
     * 出発空港コード。
     */
    private String departureAirportCd;

    /**
     * 搭乗クラスコード。
     */
    private BoardingClassCd boardingClassCd;

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
     * 搭乗日を取得する。
     * @return 搭乗日
     */
    public Date getDepDate() {
        return depDate;
    }

    /**
     * 搭乗日を設定する。
     * @param depDate 搭乗日
     */
    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }

    /**
     * 出発時刻を取得する。
     * @return 出発時刻
     */
    public String getDepTime() {
        return depTime;
    }

    /**
     * 出発時刻を設定する。
     * @param depTime 出発時刻
     */
    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    /**
     * 到着空港コードを取得する。
     * @return 到着空港
     */
    public String getArrivalAirportCd() {
        return arrivalAirportCd;
    }

    /**
     * 到着空港コードを設定する。
     * @param arrivalAirportCd 到着空港コード
     */
    public void setArrivalAirportCd(String arrivalAirportCd) {
        this.arrivalAirportCd = arrivalAirportCd;
    }

    /**
     * 出発空港コードを取得する。
     * @return 出発空港
     */
    public String getDepartureAirportCd() {
        return departureAirportCd;
    }

    /**
     * 出発空港コードを設定する。
     * @param departureAirportCd 出発空港コード
     */
    public void setDepartureAirportCd(String departureAirportCd) {
        this.departureAirportCd = departureAirportCd;
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
