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
