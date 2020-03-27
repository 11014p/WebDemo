package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class DefaultController {
    @RequestMapping(value ="/aa",method = RequestMethod.GET)
    public String home() {
        System.out.println("aaaaaaaaaaaaaa");
        return "/index2.html";
    }
    @RequestMapping(value ="/static/bb",method = RequestMethod.GET)
    public String home2() {
        System.out.println("bbbbbbbb");
        return "/index.html";
    }


}

