package com.demo.utils;

/**
 * 正则表达式工具类
 */
public class RegexUtil {
    //邮箱正则表达式
    private static final String EMAIL_REGEX="[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+";

    //邮箱校验
    public static boolean checkEmailPattern(String email){
        return email.matches(EMAIL_REGEX);
    }
}
