/*
 * Copyright 2014-2018 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package jp.co.ntt.atrs.domain.common.codelist;

import jp.co.ntt.atrs.domain.service.b0.TicketSharedService;

import org.joda.time.LocalDate;
import org.springframework.util.Assert;
import org.terasoluna.gfw.common.codelist.AbstractReloadableCodeList;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * 空席照会可能な月のコードリストクラス。
 * <p>
 * キー：月，値：月名
 * </p>
 * @author NTT 電電太郎
 */
public class SearchAvailableMonthCodeList extends AbstractReloadableCodeList {

    /**
     * チケット予約共通サービス。
     */
    @Inject
    TicketSharedService ticketSharedService;

    /**
     * システム日付取得用インターフェース。
     */
    @Inject
    JodaTimeDateFactory dateFactory;

    /**
     * ラベルのフォーマット。
     */
    private String labelFormat = "%d月";

    /**
     * ラベルのフォーマットを設定する。
     * <p>
     * デフォルトは「"%d月"」。
     * </p>
     * @param labelFormat ラベルのフォーマット
     */
    public void setLabelFormat(String labelFormat) {
        this.labelFormat = labelFormat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Map<String, String> retrieveMap() {

        // システム日付
        LocalDate sysDate = dateFactory.newDateTime().toLocalDate();

        // 照会可能限界日付
        LocalDate searchLimitDate = ticketSharedService.getSearchLimitDate();

        // 月コードリストマップ
        Map<String, String> monthsMap = new LinkedHashMap<>();

        // システム日付の月から照会可能限界日付の月までをコードリストに追加する。
        LocalDate firstDateOfMonth = sysDate.dayOfMonth().withMinimumValue();
        while (firstDateOfMonth.compareTo(searchLimitDate) <= 0) {
            String value = Integer.toString(firstDateOfMonth.getMonthOfYear());
            String label = String.format(labelFormat, firstDateOfMonth
                    .getMonthOfYear());
            monthsMap.put(value, label);
            firstDateOfMonth = firstDateOfMonth.plusMonths(1);
        }

        return monthsMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() {
        Assert.hasLength(labelFormat, "labelFormat is empty");
        super.afterPropertiesSet();
    }

}
