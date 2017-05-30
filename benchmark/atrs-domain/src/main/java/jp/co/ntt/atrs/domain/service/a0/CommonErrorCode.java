/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.service.a0;

/**
 * 共通エラーコードを表す列挙型。
 * @author NTT 電電次郎
 */
public enum CommonErrorCode {

    /**
     * 日付として不正な値が指定された事を通知するためのエラーコード。
     */
    E_AR_FW_5001("e.ar.fw.5001");

    /**
     * エラーコード。
     */
    private final String code;

    /**
     * コンストラクタ。
     * @param code エラーコード。
     */
    private CommonErrorCode(String code) {
        this.code = code;
    }

    /**
     * エラーコードを取得する。
     * @return エラーコード
     */
    public String code() {
        return code;
    }

}
