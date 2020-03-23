package com.demo.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FindpwdRecordVo extends ManMachineCheckVo{
    //邮箱
    private String email;
    //凭证
    private String findpwdToken;
    //密码
    private String password;

}
