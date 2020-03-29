package com.demo.dao.mapper;

import com.demo.model.AccountFindpwdRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AccountFindpwdRecordMapper {
    @Insert("<script>" +
            "insert into account_findpwd_record\n" +
            "    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\n" +
            "      <if test=\"accountId != null\">\n" +
            "        account_id,\n" +
            "      </if>\n" +
            "      <if test=\"accountName != null\">\n" +
            "        account_name,\n" +
            "      </if>\n" +
            "      <if test=\"accountEmail != null\">\n" +
            "        account_email,\n" +
            "      </if>\n" +
            "      <if test=\"token != null\">\n" +
            "        token,\n" +
            "      </if>\n" +
            "      <if test=\"createTime != null\">\n" +
            "        create_time,\n" +
            "      </if>\n" +
            "      <if test=\"expiryTime != null\">\n" +
            "        expiry_time,\n" +
            "      </if>\n" +
            "      <if test=\"isExpiried != null\">\n" +
            "        is_expiried,\n" +
            "      </if>\n" +
            "    </trim>\n" +
            "    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\n" +
            "      <if test=\"accountId != null\">\n" +
            "        #{accountId,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"accountName != null\">\n" +
            "        #{accountName,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"accountEmail != null\">\n" +
            "        #{accountEmail,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"token != null\">\n" +
            "        #{token,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"createTime != null\">\n" +
            "        #{createTime,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "      <if test=\"expiryTime != null\">\n" +
            "        #{expiryTime,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "      <if test=\"isExpiried != null\">\n" +
            "        #{isExpiried,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "    </trim>" +
            "</script>")
    @Options(useGeneratedKeys=true,keyProperty="id",keyColumn="id")
    void insertOne(AccountFindpwdRecord accountFindpwdRecord);

    @Update("<script>" +
            "update account_findpwd_record\n" +
            "    <set>\n" +
            "      <if test=\"accountId != null\">\n" +
            "        account_id = #{accountId,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "      <if test=\"accountName != null\">\n" +
            "        account_name = #{accountName,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"accountEmail != null\">\n" +
            "        account_email = #{accountEmail,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"token != null\">\n" +
            "        token = #{token,jdbcType=VARCHAR},\n" +
            "      </if>\n" +
            "      <if test=\"createTime != null\">\n" +
            "        create_time = #{createTime,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "      <if test=\"expiryTime != null\">\n" +
            "        expiry_time = #{expiryTime,jdbcType=TIMESTAMP},\n" +
            "      </if>\n" +
            "      <if test=\"isExpiried != null\">\n" +
            "        is_expiried = #{isExpiried,jdbcType=INTEGER},\n" +
            "      </if>\n" +
            "    </set>\n" +
            "    where id = #{id,jdbcType=INTEGER}" +
            "</script>")
    void updateRecord(AccountFindpwdRecord accountFindpwdRecord);

    @Select("SELECT * FROM account_findpwd_record WHERE token = #{token}")
    AccountFindpwdRecord getRecordByToken(String token);
}
