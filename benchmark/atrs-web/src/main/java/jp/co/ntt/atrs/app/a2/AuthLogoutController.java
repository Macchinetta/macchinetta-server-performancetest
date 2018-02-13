/*
 * Copyright(c) 2014-2017 NTT Corporation.
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
