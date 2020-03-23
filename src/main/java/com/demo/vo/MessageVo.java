package com.demo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MessageVo implements Serializable{
    private boolean result;
    private String msg;
    private String token;
    private String userName;
}
