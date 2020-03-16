package com.demo.utils;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 演示Java的国际化
 * @author Abu
 *
 */
public class ParamFormat {

    /**
     * @param args
     */
    public static void main(String[] args) {

        // 测试缺省的资源文件
//        testDefault();

        // 测试美国英语的资源文件
        testCustom();
    }

    public static void testDefault() {
        // 获得缺省的系统区域
        Locale locale = Locale.getDefault();
        // 获得资源文件
        ResourceBundle rb = ResourceBundle.getBundle("message", locale);

        // 获得相应的key值
        String greeting = rb.getString("greeting");
        String userInfo = rb.getString("userinfo");


        String name= "迦南";
        int age = 18;
        Date birth = new Date();

        // 格式化参数,注意是一个Object的数组,这意味着可以将任何类型的对象来格式化模板.
        Object[] params = {name,age,birth};

        // 格式化参数,返回格式后的字符串
        String result = MessageFormat.format(userInfo, params);

        System.out.println(greeting + result);
    }

    public static void testCustom() {
        // 设置定制的语言国家代码
        Locale locale = new Locale("zh_CN");
        // 获得资源文件
        ResourceBundle rb = ResourceBundle.getBundle("message", locale);
        // 获得相应的key值

        String greeting = rb.getString("greeting");
        String accountRegesitSuccess = rb.getString("accountRegesitSuccess");
        String accountRegesitFailure = rb.getString("accountRegesitFailure");
        String accountRegesitEmailErr = rb.getString("accountRegesitEmailErr");
        String loginFailure = rb.getString("loginFailure");


        String name= "liky";
        int age = 18;
        Date birth = new Date();

        // 格式化参数,注意是一个Object的数组,这意味着可以将任何类型的对象来格式化模板.
        Object[] params = {name,age,birth};

        // 格式化参数,返回格式后的字符串
        String result = MessageFormat.format(greeting, params);

        System.out.println(greeting + result);
    }
}
