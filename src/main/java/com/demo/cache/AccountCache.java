package com.demo.cache;

import com.demo.dao.mapper.AccountMapper;
import com.demo.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * application启动时初始化缓存
 */
@Component
public class AccountCache implements CommandLineRunner {
    private  Map<String, Account> accountMap = new ConcurrentHashMap<>();
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void run(String... args) throws Exception {
        //查询所有账户(包括未激活账户)
        List<Account> allAccounts = accountMapper.getAllAccounts();
        allAccounts.forEach(acc -> accountMap.put(acc.getEmail(), acc));
    }

    public Map<String, Account> getAccountMap() {
        return accountMap;
    }
}
