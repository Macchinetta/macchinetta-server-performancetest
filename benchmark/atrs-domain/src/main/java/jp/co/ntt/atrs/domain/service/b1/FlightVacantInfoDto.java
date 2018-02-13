/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.service.b1;

import jp.co.ntt.atrs.domain.model.FareTypeCd;
import jp.co.ntt.atrs.domain.model.FlightMaster;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 便ごとの空席照会結果を格納するDTO。
 * @author NTT 電電次郎
 */
public class FlightVacantInfoDto implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = 5447225836186041285L;

    /**
     * フライト基本情報。
     */
    private FlightMaster flightMaster;

    /**
     * 運賃種別ごとの空席照会結果を格納するマップ。
     */
    private Map<FareTypeCd, FareTypeVacantInfoDto> fareTypeVacantInfoMap = new HashMap<>();

    /**
     * フライト基本情報を取得する。
     * @return フライト基本情報
     */
    public FlightMaster getFlightMaster() {
        return flightMaster;
    }

    /**
     * フライト基本情報を設定する。
     * @param flightMaster
     */
    public void setFlightMaster(FlightMaster flightMaster) {
        this.flightMaster = flightMaster;
    }

    /**
     * 運賃種別情報マップを取得する。
     * @return 運賃種別情報マップ
     */
    public Map<FareTypeCd, FareTypeVacantInfoDto> getFareTypeVacantInfoMap() {
        return fareTypeVacantInfoMap;
    }

    /**
     * 運賃種別情報を追加する。
     * @param fareTypeCd 運賃種別コード
     * @param fareTypeVacantInfo 運賃種別情報
     */
    public void addFareTypeVacantInfo(FareTypeCd fareTypeCd,
            FareTypeVacantInfoDto fareTypeVacantInfo) {
        fareTypeVacantInfoMap.put(fareTypeCd, fareTypeVacantInfo);
    }

    /**
     * 運賃種別情報マップを設定する。
     * @param fareTypeVacantInfoMap 運賃種別情報マップ
     */
    public void setFareTypeVacantInfoMap(
            Map<FareTypeCd, FareTypeVacantInfoDto> fareTypeVacantInfoMap) {
        this.fareTypeVacantInfoMap = fareTypeVacantInfoMap;
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
