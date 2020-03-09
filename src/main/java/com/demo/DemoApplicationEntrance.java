package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
//默认扫描启动类所在的包目录及子目录，可以通过scanBasePackages属性定制
@SpringBootApplication
public class DemoApplicationEntrance {
    private static final Logger logger=LoggerFactory.getLogger(DemoApplicationEntrance.class);
    public static void main(String[] args) {
        //print system env
        System.getenv().entrySet().stream().forEach(entry -> logger.info("system env:{}",entry));
        System.getProperties().entrySet().stream().forEach(property -> logger.info("system property:{}",property));
        Arrays.asList(args).stream().forEach(arg -> logger.info("application arg:{}",arg));
        //start application
        SpringApplication.run(DemoApplicationEntrance.class, args);
    }
}
