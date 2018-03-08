/*
 * Copyright 2014-2018 NTT Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package jp.co.ntt.atrs.app.common.security;

import jp.co.ntt.atrs.domain.common.logging.LogMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ユーザーログイン入力チェックフィルタ。
 * @author NTT 電電太郎
 */
public class AtrsUsernamePasswordAuthenticationFilter extends
                                                      UsernamePasswordAuthenticationFilter {

    /**
     * ロガー。
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(
            AtrsUsernamePasswordAuthenticationFilter.class);

    /**
     * お客様番号文字数
     */
    private int customerNoLength = 10;

    /**
     * パスワードの最小桁数
     */
    private int passwordMinLength = 8;

    /**
     * お客様番号文字数を設定する。
     * <p>
     * デフォルトは10。
     * </p>
     * @param customerNoLength お客様番号文字数
     */
    public void setCustomerNoLength(int customerNoLength) {
        this.customerNoLength = customerNoLength;
    }

    /**
     * パスワードの最小桁数を設定する。
     * <p>
     * デフォルトは8。
     * </p>
     * @param passwordMinLength パスワードの最小桁数
     */
    public void setPasswordMinLength(int passwordMinLength) {
        this.passwordMinLength = passwordMinLength;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        if (username == null || username.length() != customerNoLength) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(LogMessages.I_AR_A1_L2001.getMessage(username));
            }
            String errorMessage = messages.getMessage("FixedLength",
                    new Object[] {
                            new DefaultMessageSourceResolvable("customerNo"),
                            customerNoLength }, Locale.getDefault());
            throw new UsernameNotFoundException(errorMessage);
        }

        if (password == null || password.length() < passwordMinLength) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info(LogMessages.I_AR_A1_L2002.getMessage(username));
            }
            String errorMessage = this.messages.getMessage("MinLength",
                    new Object[] {
                            new DefaultMessageSourceResolvable("password"),
                            passwordMinLength }, Locale.getDefault());
            throw new BadCredentialsException(errorMessage);
        }

        return super.attemptAuthentication(request, response);
    }

}
