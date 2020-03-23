package com.demo.model;

import com.demo.enums.ActiveEnum;
import com.demo.enums.DelEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AccountFindpwdRecord implements Serializable{
    //主键
    private Integer id;
    //用户id
    private Integer accountId;
    //用户名
    private String accountName;
    //邮箱
    private String accountEmail;
    //凭证
    private String token;
    //创建时间
    private Date createTime;
    //失效时间
    private Date expiryTime;
    //状态：0 正常；1 失效
    private Integer isExpiried;
}
