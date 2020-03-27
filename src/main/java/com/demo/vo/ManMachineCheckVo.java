package com.demo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ManMachineCheckVo implements Serializable{
    //人机验证参数
    private String sessionId;
    private String sig;
    private String token;
    private String scene;
    private String appKey="FFFF0N00000000008B49";
    private String remoteIp;
}
