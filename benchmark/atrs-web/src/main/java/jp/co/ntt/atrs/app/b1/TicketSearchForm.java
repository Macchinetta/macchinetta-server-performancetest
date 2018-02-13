/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.b1;

import jp.co.ntt.atrs.app.b0.FlightSearchCriteriaForm;
import jp.co.ntt.atrs.app.b0.SelectFlightForm;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

/**
 * 空席照会フォーム。
 * <p>
 * 以下のフォームを内包する
 * </p>
 * <ul>
 * <li>空席照会条件フォーム</li>
 * <li>往路の空席照会条件フォーム</li>
 * <li>選択フライト情報フォームのリスト</li>
 * </ul>
 * @author NTT 電電次郎
 */
public class TicketSearchForm implements Serializable {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = -9041848302255127269L;

    /**
     * 空席照会条件フォーム。
     */
    @Valid
    private FlightSearchCriteriaForm flightSearchCriteriaForm;

    /**
     * 往路の空席照会条件フォーム。
     * <p>
     * 退避情報のため、入力チェックの必要はない(@Valid不要)。
     * </p>
     */
    private FlightSearchCriteriaForm outwardLineSearchCriteriaForm;

    /**
     * 選択フライト情報フォームのリスト。
     */
    @Valid
    private List<SelectFlightForm> selectFlightFormList;

    /**
     * コンストラクタ。
     */
    public TicketSearchForm() {
        super();
        this.flightSearchCriteriaForm = new FlightSearchCriteriaForm();
        this.selectFlightFormList = new ArrayList<>();
    }

    /**
     * 空席照会条件 を取得する。
     * @return 空席照会条件
     */
    public FlightSearchCriteriaForm getFlightSearchCriteriaForm() {
        return flightSearchCriteriaForm;
    }

    /**
     * 空席照会条件 を設定する。
     * @param flightSearchCriteriaForm 空席照会条件
     */
    public void setFlightSearchCriteriaForm(
            FlightSearchCriteriaForm flightSearchCriteriaForm) {
        this.flightSearchCriteriaForm = flightSearchCriteriaForm;
    }

    /**
     * 往路の空席照会条件 を取得する。
     * @return 往路の空席照会条件
     */
    public FlightSearchCriteriaForm getOutwardLineSearchCriteriaForm() {
        return outwardLineSearchCriteriaForm;
    }

    /**
     * 往路の空席照会条件 を設定する。
     * @param outwardLineSearchCriteriaForm 往路の空席照会条件
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
     * 最後に選択されたフライト情報フォームをリストより削除する。
     * <p>
     * 選択したフライト情報にエラーが存在する場合は、エラーが発生したフライト情報を削除するためのメソッドです。
     * </p>
     */
    public void removeLastSelectedFlightForm() {
        selectFlightFormList.remove(selectFlightFormList.size() - 1);
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
