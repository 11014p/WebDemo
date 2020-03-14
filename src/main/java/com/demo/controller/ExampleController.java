package com.demo.controller;

import com.demo.model.Account;
import com.demo.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExampleController {
    @Autowired
    private ExampleService exampleService;

    @GetMapping(path = "/home")
    public String home() {
        return exampleService.hello();
    }

    @GetMapping(path = "/accounts")
    public List<Account> getAccounts() {
        return exampleService.getAllAccounts();
    }
}

