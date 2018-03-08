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
package jp.co.ntt.atrs.app.a2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * ログアウトコントローラ。
 * @author NTT 電電太郎
 */
@Controller
@RequestMapping(value = "Auth/logout")
public class AuthLogoutController {

    /**
     * ログアウト確認画面を表示する。
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "confirm")
    public String logoutConfirm() {
        return "A2/logoutConfirm";
    }

    /**
     * ログアウト完了画面を表示する。
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "complete")
    public String logoutComplete() {
        return "A2/logoutComplete";
    }

}
