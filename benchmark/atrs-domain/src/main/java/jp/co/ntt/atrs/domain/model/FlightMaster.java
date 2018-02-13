/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * フライト基本情報。
 * @author NTT 電電太郎
 */
public class FlightMaster implements Serializable {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 5002676861075741349L;

    /**
     * 便名。
     */
    private String flightName;

    /**
     * 出発時刻。
     */
    private String departureTime;

    /**
     * 到着時刻。
     */
    private String arrivalTime;

    /**
     * 区間。
     */
    private Route route;

    /**
     * 機種。
     */
    private Plane plane;

    /**
     * 便名を取得する。
     * @return 便名
     */
    public String getFlightName() {
        return flightName;
    }

    /**
     * 便名を設定する。
     * @param flightName 便名
     */
    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    /**
     * 出発時刻を取得する。
     * @return 出発時刻
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * 出発時刻を設定する。
     * @param departureTime 出発時刻
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * 到着時刻 を取得する。
     * @return 到着時刻
     */
    public String getArrivalTime() {
        return arrivalTime;
    }

    /**
     * 到着時刻 を設定する。
     * @param arrivalTime 到着時刻
     */
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * 区間 を取得する。
     * @return 区間
     */
    public Route getRoute() {
        return route;
    }

    /**
     * 区間 を設定する。
     * @param route 区間
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * <p>
     * [概 要] {@code plane} を取得する。
     * </p>
     * <p>
     * [詳 細]
     * </p>
     * <p>
     * [備 考]
     * </p>
     * @return {@code plane}
     */
    public Plane getPlane() {
        return plane;
    }

    /**
     * <p>
     * [概 要] {@code plane} を設定する。
     * </p>
     * <p>
     * [詳 細]
     * </p>
     * <p>
     * [備 考]
     * </p>
     * @param plane {@code plane}
     */
    public void setPlane(Plane plane) {
        this.plane = plane;
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
