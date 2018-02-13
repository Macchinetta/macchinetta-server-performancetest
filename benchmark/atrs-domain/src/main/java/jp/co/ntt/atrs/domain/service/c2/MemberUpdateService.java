/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.domain.service.c2;

import jp.co.ntt.atrs.domain.model.Member;

/**
 * 会員情報変更を行うServiceインタフェース。
 * @author NTT 電電花子
 */
public interface MemberUpdateService {

    /**
     * お客様番号に該当する会員情報を取得する。
     * @param customerNo お客様番号
     * @return Member お客様番号に該当するユーザの会員情報
     */
    Member findMember(String customerNo);

    /**
     * 会員情報を更新する。
     * @param member 会員情報
     */
    void updateMember(Member member);

    /**
     * 引数に渡されたパスワードがDBに登録されているパスワードと同一かチェックする。
     * @param password チェックするパスワード
     * @param customerNo パスワードを確認する会員のお客様番号
     */
    void checkMemberPassword(String password, String customerNo);
}
