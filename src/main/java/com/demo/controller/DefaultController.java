package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DefaultController {
    @RequestMapping(value ="/",method = RequestMethod.GET)
    public String home() {
        return "/index.html";
    }
    @RequestMapping(value ="/mytest/nginx",method = RequestMethod.GET)
    @ResponseBody
    public String nginxTest( HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        System.out.println("url:"+url);
        return url;
    }

}

