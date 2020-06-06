package com.demo.dao.mapper;

import com.demo.model.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AccountMapper {
    @Select("SELECT * FROM account where IS_DEL='N'")
    //查询所有账号
    List<Account> getAllAccounts();

    @Select("SELECT * FROM account where IS_DEL='N' and IS_ACTIVE='A'")
    //查询已激活的账号
    List<Account> getAllActiveAccounts();

    @Select("SELECT * FROM account WHERE id = #{id}")
    Account getAccountById(Integer id);

    @Select("SELECT * FROM account WHERE email = #{email}")
    Account getAccountByEmail(String email);

    @Insert("<script>" +
            "insert into account" +
            "    <trim prefix='(' suffix=')' suffixOverrides=','>" +
            "      <if test='name != null'>" +
            "        NAME," +
            "      </if>" +
            "      <if test='accountRole != null'>" +
            "        ACCOUNT_ROLE," +
            "      </if>" +
            "      <if test='email != null'>" +
            "        EMAIL," +
            "      </if>" +
            "      <if test='password != null'>" +
            "        PASSWORD," +
            "      </if>" +
            "      <if test='isDel != null'>" +
            "        IS_DEL," +
            "      </if>" +
            "      <if test='createTime != null'>" +
            "        CREATE_TIME," +
            "      </if>" +
            "      <if test='updateTime != null'>" +
            "        UPDATE_TIME," +
            "      </if>" +
            "      <if test='isActive != null'>" +
            "        IS_ACTIVE," +
            "      </if>" +
            "    </trim>" +
            "    <trim prefix='values (' suffix=')' suffixOverrides=','>" +
            "      <if test='name != null'>" +
            "        #{name,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='accountRole != null'>" +
            "        #{accountRole,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='email != null'>" +
            "        #{email,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='password != null'>" +
            "        #{password,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='isDel != null'>" +
            "        #{isDel,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='createTime != null'>" +
            "        #{createTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test='updateTime != null'>" +
            "        #{updateTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test='isActive != null'>" +
            "        #{isActive,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </trim>" +
            "</script>")
    @Options(useGeneratedKeys=true,keyProperty="id",keyColumn="id")
    void insertAccount(Account account);

    @Update("<script> " +
            "update account" +
            "    <set>" +
            "      <if test='name != null'>" +
            "        NAME = #{name,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='accountRole != null'>" +
            "        ACCOUNT_ROLE = #{accountRole,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='email != null'>" +
            "        EMAIL = #{email,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='password != null'>" +
            "        PASSWORD = #{password,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='isDel != null'>" +
            "        IS_DEL = #{isDel,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='createTime != null'>" +
            "        CREATE_TIME = #{createTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test='updateTime != null'>" +
            "        UPDATE_TIME = #{updateTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test='isActive != null'>" +
            "        IS_ACTIVE = #{isActive,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </set>" +
            "    where ID = #{id,jdbcType=INTEGER} "+
            "</script> ")
    void updateAccount(Account account);
}
