package com.catpaw.catpawmiddleware.utils;

public class LogUtils {

    public static String notNullFormat(String target) {
        return String.format("%s은(는) 값이 존재하지 않습니다.", target);
    }

    public static String notEmptyFormat(String target) {
        return String.format("%s은(는) 빈 값입니다.", target);
    }
}
