package com.demo.controller;

import com.demo.DemoApplicationEntrance;
import com.demo.model.Account;
import com.demo.service.AccountService;
import com.demo.service.ExampleService;
import com.demo.utils.RegexUtil;
import com.google.common.base.Preconditions;
import com.mysql.jdbc.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AccountController {
    private static final Logger logger= LoggerFactory.getLogger(DemoApplicationEntrance.class);
    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/account/register")
    public void register(Account account) {
        accountService.regesit(account);
    }

    @GetMapping(path = "/account/login")
    public boolean login(Account account) {
        return accountService.login(account);
    }


}

