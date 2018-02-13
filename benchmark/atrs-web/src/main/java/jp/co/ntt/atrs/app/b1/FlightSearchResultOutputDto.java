/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.b1;

import jp.co.ntt.atrs.domain.model.BoardingClassCd;
import jp.co.ntt.atrs.domain.service.b1.TicketSearchResultDto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 空席照会結果画面出力用DTO。
 * @author NTT 電電次郎
 */
public class FlightSearchResultOutputDto implements Serializable {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = -8890904965586980747L;

    /**
     * 搭乗日。
     */
    private Date depDate;

    /**
     * 選択されていない方の搭乗クラスコード。
     */
    private BoardingClassCd otherBoardingClassCd;

    /**
     * 前日ボタン表示フラグ。
     */
    private Boolean isDepDateAfterToday;

    /**
     * 翌日ボタン表示フラグ。
     */
    private Boolean isDepDateBeforeLimitDate;

    /**
     * 搭乗日の前日(月)。
     */
    private int monthOfPreviousDate;

    /**
     * 搭乗日の前日(日)。
     */
    private int dayOfPreviousDate;

    /**
     * 搭乗日の翌日(月)。
     */
    private int monthOfNextDate;

    /**
     * 搭乗日の翌日(日)。
     */

    private int dayOfNextDate;

    /**
     * 空席照会結果。
     */
    private TicketSearchResultDto ticketSearchResultDto;

    /**
     * 搭乗日を取得する。
     * @return depDate
     */
    public Date getDepDate() {
        return depDate;
    }

    /**
     * 搭乗日を設定する。
     * @param depDate depDate
     */
    public void setDepDate(Date depDate) {
        this.depDate = depDate;
    }

    /**
     * 選択されていない方の搭乗クラスコードを取得する。
     * @return 選択されていない方の搭乗コード
     */
    public BoardingClassCd getOtherBoardingClassCd() {
        return otherBoardingClassCd;
    }

    /**
     * 選択されていない方の搭乗コードを設定する。
     * @param otherBoardingClassCd 選択されていない方の搭乗コード
     */
    public void setOtherBoardingClassCd(BoardingClassCd otherBoardingClassCd) {
        this.otherBoardingClassCd = otherBoardingClassCd;
    }

    /**
     * 前日ボタン表示フラグを取得する。
     * @return 前日ボタン表示フラグ
     */
    public Boolean getIsDepDateAfterToday() {
        return isDepDateAfterToday;
    }

    /**
     * 前日ボタン表示フラグを設定する。
     * @param isDepDateAfterToday 前日ボタン表示フラグ
     */
    public void setIsDepDateAfterToday(Boolean isDepDateAfterToday) {
        this.isDepDateAfterToday = isDepDateAfterToday;
    }

    /**
     * 翌日ボタン表示フラグを取得する。
     * @return 翌日ボタン表示フラグ
     */
    public Boolean getIsDepDateBeforeLimitDate() {
        return isDepDateBeforeLimitDate;
    }

    /**
     * 翌日ボタン表示フラグを設定する。
     * @param isDepDateBeforeLimitDate 翌日ボタン表示フラグ
     */
    public void setIsDepDateBeforeLimitDate(Boolean isDepDateBeforeLimitDate) {
        this.isDepDateBeforeLimitDate = isDepDateBeforeLimitDate;
    }

    /**
     * 搭乗日の前日(月)を取得する。
     * @return 搭乗日の前日(月)
     */
    public int getMonthOfPreviousDate() {
        return monthOfPreviousDate;
    }

    /**
     * 搭乗日の前日(月)を設定する。
     * @param monthOfPreviousDate 搭乗日の前日(月)
     */
    public void setMonthOfPreviousDate(int monthOfPreviousDate) {
        this.monthOfPreviousDate = monthOfPreviousDate;
    }

    /**
     * 搭乗日の前日(日)を取得する。
     * @return 搭乗日の前日(日)
     */
    public int getDayOfPreviousDate() {
        return dayOfPreviousDate;
    }

    /**
     * 搭乗日の前日(日)を設定する。
     * @param dayOfPreviousDate 搭乗日の前日(日)
     */
    public void setDayOfPreviousDate(int dayOfPreviousDate) {
        this.dayOfPreviousDate = dayOfPreviousDate;
    }

    /**
     * 搭乗日の翌日(月)を取得する。
     * @return 搭乗日の翌日(月)
     */
    public int getMonthOfNextDate() {
        return monthOfNextDate;
    }

    /**
     * 搭乗日の翌日(月)を設定する。
     * @param monthOfNextDate 搭乗日の翌日(月)
     */
    public void setMonthOfNextDate(int monthOfNextDate) {
        this.monthOfNextDate = monthOfNextDate;
    }

    /**
     * 搭乗日の翌日(日)を取得する。
     * @return 搭乗日の翌日(日)
     */
    public int getDayOfNextDate() {
        return dayOfNextDate;
    }

    /**
     * 搭乗日の翌日(日)を設定する。
     * @param dayOfNextDate 搭乗日の翌日(日)
     */
    public void setDayOfNextDate(int dayOfNextDate) {
        this.dayOfNextDate = dayOfNextDate;
    }

    /**
     * 空席情報照会結果を取得する。
     * @return 空席情報照会結果
     */
    public TicketSearchResultDto getTicketSearchResultDto() {
        return ticketSearchResultDto;
    }

    /**
     * 空席情報照会結果を設定する。
     * @param ticketSearchResultDto 空席情報照会結果
     */
    public void setTicketSearchResultDto(
            TicketSearchResultDto ticketSearchResultDto) {
        this.ticketSearchResultDto = ticketSearchResultDto;
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
