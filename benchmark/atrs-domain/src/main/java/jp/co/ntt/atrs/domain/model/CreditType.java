/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * クレジットカード種別情報。
 * @author NTT 電電太郎
 */
public class CreditType implements Serializable {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 416170904782850822L;

    /**
     * クレジットカード種別コード。
     */
    private String creditTypeCd;

    /**
     * クレジットカード会社。
     */
    private String creditFirm;

    /**
     * クレジットカード種別コードを取得する。
     * @return クレジットカード種別コード
     */
    public String getCreditTypeCd() {
        return creditTypeCd;
    }

    /**
     * クレジットカード種別コードを設定する。
     * @param creditTypeCd クレジットカード種別コード
     */
    public void setCreditTypeCd(String creditTypeCd) {
        this.creditTypeCd = creditTypeCd;
    }

    /**
     * クレジットカード会社を取得する。
     * @return クレジットカード会社
     */
    public String getCreditFirm() {
        return creditFirm;
    }

    /**
     * クレジットカード会社を設定する。
     * @param creditFirm クレジットカード会社
     */
    public void setCreditFirm(String creditFirm) {
        this.creditFirm = creditFirm;
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
