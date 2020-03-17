package com.demo.service.impl;

import com.demo.dao.mapper.AccountMapper;
import com.demo.enums.ActiveEnum;
import com.demo.enums.DelEnum;
import com.demo.model.Account;
import com.demo.model.EmailTemplate;
import com.demo.service.AccountService;
import com.demo.utils.EncryptUtil;
import com.demo.utils.RegexUtil;
import com.demo.utils.SendMail;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private static final String PRIVATE_KEY = "p@ssword#123";//私钥
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void regesit(Account account) {
        //非空和邮箱格式校验
        checkAccountValues(account);
        //邮箱重复性校验
        checkMultiEmail(account.getEmail());
        Date date = new Date();
        account.setCreateTime(date);
        account.setUpdateTime(date);
        //默认账号未激活
        account.setIsActive(ActiveEnum.I);
        //默认账号非删除
        account.setIsDel(DelEnum.N);
        //保存到数据库
        accountMapper.insertAccount(account);
        //发送邮件
        EmailTemplate template = new EmailTemplate();
        template.setToAddress(account.getEmail());
        template.setSubject("账号激活");
        String url = "http://localhost:8080/account/active?email=" + account.getEmail()
                + "&activeCode=" + EncryptUtil.getSoltMd5(account.getEmail().trim(), PRIVATE_KEY);
        template.setContent("请点击下面链接进行账号激活：<a href='" + url + "'>" + url + "</a>");
        SendMail sendMail = new SendMail(template);
        sendMail.start();
        logger.info("insert account into database success,[{}]", account);
    }

    @Override
    public void active(String email, String activeCode) {
        //md5校验(去空格)
        String soltMd5 = EncryptUtil.getSoltMd5(email.trim(), PRIVATE_KEY);
        //6WQUgXqq7BBZCE3UBXOsSg==
        if (!soltMd5.equals(activeCode)) {
            logger.error("account active faile,url has been changed.");
            //校验失败
            throw new RuntimeException("account active faile,url has been changed.");
        }
        Account account = accountMapper.getAccountByEmail(email);
        account.setUpdateTime(new Date());
        account.setIsActive(ActiveEnum.A);
        //账号激活
        accountMapper.updateAccount(account);
        logger.info("account active success.");
    }

    @Override
    public Account login(Account account) {
        //非空和邮箱格式校验
        checkAccountValues(account);
        //查询所有生效账户
        List<Account> allAccounts = accountMapper.getAllActiveAccounts();
        Account ac = allAccounts.stream()
                .filter(acc -> account.getEmail().equals(acc.getEmail())
                        && account.getPassword().equals(acc.getPassword()))
                .findFirst().orElse(null);
        if (ac == null) {
            //验证失败
            logger.error("account login failure,account is not exit or actived.");
            throw new RuntimeException("account login failure,account is not exit or actived.");
        }
        return ac;
    }

    private void checkAccountValues(Account account) {
        Preconditions.checkNotNull(account, "param account is null.");
        Preconditions.checkArgument(account.getEmail() != null && account.getEmail().trim().length() != 0,
                "email is null or email is whitespace.");
        Preconditions.checkArgument(account.getPassword() != null && account.getPassword().trim().length() != 0,
                "password is null or password is whitespace.");
        //邮箱格式校验
        if (!RegexUtil.checkEmailPattern(account.getEmail())) {
            logger.error("email pattern check failure,please check email:{}.", account.getEmail());
            throw new RuntimeException("email pattern check failure,please check email:" + account.getEmail());
        }
    }

    //邮箱重复校验
    private void checkMultiEmail(String email) {
        //查询所有生效账户
        List<Account> allAccounts = accountMapper.getAllActiveAccounts();
        List<Account> collect = allAccounts.stream()
                .filter(acc -> email.equals(acc.getEmail()))
                .collect(Collectors.toList());
        if (collect.size() > 0) {
            logger.error("multi email in database,email:{}.", email);
            throw new RuntimeException("multi email in database,email:" + email);
        }
    }
}
