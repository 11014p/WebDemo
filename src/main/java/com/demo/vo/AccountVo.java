package com.demo.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class AccountVo extends ManMachineCheckVo{
    //用户名
    private String name;
    //邮箱
    private String email;
    //密码
    private String password;

}
