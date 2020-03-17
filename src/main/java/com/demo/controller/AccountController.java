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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
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
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(account.getEmail(), account.getPassword());
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return "未知账户";
        } catch (IncorrectCredentialsException ice) {
            return "密码不正确";
        } catch (LockedAccountException lae) {
            return "账户已锁定";
        } catch (ExcessiveAttemptsException eae) {
            return "用户名或密码错误次数过多";
        } catch (AuthenticationException ae) {
            return "用户名或密码不正确！";
        }
        if (subject.isAuthenticated()) {
            return "登录成功";
        } else {
            token.clear();
            return "登录失败";
        }
    }

}

