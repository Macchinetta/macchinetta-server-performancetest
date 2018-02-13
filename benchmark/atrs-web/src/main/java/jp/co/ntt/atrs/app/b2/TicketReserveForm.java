/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.b2;

import jp.co.ntt.atrs.app.b0.FlightSearchCriteriaForm;
import jp.co.ntt.atrs.app.b0.SelectFlightForm;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

/**
 * チケット予約フォーム。
 * <p>
 * 以下のフォームを内包する
 * </p>
 * <ul>
 * <li>往路の空席照会条件フォーム</li>
 * <li>選択フライト情報フォームのリスト</li>
 * <li>予約情報フォーム</li>
 * </ul>
 * @author NTT 電電三郎
 */
public class TicketReserveForm implements Serializable {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 844559589255817310L;

    /**
     * 往路の空席照会条件フォーム。
     */
    @Valid
    private FlightSearchCriteriaForm outwardLineSearchCriteriaForm;

    /**
     * 選択フライト情報フォームのリスト。
     */
    @Valid
    private List<SelectFlightForm> selectFlightFormList;

    /**
     * 予約情報フォーム。
     */
    @Valid
    private ReservationForm reservationForm;

    /**
     * コンストラクタ。
     */
    public TicketReserveForm() {
        super();
        this.outwardLineSearchCriteriaForm = new FlightSearchCriteriaForm();
        this.selectFlightFormList = new ArrayList<>();
        this.reservationForm = new ReservationForm();
    }

    /**
     * 往路の空席照会条件フォームを取得する。
     * @return 往路の空席照会条件フォーム
     */
    public FlightSearchCriteriaForm getOutwardLineSearchCriteriaForm() {
        return outwardLineSearchCriteriaForm;
    }

    /**
     * 往路の空席照会条件フォームを設定する。
     * @param outwardLineSearchCriteriaForm 往路の空席照会条件フォーム
     */
    public void setOutwardLineSearchCriteriaForm(
            FlightSearchCriteriaForm outwardLineSearchCriteriaForm) {
        this.outwardLineSearchCriteriaForm = outwardLineSearchCriteriaForm;
    }

    /**
     * 選択フライト情報フォームのリストを取得する。
     * @return 選択フライト情報フォームのリスト
     */
    public List<SelectFlightForm> getSelectFlightFormList() {
        return selectFlightFormList;
    }

    /**
     * 選択フライト情報フォームのリストを設定する。
     * @param selectFlightFormList 選択フライト情報フォームのリスト
     */
    public void setSelectFlightFormList(
            List<SelectFlightForm> selectFlightFormList) {
        this.selectFlightFormList = selectFlightFormList;
    }

    /**
     * 予約情報フォームを取得する。
     * @return 予約情報フォーム
     */
    public ReservationForm getReservationForm() {
        return reservationForm;
    }

    /**
     * 予約情報フォームを設定する。
     * @param reservationForm 予約情報フォーム
     */
    public void setReservationForm(ReservationForm reservationForm) {
        this.reservationForm = reservationForm;
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
