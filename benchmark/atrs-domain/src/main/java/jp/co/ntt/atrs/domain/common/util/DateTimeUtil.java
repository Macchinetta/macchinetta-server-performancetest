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
package jp.co.ntt.atrs.domain.common.util;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日時に関するユーティリティクラス。
 * @author NTT 電電太郎
 */
public class DateTimeUtil {

    /**
     * 時間(文字列)のパースに使用するフォーマッタ。
     */
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormat
            .forPattern("HHmm");

    /**
     * コンストラクタ。
     */
    private DateTimeUtil() {
        // 実装なし。
    }

    /**
     * DateTimeへ変換する。
     * @param date 日付オブジェクト
     * @param timeString 時刻文字列(HHmm)
     * @return 引数で指定された日付および時刻を保持するDateTimeオブジェクト
     */
    public static DateTime toDateTime(Date date, String timeString) {
        return new LocalDate(date).toDateTime(DateTimeUtil.toLocalTime(
                timeString));
    }

    /**
     * LocalTimeへ変換する。
     * @param timeString 時刻文字列(HHmm)
     * @return 引数で指定された時刻を保持するLocalTimeオブジェクト
     */
    public static LocalTime toLocalTime(String timeString) {
        return TIME_FORMATTER.parseLocalTime(timeString);
    }

    /**
     * 時刻(HHmm)の文字列から時(HH)を抜き出す。
     * @param timeString 時刻文字列(HHmm)
     * @return 時(HH)
     */
    public static String extractHourString(String timeString) {
        return timeString.substring(0, 2);
    }

    /**
     * 実在する日かどうかをチェックする。
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 実在する日であればtrue、そうでなければfalse
     */
    public static boolean isValidDate(int year, int month, int day) {
        try {
            new LocalDate(year, month, day);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

}
