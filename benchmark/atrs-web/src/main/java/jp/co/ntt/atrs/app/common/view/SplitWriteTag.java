/*
 * Copyright(c) 2014-2017 NTT Corporation.
 */
package jp.co.ntt.atrs.app.common.view;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.util.StringUtils;

/**
 * 文字列操作の拡張タグクラス。
 * <p>
 * split,position属性を設定すると指定した位置:postionで文字を分割し、 splitを間に連結して表示します。
 * </p>
 * @author NTT 電電太郎
 */
public class SplitWriteTag extends TagSupport {

    /**
     * デフォルトシリアルバージョンUID。
     */
    private static final long serialVersionUID = -4992272989725466110L;

    /**
     * 分割したい文字列。
     */
    private String str;

    /**
     * splitした文字列の間に挟む文字。
     */
    private String split;

    /**
     * splitする文字の何番目か。
     */
    private String position;

    /**
     * {@inheritDoc}
     */
    @Override
    public int doStartTag() throws JspException {

        if (str == null) {
            return SKIP_BODY;
        }

        if (StringUtils.hasLength(split) && StringUtils.hasLength(position)) {

            int positionInt = Integer.parseInt(position);
            String s1 = str.substring(0, positionInt);
            String s2 = str.substring(positionInt);

            String output = (s1 + split + s2);

            try {
                pageContext.getOut().print(output);
            } catch (IOException e) {
                throw new JspException(e.getMessage(), e);
            }
        }

        return SKIP_BODY;
    }

    /**
     * 分割したい文字列 を取得する。
     * @return 分割したい文字列
     */
    public String getStr() {
        return str;
    }

    /**
     * 分割したい文字列 を設定する。
     * @param str 分割したい文字列
     */
    public void setStr(String str) {
        this.str = str;
    }

    /**
     * splitした文字列の間に挟む文字 を取得する。
     * @return splitした文字列の間に挟む文字
     */
    public String getSplit() {
        return split;
    }

    /**
     * splitした文字列の間に挟む文字 を設定する。
     * @param split splitした文字列の間に挟む文字
     */
    public void setSplit(String split) {
        this.split = split;
    }

    /**
     * splitする文字の何番目か を取得する。
     * @return splitする文字の何番目か
     */
    public String getPosition() {
        return position;
    }

    /**
     * splitする文字の何番目か を設定する。
     * @param position splitする文字の何番目か
     */
    public void setPosition(String position) {
        this.position = position;
    }
}
