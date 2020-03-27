package com.demo.service;

import com.aliyuncs.exceptions.ClientException;
import com.demo.vo.AccountVo;
import com.demo.vo.FindpwdRecordVo;
import com.demo.vo.MessageVo;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;

public interface AccountService {

    //用户注册
    void regesit(AccountVo accountVo) throws ClientException, MessagingException, GeneralSecurityException;

    //用户激活
    void active(String email,String activeCode);

    //用户登录
    MessageVo login(AccountVo accountVo) throws ClientException;

    //忘记密码
    void forgetPassword(FindpwdRecordVo findpwdRecordVo) throws ClientException, MessagingException, GeneralSecurityException;
}
