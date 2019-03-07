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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 申し込み内容確認画面出力用DTO。
 * @author NTT 電電三郎
 */
public class ReserveConfirmOutputDto {

    /**
     * 予約チケットの合計金額。
     */
    private int totalFare;

    /**
     * 選択したフライト情報リスト。
     */
    private List<SelectFlightDto> selectFlightDtoList;

    /**
     * 予約チケットの合計金額 を取得する。
     * @return 予約チケットの合計金額
     */
    public int getTotalFare() {
        return totalFare;
    }

    /**
     * 予約チケットの合計金額 を設定する。
     * @param totalFare 予約チケットの合計金額
     */
    public void setTotalFare(int totalFare) {
        this.totalFare = totalFare;
    }

    /**
     * 選択したフライト情報リスト を取得する。
     * @return 選択したフライト情報リスト
     */
    public List<SelectFlightDto> getSelectFlightDtoList() {
        return selectFlightDtoList;
    }

    /**
     * 選択したフライト情報リスト を設定する。
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
