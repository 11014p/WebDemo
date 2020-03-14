package com.demo.dao.mapper;

import com.demo.model.Account;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AccountMapper {
    @Select("SELECT * FROM account")
    List<Account> getAllAccounts();

    @Select("SELECT * FROM account WHERE id = #{id}")
    Account getAccountById(Long id);
}
