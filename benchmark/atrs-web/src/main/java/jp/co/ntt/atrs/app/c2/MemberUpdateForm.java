/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.c2;

import jp.co.ntt.atrs.app.c0.MemberForm;
import jp.co.ntt.atrs.domain.common.validate.HalfWidth;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * 会員情報変更フォーム。
 * @author NTT 電電花子
 */
public class MemberUpdateForm implements Serializable {
    /**
     * serialVersionUID。
     */
    private static final long serialVersionUID = -523583197805765367L;

    /**
     * 会員情報フォーム。
     */
    @Valid
    private MemberForm memberForm;

    /**
     * パスワード。
     */
    @Size(min = 8, max = 20)
    @HalfWidth
    private String password;

    /**
     * 再入力パスワード。
     */
    @Size(min = 8, max = 20)
    @HalfWidth
    private String reEnterPassword;

    /**
     * 現在のパスワード。
     */
    @Size(min = 8, max = 20)
    @HalfWidth
    private String nowPassword;

    /**
     * <p>
     * カード会員情報を取得します。
     * </p>
     * @return カード会員情報
     */
    public MemberForm getMemberForm() {
        return memberForm;
    }

    /**
     * <p>
     * カード会員情報を設定します。
     * </p>
     * @param memberForm カード会員情報
     */
    public void setMemberForm(MemberForm memberForm) {
        this.memberForm = memberForm;
    }

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
     * 現在のパスワードを取得します。
     * </p>
     * @return 確認用パスワード
     */
    public String getNowPassword() {
        return nowPassword;
    }

    /**
     * <p>
     * 現在のパスワードを設定します。
     * </p>
     * @param nowPassword 確認用パスワード
     */
    public void setNowPassword(String nowPassword) {
        this.nowPassword = nowPassword;
    }

}
