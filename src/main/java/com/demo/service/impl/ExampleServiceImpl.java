package com.demo.service.impl;

import com.demo.service.ExampleService;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl implements ExampleService {
    public String hello() {
        return "你好，Spring Boot";
    }
}
