package com.demo.utils;

import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 演示Java的国际化
 *
 * @author Abu
 */
public class I18nUtil {
    public static String getI18nMessage(String language, String key) {
        // 设置定制的语言国家代码
        Locale locale = null;
        if (StringUtils.isEmpty(language)) {
            locale = Locale.getDefault();
        } else {
            locale = new Locale(language);
        }
        // 获得资源文件
        ResourceBundle rb = ResourceBundle.getBundle("message", locale);
        // 获得相应的key值
        return rb.getString(key);
    }

    public static String getI18nMessage(String language, String key, Object[] params) {
        // 设置定制的语言国家代码
        Locale locale = null;
        if (StringUtils.isEmpty(language)) {
            locale = Locale.getDefault();
        } else {
            locale = new Locale(language);
        }
        // 获得资源文件
        ResourceBundle rb = ResourceBundle.getBundle("message", locale);
        // 获得相应的key值
        String value = rb.getString(key);
        // 格式化参数,返回格式后的字符串
        return MessageFormat.format(value, params);
    }
}
