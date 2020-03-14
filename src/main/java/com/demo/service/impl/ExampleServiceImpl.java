package com.demo.service.impl;

import com.demo.dao.JdbcDao;
import com.demo.dao.mapper.AccountMapper;
import com.demo.model.Account;
import com.demo.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExampleServiceImpl implements ExampleService {
    @Autowired
    private JdbcDao jdbcDao;
    @Autowired
    private AccountMapper accountMapper;

    public String hello() {
        return jdbcDao.getHello();
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountMapper.getAllAccounts();
    }
}
