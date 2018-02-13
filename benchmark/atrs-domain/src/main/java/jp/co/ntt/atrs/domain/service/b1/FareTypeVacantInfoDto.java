/*
 * Copyright(c) 2014-2017 NTT Corporation.
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
