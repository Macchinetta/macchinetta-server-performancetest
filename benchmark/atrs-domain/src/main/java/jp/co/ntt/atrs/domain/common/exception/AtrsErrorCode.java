/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.exception;

/**
 * エラーコードの共通インタフェース。
 * @author NTT 電電太郎
 */
public interface AtrsErrorCode {

    /**
     * エラーコードのコード値を取得する。
     * @return エラーコードのコード値
     */
    String code();

}
