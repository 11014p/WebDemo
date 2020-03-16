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
        accountService.regesit(account);
//        String headerLanguage = request.getHeader(LANGUAGE);
        String headerLanguage ="zh_CN";
        String i18nMessage = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.accountRegesitSuccess);
        return i18nMessage;
    }

    @PostMapping(path = "/account/login")
    public boolean login(Account account) {
        return accountService.login(account);
    }


}

