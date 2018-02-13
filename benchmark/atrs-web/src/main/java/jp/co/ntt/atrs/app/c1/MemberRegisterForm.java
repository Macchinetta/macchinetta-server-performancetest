/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.c1;

import jp.co.ntt.atrs.app.c0.MemberForm;
import jp.co.ntt.atrs.domain.common.validate.HalfWidth;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 会員情報登録フォーム。
 * @author NTT 電電花子
 */
public class MemberRegisterForm implements Serializable {

    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = -6636167932745093781L;

    /**
     * 会員情報フォーム。
     */
    @Valid
    private MemberForm memberForm;

    /**
     * パスワード。
     */
    @NotNull
    @Size(min = 8, max = 20)
    @HalfWidth
    private String password;

    /**
     * 再入力パスワード。
     */
    @NotNull
    @Size(min = 8, max = 20)
    @HalfWidth
    private String reEnterPassword;

    /**
     * <p>
     * パスワードを取得します。
     * </p>
     * @return パスワード
     */

    public String getPassword() {
        return password;
    }

    /**
     * <p>
     * パスワードを設定します。
     * </p>
     * @param password パスワード
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>
     * 再入力パスワードを取得します。
     * </p>
     * @return 再入力パスワード
     */

    public String getReEnterPassword() {
        return reEnterPassword;
    }

    /**
     * <p>
     * 再入力パスワードを設定します。
     * </p>
     * @param reEnterPassword 再入力パスワード
     */

    public void setReEnterPassword(String reEnterPassword) {
        this.reEnterPassword = reEnterPassword;
    }

    /**
     * <p>
     * 会員情報フォームを取得します。
     * </p>
     * @return 会員情報フォーム
     */

    public MemberForm getMemberForm() {
        return memberForm;
    }

    /**
     * <p>
     * 会員情報フォームを設定します。
     * </p>
     * @param memberForm 会員情報フォーム
     */
    public void setMemberForm(MemberForm memberForm) {
        this.memberForm = memberForm;
    }

}
