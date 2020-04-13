package com.demo.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigRequest;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.demo.dao.mapper.AccountFindpwdRecordMapper;
import com.demo.dao.mapper.AccountMapper;
import com.demo.enums.ActiveEnum;
import com.demo.enums.DelEnum;
import com.demo.listner.event.PwdFindEvent;
import com.demo.listner.event.RegesitSuccessEvent;
import com.demo.model.Account;
import com.demo.model.AccountFindpwdRecord;
import com.demo.model.EmailTemplate;
import com.demo.service.AccountService;
import com.demo.utils.EncryptUtil;
import com.demo.utils.RegexUtil;
import com.demo.utils.SendMailUtil;
import com.demo.vo.AccountVo;
import com.demo.vo.FindpwdRecordVo;
import com.demo.vo.ManMachineCheckVo;
import com.demo.vo.MessageVo;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private static final String PRIVATE_KEY = "p@ssword#123";//私钥
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountFindpwdRecordMapper accountFindpwdRecordMapper;
    //人机验证配置
    private static final String accesskeyId = "LTAIUEYcjPpVjiFo";
    private static final String secret = "KRbOqJSVPYc7iNuNji07j50Urw5m5V";
    private static IAcsClient client;

    static {
        //YOUR ACCESS_KEY、YOUR ACCESS_SECRET请替换成您的阿里云accesskey id和secret
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accesskeyId, secret);
        client = new DefaultAcsClient(profile);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "afs", "afs.aliyuncs.com");
        } catch (ClientException e) {
            logger.error("manMachineCheck client init failure.", e);
        }
    }

    @Override
    @Transactional
    public void regesit(AccountVo accountVo) throws ClientException, MessagingException, GeneralSecurityException {
        //类型转换
        Account account = convertAccountVo(accountVo);
        //非空和邮箱格式校验
        checkAccountValues(account);
        //人机验证
        checkManMachine(accountVo);
        //邮箱重复性校验
        checkMultiEmail(account.getEmail());
        //业务处理
        Date date = new Date();
        account.setCreateTime(date);
        account.setUpdateTime(date);
        //默认账号未激活
        account.setIsActive(ActiveEnum.I);
        //默认账号非删除
        account.setIsDel(DelEnum.N);
        //保存到数据库
        accountMapper.insertAccount(account);
        //发布注册成功事件
        applicationContext.publishEvent(new RegesitSuccessEvent(account));
        logger.info("insert account into database success,[{}]", account);
    }

    @Override
    public void active(AccountVo accountVo) {
        Account account = accountMapper.getAccountByEmail(accountVo.getEmail());
        Preconditions.checkNotNull(account, "account in db is null.email:" + accountVo.getEmail());
        String token = EncryptUtil.getToken(new String[]{account.getName(), account.getEmail(), account.getPassword(), account.getUpdateTime().toString()});
        if (!token.equals(accountVo.getMyToken())) {
            logger.error("account active faile,activeCode has been changed.");
            //校验失败
            throw new RuntimeException("account active faile,activeCode has been changed.");
        }
        account.setUpdateTime(new Date());
        account.setIsActive(ActiveEnum.A);
        //账号激活
        accountMapper.updateAccount(account);
        logger.info("account active success.");
    }

    @Override
    public MessageVo login(AccountVo accountVo) throws ClientException {
        MessageVo messageVo = new MessageVo();
        //类型转换
        Account account = convertAccountVo(accountVo);
        //非空和邮箱格式校验
        checkAccountValues(account);
        //人机校验
        checkManMachine(accountVo);
        //登录业务处理(账号不存在、非激活状态、用户名和密码错误，都抛异常)
        Account ac = accountMapper.getAccountByEmail(accountVo.getEmail());
        if (ac == null || ac.getIsActive().equals(ActiveEnum.I)
                || !(account.getEmail().equals(ac.getEmail()) && account.getPassword().equals(ac.getPassword()))) {
            //验证失败
            logger.error("account login failure,account[{}] is not exit or actived.", account);
            throw new RuntimeException("account login failure,account is not exit or actived.");
        }
        messageVo.setToken(EncryptUtil.getMd5(ac.getEmail().trim()));
        messageVo.setUserName(ac.getName());
        return messageVo;
    }

    @Override
    @Transactional
    public void passwordForget(FindpwdRecordVo findpwdRecordVo) throws ClientException, MessagingException, GeneralSecurityException {
        //邮箱格式校验
        checkEmailPattern(findpwdRecordVo.getEmail());
        //人机验证
        checkManMachine(findpwdRecordVo);
        //业务处理
        Account account = accountMapper.getAccountByEmail(findpwdRecordVo.getEmail());
        Preconditions.checkNotNull(account, "email[" + findpwdRecordVo.getEmail() + "] doesn't exit in database.");
        AccountFindpwdRecord record = new AccountFindpwdRecord();
        Date createTime = new Date();
        Date expiryTime = new Date();
        expiryTime.setTime(createTime.getTime() + 30 * 60 * 1000);
        record.setAccountId(account.getId());
        record.setAccountName(account.getName());
        record.setAccountEmail(account.getEmail());
        record.setToken(EncryptUtil.getToken(new String[]{account.getName(), account.getEmail(), account.getPassword(), System.currentTimeMillis() + ""}));
        record.setCreateTime(createTime);
        //凭证有效期30分钟
        record.setExpiryTime(expiryTime);
        record.setIsExpiried(0);
        //保存到数据库
        accountFindpwdRecordMapper.insertOne(record);
        //发布事件
        applicationContext.publishEvent(new PwdFindEvent(record));
        logger.info("insert account_findpwd_record into database success,[{}]", findpwdRecordVo);
    }

    @Override
    @Transactional
    public void passwordReset(FindpwdRecordVo findpwdRecordVo) throws ClientException, MessagingException, GeneralSecurityException {
        Preconditions.checkArgument(findpwdRecordVo != null && findpwdRecordVo.getMyToken() != null, "token is null.");
        AccountFindpwdRecord record = accountFindpwdRecordMapper.getRecordByToken(findpwdRecordVo.getMyToken());
        if (record == null) {
            throw new RuntimeException("token 无效.");
        }
        if (record.getIsExpiried() == 1
                || new Date().compareTo(record.getExpiryTime()) > 0) {
            throw new RuntimeException("token已失效，已使用或则超过有效期(30分钟).");
        }
        //失效
        record.setIsExpiried(1);
        accountFindpwdRecordMapper.updateRecord(record);
        Account account = accountMapper.getAccountByEmail(findpwdRecordVo.getEmail());
        //重置密码
        account.setPassword(EncryptUtil.getSoltMd5(findpwdRecordVo.getPassword(), PRIVATE_KEY));
        account.setUpdateTime(new Date());
        accountMapper.updateAccount(account);
        logger.info("password reset success.");
    }

    private void checkAccountValues(Account account) {
        Preconditions.checkNotNull(account, "param account is null.");
        Preconditions.checkArgument(account.getEmail() != null && account.getEmail().trim().length() != 0,
                "email is null or email is whitespace.");
        Preconditions.checkArgument(account.getPassword() != null && account.getPassword().trim().length() != 0,
                "password is null or password is whitespace.");
        //邮箱格式校验
        checkEmailPattern(account.getEmail());
    }

    //邮箱格式校验
    private void checkEmailPattern(String email) {
        //邮箱格式校验
        if (!RegexUtil.checkEmailPattern(email)) {
            logger.error("email pattern check failure,please check email:{}.", email);
            throw new RuntimeException("email pattern check failure,please check email:" + email);
        }
    }

    //邮箱重复校验
    private void checkMultiEmail(String email) {
        Account account = accountMapper.getAccountByEmail(email);
        if (account != null) {
            logger.error("multi email in database,email:{}.", email);
            throw new RuntimeException("multi email in database,email:" + email);
        }
    }

    private void checkManMachine(ManMachineCheckVo vo) throws ClientException {
        logger.info("man machine check params[{}]",vo);
        if(vo==null || StringUtils.isEmpty(vo.getSessionId())
                || StringUtils.isEmpty(vo.getSig())
                || StringUtils.isEmpty(vo.getToken())
                || StringUtils.isEmpty(vo.getScene())){
            throw new ClientException("man machine check param is null.");
        }
        AuthenticateSigRequest request = new AuthenticateSigRequest();
        //会话ID。必填参数，从前端获取，不可更改。
        request.setSessionId(vo.getSessionId());
        // 签名串。必填参数，从前端获取，不可更改。
        request.setSig(vo.getSig());
        // 请求唯一标识。必填参数，从前端获取，不可更改。
        request.setToken(vo.getToken());
        // 场景标识。必填参数，从前端获取，不可更改。
        request.setScene(vo.getScene());
        // 应用类型标识。必填参数，后端填写。
        request.setAppKey(vo.getAppKey());
        // 客户端IP。必填参数，后端填写。
        request.setRemoteIp(vo.getRemoteIp());

        AuthenticateSigResponse response = client.getAcsResponse(request);
        if (response.getCode() == 100) {
            logger.info("人机验签通过。");
        } else {
            throw new RuntimeException("人机验签失败！");
        }
    }

    private Account convertAccountVo(AccountVo vo) {
        Account account = new Account();
        account.setName(vo.getName());
        account.setEmail(vo.getEmail());
        //密码加密
        account.setPassword(EncryptUtil.getSoltMd5(vo.getPassword(), PRIVATE_KEY));
        return account;
    }
}

