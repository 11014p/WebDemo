package com.demo.service.impl;

import com.demo.dao.JdbcDao;
import com.demo.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl implements ExampleService {
    @Autowired
    private JdbcDao jdbcDao;

    public String hello() {
        return jdbcDao.getHello();
    }
}
