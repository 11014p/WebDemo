package com.demo.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class AccountVo implements Serializable{
    //用户名
    private String name;
    //邮箱
    private String email;
    //密码
    private String password;

    //人机验证参数
    private String sessionId;
    private String sig;
    private String token;
    private String scene;
    private String appKey;
    private String remoteIp;
}
