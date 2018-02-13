/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.exception;

import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

/**
 * 共通業務例外クラス。
 * @author NTT 電電太郎
 */
public class AtrsBusinessException extends BusinessException {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 9121582722143254974L;

    /**
     * コンストラクタ。
     * @param errorCode エラーコード
     * @param args 置き換え文字列
     */
    public AtrsBusinessException(AtrsErrorCode errorCode, Object... args) {
        super(ResultMessages.error().add(ResultMessage.fromCode(errorCode
                .code(), args)));
    }

}
