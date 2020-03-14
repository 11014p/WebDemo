package com.demo.service;

import com.demo.model.Account;

import java.util.List;

public interface ExampleService {
    String hello();
    //获取所有用户
    List<Account> getAllAccounts();
}
