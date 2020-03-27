package com.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * 演示Java的国际化
 *
 * @author Abu
 */
public class I18nUtil {
    private static final Logger logger = LoggerFactory.getLogger(I18nUtil.class);

    private static String language_zh="zh";
    private static String message_zh_path="/message_zh.properties";
    private static String language_en="en";
    private static String message_en_path="/message_en.properties";
    private static Map<String,Properties> messageMap=new HashMap();
    //init
    static {
        try {
            Properties zhProperties=new Properties();
            zhProperties.load(I18nUtil.class.getResourceAsStream(message_zh_path));
            messageMap.put(language_zh,zhProperties);

            Properties enProperties=new Properties();
            enProperties.load(I18nUtil.class.getResourceAsStream(message_en_path));
            messageMap.put(language_en,enProperties);
        } catch (IOException e) {
            logger.error("message properties init failure.",e);
        }
    }
    public static String getI18nMessage(String language, String key) {
        Properties properties = messageMap.get(language);
        return properties.get(key).toString();
    }

}
