package com.demo.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.demo.DemoApplicationEntrance;
import com.demo.constants.I18nKeys;
import com.demo.service.AccountService;
import com.demo.utils.I18nUtil;
import com.demo.vo.AccountVo;
import com.demo.vo.FindpwdRecordVo;
import com.demo.vo.MessageVo;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(DemoApplicationEntrance.class);
    private static final String LANGUAGE = "language";

    @Autowired
    private AccountService accountService;

    @PostMapping(path = "/account/register")
    public MessageVo register(@RequestBody AccountVo accountVo, HttpServletRequest request) {
        String headerLanguage = request.getHeader(LANGUAGE);
        logger.info("headerLanguage:{}", headerLanguage);
        logger.info("register accountVo:{}", accountVo);
        MessageVo vo = new MessageVo();
        vo.setUserName(accountVo.getName());
        try {
            accountVo.setRemoteIp(request.getRemoteHost());
            accountService.regesit(accountVo);
            String sucMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.accountRegesitSuccess);
            vo.setResult(true);
            vo.setMsg(sucMsg);
            return vo;
        } catch (ClientException e) {
            logger.error("register error", e);
            String failMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.manMachineCheckFailure);
            vo.setResult(false);
            vo.setMsg(failMsg);
            return vo;
        } catch (Exception e) {
            logger.error("register error", e);
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
        String headerLanguage = request.getHeader(LANGUAGE);
        logger.info("headerLanguage:{}", headerLanguage);
        logger.info("active email:{},activeCode{}", email, activeCode);
        MessageVo vo = new MessageVo();
        try {
            accountService.active(email, activeCode);
            String sucMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.accountActiveSuccess);
            vo.setResult(true);
            vo.setMsg(sucMsg);
            return vo;
        } catch (Exception e) {
            logger.error("active error", e);
            String failMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.accountActiveFailure);
            vo.setResult(false);
            vo.setMsg(failMsg);
            return vo;
        }
    }

    @PostMapping(path = "/account/login")
    public MessageVo login(@RequestBody AccountVo accountVo, HttpServletRequest request) {
        String headerLanguage = request.getHeader(LANGUAGE);
        logger.info("login accountVo:{}", accountVo);
        logger.info("headerLanguage:{}", headerLanguage);
        try {
            MessageVo messageVo = accountService.login(accountVo);
            String sucMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.greeting);
            messageVo.setResult(true);
            messageVo.setMsg(sucMsg);
            return messageVo;
        } catch (ClientException e) {
            logger.error("register error", e);
            String failMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.manMachineCheckFailure);
            MessageVo messageVo = new MessageVo();
            messageVo.setResult(false);
            messageVo.setMsg(failMsg);
            return messageVo;
        } catch (Exception e) {
            logger.error("login error", e);
            String failMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.loginFailure);
            MessageVo messageVo = new MessageVo();
            messageVo.setResult(false);
            messageVo.setMsg(failMsg);
            return messageVo;
        }
    }

    @PostMapping(path = "/account/password/forget")
    public MessageVo pwdForget(@RequestBody FindpwdRecordVo findpwdRecordVo, HttpServletRequest request) {
        String headerLanguage = request.getHeader(LANGUAGE);
        logger.info("headerLanguage:{}", headerLanguage);
        logger.info("forget findpwdRecordVo:{}", findpwdRecordVo);
        MessageVo messageVo = new MessageVo();
        try {
            accountService.passwordForget(findpwdRecordVo);
//            String sucMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.greeting);
            messageVo.setResult(true);
            messageVo.setMsg("密码重置链接邮件已发送到邮箱，请前往邮箱点击链接重置密码。");
            return messageVo;
        } catch (Exception e) {
            logger.error("pwd forget error", e);
//            String failMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.loginFailure);
            messageVo.setResult(false);
            messageVo.setMsg("忘记密码处理失败。");
            return messageVo;
        }
    }

    @PostMapping(path = "/account/password/reset")
    public MessageVo pwdReset(@RequestBody FindpwdRecordVo findpwdRecordVo, HttpServletRequest request) {
        String headerLanguage = request.getHeader(LANGUAGE);
        logger.info("headerLanguage:{}", headerLanguage);
        MessageVo messageVo = new MessageVo();
        try {
            accountService.passwordReset(findpwdRecordVo);
            String sucMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.greeting);
            messageVo.setResult(true);
            messageVo.setMsg(sucMsg);
            return messageVo;
        } catch (Exception e) {
            logger.error("pwd reset", e);
            String failMsg = I18nUtil.getI18nMessage(headerLanguage, I18nKeys.loginFailure);
            messageVo.setResult(false);
            messageVo.setMsg(failMsg);
            return messageVo;
        }
    }

}

