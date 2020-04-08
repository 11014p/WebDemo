package com.demo.listner.event;

import org.springframework.context.ApplicationEvent;

/**
 * 账户注册成功事件
 */
public class RegesitSuccessEvent extends ApplicationEvent{
    public RegesitSuccessEvent(Object source) {
        super(source);
    }
}
