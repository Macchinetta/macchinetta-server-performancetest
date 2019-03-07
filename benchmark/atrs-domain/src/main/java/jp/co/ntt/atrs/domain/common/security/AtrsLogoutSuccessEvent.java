/*
 * Copyright(c) 2014 NTT Corporation.
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
package jp.co.ntt.atrs.domain.common.security;

import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;

/**
 * ログアウト成功イベントクラス。
 * @author NTT 電電太郎
 */
public class AtrsLogoutSuccessEvent extends AbstractAuthenticationEvent {

    /**
     * シリアルバージョンUID。
     */
    private static final long serialVersionUID = 1024168737113170485L;

    /**
     * コンストラクタ。
     * @param authentication 認証情報
     */
    public AtrsLogoutSuccessEvent(Authentication authentication) {
        super(authentication);
    }
}
