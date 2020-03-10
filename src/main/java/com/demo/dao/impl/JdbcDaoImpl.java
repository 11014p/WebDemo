package com.demo.dao.impl;

import com.demo.dao.JdbcDao;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcDaoImpl implements JdbcDao {
    @Override
    public String getHello() {
        return "Hi,HelloWorld.";
    }
}
