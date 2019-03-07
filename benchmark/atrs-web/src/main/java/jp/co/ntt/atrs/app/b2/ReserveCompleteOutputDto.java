/*
 * Copyright(c) 2014 NTT Corporation.
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
package jp.co.ntt.atrs.app.b2;

import jp.co.ntt.atrs.app.b0.SelectFlightDto;
import jp.co.ntt.atrs.domain.service.b2.TicketReserveDto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * チケット予約完了画面出力用DTO。
 * @author NTT 電電三郎
 */
public class ReserveCompleteOutputDto implements Serializable {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 8669484338093682534L;

    /**
     * 予約情報DTO。
     */
    private TicketReserveDto ticketReserveDto;

    /**
     * 予約チケットの合計金額。
     */
    private int totalFare;

    /**
     * 予約情報フォーム。
     */
    private ReservationForm reservationForm;

    /**
     * 選択したフライト情報リスト。
     */
    private List<SelectFlightDto> selectFlightDtoList;

    /**
     * 予約情報DTOを取得する。
     * @return 予約情報DTO
     */
    public TicketReserveDto getReserveInfo() {
        return ticketReserveDto;
    }

    /**
     * 予約情報DTOを設定する。
     * @param ticketReserveDto 予約情報DTO
     */
    public void setReserveInfo(TicketReserveDto ticketReserveDto) {
        this.ticketReserveDto = ticketReserveDto;
    }

    /**
     * 予約チケットの合計金額を取得する。
     * @return 予約チケットの合計金額
     */
    public int getTotalFare() {
        return totalFare;
    }

    /**
     * 予約チケットの合計金額を設定する。
     * @param totalFare 予約チケットの合計金額
     */
    public void setTotalFare(int totalFare) {
        this.totalFare = totalFare;
    }

    /**
     * 予約情報を取得する。
     * @return 予約情報
     */
    public ReservationForm getReservationForm() {
        return reservationForm;
    }

    /**
     * 予約情報を設定する。
     * @param reservationForm 予約情報
     */
    public void setReservationForm(ReservationForm reservationForm) {
        this.reservationForm = reservationForm;
    }

    /**
     * 選択したフライト情報リストを取得する。
     * @return 選択したフライト情報リスト
     */
    public List<SelectFlightDto> getSelectFlightDtoList() {
        return selectFlightDtoList;
    }

    /**
     * 選択したフライト情報リストを設定する。
     * @param selectFlightDtoList 選択したフライト情報リスト
     */
    public void setSelectFlightDtoList(
            List<SelectFlightDto> selectFlightDtoList) {
        this.selectFlightDtoList = selectFlightDtoList;
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
