/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.common.security;

import javax.inject.Inject;

import jp.co.ntt.atrs.domain.service.a1.AtrsUserDetails;
import jp.co.ntt.atrs.domain.service.a2.AuthLogoutService;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * ログアウト成功イベントハンドリングクラス。
 * @author NTT 電電太郎
 */
@Component
public class AtrsLogoutSucessListener
                                     implements
                                     ApplicationListener<AtrsLogoutSuccessEvent> {

    /**
     * 会員ログアウトサービス。
     */
    @Inject
    AuthLogoutService authLogoutService;

    /**
     * {@inheritDoc}
     * <p>
     * ログアウト成功後に会員ログインステータスを更新する。
     * </p>
     */
    @Override
    public void onApplicationEvent(AtrsLogoutSuccessEvent event) {
        Authentication authentication = event.getAuthentication();

        AtrsUserDetails userDetails = (AtrsUserDetails) authentication
                .getPrincipal();
        authLogoutService.logout(userDetails.getMember());

    }
}
