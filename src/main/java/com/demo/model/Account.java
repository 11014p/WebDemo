package com.demo.model;

import com.demo.enums.AccountRole;
import com.demo.enums.ActiveEnum;
import com.demo.enums.DelEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Account implements Serializable{
    //主键
    private Integer id;
    //用户名
    private String name;
    //邮箱
    private String email;
    //密码
    private String password;
    //账户删除标记
    private DelEnum isDel;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //账户激活标记
    private ActiveEnum isActive;
    //用户角色(默认Guest)
    private AccountRole accountRole= AccountRole.GUEST;
}
