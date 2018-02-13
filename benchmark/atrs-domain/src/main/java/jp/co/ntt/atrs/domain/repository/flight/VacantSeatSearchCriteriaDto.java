/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.repository.flight;

import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.model.Route;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 空席情報検索条件を保持するクラス。
 * @author NTT 電電太郎
 */
public class VacantSeatSearchCriteriaDto implements Serializable {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 8627180653316385366L;

    /**
     * 搭乗クラス。
     **/
    private BoardingClassCd boardingClass;

    /**
     * 出発時間（分は切り捨て）。
     */
    private String depHour;

    /**
     * 搭乗日。
     **/
    private Date depDate;

    /**
     * 区間情報。
     */
    private Route route;

    /**
     * 搭乗日前日数。
     */
    private int beforeDayNum;

    /**
     * コンストラクタ。
     * @param depDate 出発日
     * @param depHour 出発時間（分は切り捨て）
     * @param route 区間情報
     * @param boardingClass 搭乗クラス
     * @param beforeDayNum 搭乗日前日数
     */
    public VacantSeatSearchCriteriaDto(Date depDate, String depHour,
            Route route, BoardingClassCd boardingClass, int beforeDayNum) {
        this.depDate = depDate;
        this.route = route;
        this.depHour = depHour;
        this.boardingClass = boardingClass;
        this.beforeDayNum = beforeDayNum;
    }

    /**
     * コンストラクタ。
     * <p>
     * 出発時間を指定しないコンストラクタ。
     * </p>
     * @param depDate 出発日
     * @param route 区間情報
     * @param boardingClass 搭乗クラス
     * @param beforeDayNum 搭乗日前日数
     */
    public VacantSeatSearchCriteriaDto(Date depDate, Route route,
            BoardingClassCd boardingClass, int beforeDayNum) {
        this(depDate, null, route, boardingClass, beforeDayNum);
    }

    /**
     * 搭乗クラスを取得する。
     * @return 搭乗クラス
     */
    public BoardingClassCd getBoardingClass() {
        return boardingClass;
    }

    /**
     * 出発日を取得する。
     * @return 出発日
     */
    public Date getDepDate() {
        return depDate;
    }

    /**
     * 出発時間（分は切り捨て）を取得する。
     * @return 出発時間（分は切り捨て）
     */
    public String getDepHour() {
        return depHour;
    }

    /**
     * 区間情報を取得する。
     * @return 区間情報
     */
    public Route getRoute() {
        return route;
    }

    /**
     * 搭乗日前日数を取得する。
     * @return 区間情報
     */
    public int getBeforeDayNum() {
        return beforeDayNum;
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
