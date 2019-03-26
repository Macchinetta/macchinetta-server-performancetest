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
package jp.co.ntt.atrs.domain.service.b1;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 運賃種別に対応する空席照会結果を格納するDTO。
 * @author NTT 電電太郎
 */
public class FareTypeVacantInfoDto implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 7155663832395971345L;

    /**
     * 運賃。
     */
    private final int fare;

    /**
     * 空席数。
     */
    private final int vacantNum;

    /**
     * コンストラクタ。
     * @param fare 運賃
     * @param vacantNum 空席数
     */
    public FareTypeVacantInfoDto(int fare, int vacantNum) {
        this.fare = fare;
        this.vacantNum = vacantNum;
    }

    /**
     * 運賃を取得する。
     * @return 運賃
     */
    public int getFare() {
        return fare;
    }

    /**
     * 空席数を取得する。
     * @return 空席数
     */
    public int getVacantNum() {
        return vacantNum;
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
