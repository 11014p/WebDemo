package com.demo.controller;

import com.demo.DemoApplicationEntrance;
import com.demo.constants.I18nKeys;
import com.demo.model.Account;
import com.demo.service.AccountService;
import com.demo.service.ExampleService;
import com.demo.utils.I18nUtil;
import com.demo.utils.RegexUtil;
import com.google.common.base.Preconditions;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplicationEntrance.class);
    private static final String LANGUAGE = "language";

    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/account/register")
    public String register(Account account, HttpServletRequest request) {
        //        String headerLanguage = request.getHeader(LANGUAGE);
        String headerLanguage = "zh_CN";
        try{
            accountService.regesit(account);
            String sucMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.accountRegesitSuccess);
            return sucMsg;
        }catch(Exception e){
            String failMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.accountRegesitFailure);
            return failMsg;
        }
    }

    @GetMapping(path = "/account/active")
    public String active(@RequestParam(name = "email") String email,
                         @RequestParam(name = "activeCode") String activeCode,
                         HttpServletRequest request) {
        //        String headerLanguage = request.getHeader(LANGUAGE);
        String headerLanguage = "zh_CN";
        try{
            accountService.active(email,activeCode);
            String sucMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.accountActiveSuccess);
            return sucMsg;
        }catch(Exception e){
            String failMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.accountActiveFailure);
            return failMsg;
        }
    }

    @PostMapping(path = "/account/login")
    public String login(Account account) {
        //        String headerLanguage = request.getHeader(LANGUAGE);
        String headerLanguage = "zh_CN";
        try{
            Account resultAccount=accountService.login(account);
            String sucMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.greeting,new Object[]{resultAccount.getName()});
            return sucMsg;
        }catch(Exception e){
            String failMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.loginFailure);
            return failMsg;
        }
    }


}

