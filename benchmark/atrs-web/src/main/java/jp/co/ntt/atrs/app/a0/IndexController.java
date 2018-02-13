/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.a0;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * インデックスアクセスを制御するコントローラ。
 * @author NTT 電電太郎
 */
@Controller
public class IndexController {

    /**
     * トップ画面を表示する。
     * @return View論理名
     */
    @RequestMapping(value = "/")
    public String homePage() {
        return "redirect:/Ticket/search?topForm";
    }
}
