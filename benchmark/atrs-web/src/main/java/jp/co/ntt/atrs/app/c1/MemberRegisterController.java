/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.c1;

import jp.co.ntt.atrs.app.c0.MemberHelper;
import jp.co.ntt.atrs.app.common.exception.BadRequestException;
import jp.co.ntt.atrs.domain.model.Member;
import jp.co.ntt.atrs.domain.model.MemberLogin;
import jp.co.ntt.atrs.domain.service.c1.MemberRegisterService;

import org.dozer.Mapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenCheck;
import org.terasoluna.gfw.web.token.transaction.TransactionTokenType;

import javax.inject.Inject;

/**
 * 会員情報登録コントローラ。
 * @author NTT 電電花子
 */
@Controller
@RequestMapping("Member/register")
@TransactionTokenCheck("Member/register")
public class MemberRegisterController {

    /**
     * 会員情報登録サービス。
     */
    @Inject
    MemberRegisterService memberRegisterService;

    /**
     * 会員情報登録フォームのバリデータ。
     */
    @Inject
    MemberRegisterValidator memberRegisterValidator;

    /**
     * Beanマッパー。
     */
    @Inject
    Mapper beanMapper;

    /**
     * 会員情報Helper。
     */
    @Inject
    MemberHelper memberHelper;

    /**
     * 会員情報登録フォームのバリデータをバインダに追加する。
     * @param binder バインダ
     */
    @InitBinder("memberRegisterForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(memberRegisterValidator);
    }

    /**
     * 会員情報登録フォームを初期化する。
     * @return 会員情報登録フォーム
     */
    @ModelAttribute("memberRegisterForm")
    public MemberRegisterForm setUpForm() {
        MemberRegisterForm memberRegisterForm = new MemberRegisterForm();
        return memberRegisterForm;
    }

    /**
     * ヘッダからユーザ情報登録画面を呼び出す。
     * @return View論理名
     */
    @RequestMapping(method = RequestMethod.GET, params = "form")
    public String registerForm() {
        return "C1/memberRegisterForm";
    }

    /**
     * 登録情報確認画面へ遷移する。
     * @param memberRegisterForm 会員情報登録フォーム
     * @param result チェック結果
     * @param model 出力情報を保持するクラス
     * @return View論理名
     */
    @TransactionTokenCheck(type = TransactionTokenType.BEGIN)
    @RequestMapping(method = RequestMethod.POST, params = "confirm")
    public String registerConfirm(
            @Validated MemberRegisterForm memberRegisterForm,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return registerRedo(memberRegisterForm, model);
        }
        return "C1/memberRegisterConfirm";
    }

    /**
     * ユーザ情報登録画面を再表示する。
     * @param memberRegisterForm 会員情報登録フォーム。
     * @param model 出力情報を保持するクラス
     * @return View論理名
     */
    @RequestMapping(params = "redo", method = RequestMethod.POST)
    public String registerRedo(MemberRegisterForm memberRegisterForm,
            Model model) {
        return "C1/memberRegisterForm";
    }

    /**
     * 入力された会員情報をDBへ登録する。
     * @param memberRegisterForm 会員情報登録フォーム
     * @param model 出力情報を保持するクラス
     * @param result チェック結果
     * @param redirectAttributes フラッシュスコープ格納用オブジェクト
     * @return View論理名
     */
    @TransactionTokenCheck
    @RequestMapping(method = RequestMethod.POST)
    public String register(@Validated MemberRegisterForm memberRegisterForm,
            BindingResult result, Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            throw new BadRequestException(result);
        }

        // 入力情報をMemberオブジェクトに格納
        Member member = memberHelper.formToMember(memberRegisterForm
                .getMemberForm());
        MemberLogin memberLogin = new MemberLogin();
        memberLogin.setPassword(memberRegisterForm.getPassword());
        member.setMemberLogin(memberLogin);

        // DBへの登録を行う。
        // MyBatis3のSelectKeyでCustomerNoを格納したmemberを取得する。
        member = memberRegisterService.register(member);

        // 入力情報をregisterCompleteへリダイレクトする準備
        redirectAttributes.addFlashAttribute(member);

        return "redirect:/Member/register?complete";
    }

    /**
     * 会員情報登録後、登録完了画面へ遷移する。
     * @return View論理名
     */
    @RequestMapping(params = "complete", method = RequestMethod.GET)
    public String registerComplete() {
        return "C1/memberRegisterComplete";
    }

}
