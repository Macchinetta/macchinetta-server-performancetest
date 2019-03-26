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
package jp.co.ntt.atrs.app.b1;

import jp.co.ntt.atrs.domain.model.BoardingClassCd;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 空席照会条件入力画面出力用DTO。
 * @author NTT 電電次郎
 */
public class FlightSearchFormOutputDto implements Serializable {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 5875923730800681447L;

    /**
     * デフォルト搭乗日(月)。
     */
    private int defaultMonth;

    /**
     * デフォルト搭乗日(日)。
     */
    private int defaultDay;

    /**
     * デフォルト出発空港コード。
     */
    private String defaultDepAirportCd;

    /**
     * デフォルト到着空港コード。
     */
    private String defaultArrAirportCd;

    /**
     * デフォルト搭乗クラスコード。
     */
    private BoardingClassCd defaultBoardingClassCd;

    /**
     * デフォルト出発時刻。
     */
    private String defaultTime;

    /**
     * 空席照会可能時期(始)。
     */
    private Date beginningPeriod;

    /**
     * 空席照会可能時期(終)。
     */
    private Date endingPeriod;

    /**
     * デフォルト搭乗日(月) を取得する。
     * @return デフォルト搭乗日(月)
     */
    public int getDefaultMonth() {
        return defaultMonth;
    }

    /**
     * デフォルト搭乗日(月) を設定する。
     * @param defaultMonth デフォルト搭乗日(月)
     */
    public void setDefaultMonth(int defaultMonth) {
        this.defaultMonth = defaultMonth;
    }

    /**
     * デフォルト搭乗日(日) を取得する。
     * @return デフォルト搭乗日(日)
     */
    public int getDefaultDay() {
        return defaultDay;
    }

    /**
     * デフォルト搭乗日(日) を設定する。
     * @param defaultDay デフォルト搭乗日(日)
     */
    public void setDefaultDay(int defaultDay) {
        this.defaultDay = defaultDay;
    }

    /**
     * デフォルト出発空港コードを取得する。
     * @return デフォルト出発空港コード
     */
    public String getDefaultDepAirportCd() {
        return defaultDepAirportCd;
    }

    /**
     * デフォルト出発空港コードを設定する。
     * @param defaultDepAirportCd
     */
    public void setDefaultDepAirportCd(String defaultDepAirportCd) {
        this.defaultDepAirportCd = defaultDepAirportCd;
    }

    /**
     * デフォルト到着空港コードを取得する。
     * @return デフォルト到着空港コード
     */
    public String getDefaultArrAirportCd() {
        return defaultArrAirportCd;
    }

    /**
     * デフォルト到着空港コードを設定する。
     * @param defaultArrAirportCd
     */
    public void setDefaultArrAirportCd(String defaultArrAirportCd) {
        this.defaultArrAirportCd = defaultArrAirportCd;
    }

    /**
     * デフォルト到着空港コードを取得する。
     * @return デフォルト搭乗クラスコード
     */
    public BoardingClassCd getDefaultBoardingClassCd() {
        return defaultBoardingClassCd;
    }

    /**
     * デフォルト搭乗クラスコードを設定する。
     * @param defaultBoardingClassCd
     */
    public void setDefaultBoardingClassCd(
            BoardingClassCd defaultBoardingClassCd) {
        this.defaultBoardingClassCd = defaultBoardingClassCd;
    }

    /**
     * デフォルト出発時刻を取得する。
     * @return デフォルト出発時刻
     */
    public String getDefaultTime() {
        return defaultTime;
    }

    /**
     * デフォルト出発時刻を設定する。
     * @param defaultTime
     */
    public void setDefaultTime(String defaultTime) {
        this.defaultTime = defaultTime;
    }

    /**
     * 空席照会可能時期(始)を取得する。
     * @return 空席照会可能時期(始)
     */
    public Date getBeginningPeriod() {
        return beginningPeriod;
    }

    /**
     * 空席照会可能時期(始)を設定する。
     * @param beginningPeriod
     */
    public void setBeginningPeriod(Date beginningPeriod) {
        this.beginningPeriod = beginningPeriod;
    }

    /**
     * 空席照会可能時期(終)を取得する。
     * @return 空席照会可能時期(終)
     */
    public Date getEndingPeriod() {
        return endingPeriod;
    }

    /**
     * 空席照会可能時期(終)を設定する。
     * @param endingPeriod
     */
    public void setEndingPeriod(Date endingPeriod) {
        this.endingPeriod = endingPeriod;
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
