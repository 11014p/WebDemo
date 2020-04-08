package com.demo.listner.event;

import org.springframework.context.ApplicationEvent;

/**
 * 密码找回事件
 */
public class PwdFindEvent extends ApplicationEvent{
    public PwdFindEvent(Object source) {
        super(source);
    }
}
