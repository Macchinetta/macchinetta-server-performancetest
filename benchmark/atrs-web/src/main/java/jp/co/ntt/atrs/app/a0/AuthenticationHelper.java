/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.a0;

import jp.co.ntt.atrs.domain.service.a1.AtrsUserDetails;

import org.springframework.stereotype.Component;

/**
 * 認証情報を扱うためのヘルパークラス。
 * @author NTT 電電太郎
 */
@Component
public class AuthenticationHelper {

    /**
     * 認証済みのユーザか確認する。
     * @param userDetails 認証情報を保持するオブジェクト
     * @return 認証済みのユーザの場合は<code>true</code>を返却。
     */
    public boolean isAuthenticatedPrincipal(AtrsUserDetails userDetails) {
        return (userDetails != null);
    }

}
