package com.demo.service;

import com.demo.model.Account;

import java.util.List;

public interface AccountService {

    //用户注册
    void regesit(Account account);

    //用户登录
    boolean login(Account account);
}