package com.demo.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigRequest;
import com.aliyuncs.afs.model.v20180112.AuthenticateSigResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.demo.cache.AccountCache;
import com.demo.dao.mapper.AccountMapper;
import com.demo.enums.ActiveEnum;
import com.demo.enums.DelEnum;
import com.demo.model.Account;
import com.demo.model.EmailTemplate;
import com.demo.service.AccountService;
import com.demo.utils.EncryptUtil;
import com.demo.utils.RegexUtil;
import com.demo.utils.SendMail;
import com.demo.vo.AccountVo;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    private static final String PRIVATE_KEY = "p@ssword#123";//私钥
//    private static final Map<String, Account> accountMap = AccountCache.getAccountMap();
    @Autowired
    private AccountCache accountCache;
    @Autowired
    private AccountMapper accountMapper;
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
            logger.error("manMachineCheck client init failure.",e);
        }
    }

    @Override
    public void regesit(AccountVo accountVo) throws ClientException {
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
        //更新缓存
        accountCache.getAccountMap().put(account.getEmail(),account);
        //发送邮件
        EmailTemplate template = new EmailTemplate();
        template.setToAddress(account.getEmail());
        template.setSubject("账号激活");
        String url = "http://localhost:8080/account/active?email=" + account.getEmail()
                + "&activeCode=" + EncryptUtil.getSoltMd5(account.getEmail().trim(), PRIVATE_KEY);
        template.setContent("请点击下面链接进行账号激活：<a href='" + url + "'>" + url + "</a>");
        SendMail sendMail = new SendMail(template);
        sendMail.start();
        logger.info("insert account into database success,[{}]", account);
    }

    @Override
    public void active(String email, String activeCode) {
        //md5校验(去空格)
        String soltMd5 = EncryptUtil.getSoltMd5(email.trim(), PRIVATE_KEY);
        if (!soltMd5.equals(activeCode)) {
            logger.error("account active faile,url has been changed.");
            //校验失败
            throw new RuntimeException("account active faile,url has been changed.");
        }
        Account account = accountCache.getAccountMap().get(email);
        Preconditions.checkNotNull(account,"account from accountMap cache is null.");
        account.setUpdateTime(new Date());
        account.setIsActive(ActiveEnum.A);
        //账号激活
        accountMapper.updateAccount(account);
        //更新缓存
        accountCache.getAccountMap().put(email,account);
        logger.info("account active success.");
    }

    @Override
    public Account login(AccountVo accountVo) throws ClientException {
        //类型转换
        Account account = convertAccountVo(accountVo);
        //非空和邮箱格式校验
        checkAccountValues(account);
        //人机校验
        checkManMachine(accountVo);
        //登录业务处理
        //查询所有生效账户
        List<Account> allAccounts = accountMapper.getAllActiveAccounts();
        Account ac = allAccounts.stream()
                .filter(acc -> account.getEmail().equals(acc.getEmail())
                        && account.getPassword().equals(acc.getPassword()))
                .findFirst().orElse(null);
        if (ac == null) {
            //验证失败
            logger.error("account login failure,account is not exit or actived.");
            throw new RuntimeException("account login failure,account is not exit or actived.");
        }
        return ac;
    }

    private void checkAccountValues(Account account) {
        Preconditions.checkNotNull(account, "param account is null.");
        Preconditions.checkArgument(account.getEmail() != null && account.getEmail().trim().length() != 0,
                "email is null or email is whitespace.");
        Preconditions.checkArgument(account.getPassword() != null && account.getPassword().trim().length() != 0,
                "password is null or password is whitespace.");
        //邮箱格式校验
        if (!RegexUtil.checkEmailPattern(account.getEmail())) {
            logger.error("email pattern check failure,please check email:{}.", account.getEmail());
            throw new RuntimeException("email pattern check failure,please check email:" + account.getEmail());
        }
    }

    //邮箱重复校验
    private void checkMultiEmail(String email) {
        if (accountCache.getAccountMap().containsKey(email)) {
            logger.error("multi email in database,email:{}.", email);
            throw new RuntimeException("multi email in database,email:" + email);
        }
    }

    private void checkManMachine(AccountVo vo) throws ClientException {
//        AuthenticateSigRequest request = new AuthenticateSigRequest();
//        //会话ID。必填参数，从前端获取，不可更改。
//        request.setSessionId(vo.getSessionId());
//        // 签名串。必填参数，从前端获取，不可更改。
//        request.setSig(vo.getSig());
//        // 请求唯一标识。必填参数，从前端获取，不可更改。
//        request.setToken(vo.getToken());
//        // 场景标识。必填参数，从前端获取，不可更改。
//        request.setScene(vo.getScene());
//        // 应用类型标识。必填参数，后端填写。
//        request.setAppKey(vo.getAppKey());
//        // 客户端IP。必填参数，后端填写。
//        request.setRemoteIp(vo.getRemoteIp());
//
//        AuthenticateSigResponse response = client.getAcsResponse(request);
//        if (response.getCode() == 100) {
//            logger.info("人机验签通过。");
//        } else {
//            throw new RuntimeException("人机验签失败！");
//        }
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
