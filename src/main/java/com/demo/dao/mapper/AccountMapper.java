package com.demo.dao.mapper;

import com.demo.model.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AccountMapper {
    @Select("SELECT * FROM account where IS_DEL='N' and IS_ACTIVE='A'")
    List<Account> getAllActiveAccounts();

    @Select("SELECT * FROM account WHERE id = #{id}")
    Account getAccountById(Integer id);

    @Insert("<script>" +
            "insert into account" +
            "    <trim prefix='(' suffix=')' suffixOverrides=','>" +
            "      <if test='name != null'>" +
            "        NAME," +
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
    void insertAccount(Account account);

    @Update("<script> " +
            "update account" +
            "    <set>" +
            "      <if test='name != null'>" +
            "        NAME = #{name,jdbcType=VARCHAR}," +
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
