package com.demo.controller;

import com.demo.DemoApplicationEntrance;
import com.demo.constants.I18nKeys;
import com.demo.service.AccountService;
import com.demo.utils.I18nUtil;
import com.demo.vo.AccountVo;
import com.demo.vo.MessageVo;
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

@RestController
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplicationEntrance.class);
    private static final String LANGUAGE = "language";

    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/account/register")
    public MessageVo register(AccountVo accountVo, HttpServletRequest request) {
        //        String headerLanguage = request.getHeader(LANGUAGE);
        String headerLanguage = "zh_CN";
        MessageVo vo = new MessageVo();
        vo.setUserName(accountVo.getName());
        try {
            accountVo.setRemoteIp(request.getRemoteHost());
            accountService.regesit(accountVo);
            String sucMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.accountRegesitSuccess);
            vo.setResult(true);
            vo.setMsg(sucMsg);
            return vo;
        } catch (Exception e) {
            String failMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.accountRegesitFailure);
            vo.setResult(false);
            vo.setMsg(failMsg);
            return vo;
        }
    }

    @GetMapping(path = "/account/active")
    public MessageVo active(@RequestParam(name = "email") String email,
                         @RequestParam(name = "activeCode") String activeCode,
                         HttpServletRequest request) {
        //        String headerLanguage = request.getHeader(LANGUAGE);
        String headerLanguage = "zh_CN";
        MessageVo vo = new MessageVo();
        try {
            accountService.active(email, activeCode);
            String sucMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.accountActiveSuccess);
            vo.setResult(true);
            vo.setMsg(sucMsg);
            return vo;
        } catch (Exception e) {
            String failMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.accountActiveFailure);
            vo.setResult(false);
            vo.setMsg(failMsg);
            return vo;
        }
    }

    @PostMapping(path = "/account/login")
    public String login(AccountVo vo,HttpServletRequest request) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(vo.getEmail(), vo.getPassword());
        try {;
            // 执行认证登陆
            subject.login(token);
        }catch (UnknownAccountException uae) {
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

