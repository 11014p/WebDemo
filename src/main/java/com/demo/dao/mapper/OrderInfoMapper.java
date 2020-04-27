package com.demo.dao.mapper;

import com.demo.model.Account;
import com.demo.model.OrderInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrderInfoMapper {
    @Select("SELECT * FROM order_info where IS_DEL='N'")
        //查询所有订单
    List<OrderInfo> getAllOrderInfos();

    @Select("SELECT * FROM order_info where account_id=#{accountId,jdbcType=INTEGER} and IS_DEL='N'")
        //查询单个用户下订单
    List<OrderInfo> getOrderInfoByAccountId(@Param("accountId") Integer accountId);

    @Select("SELECT * FROM order_info where account_id=#{accountId,jdbcType=INTEGER} " +
            "and category_id=#{categoryId,jdbcType=INTEGER} and IS_DEL='N'")
        //查询单个用户对应某个产品下的订单
    List<OrderInfo> getOrderInfoByAccountIdCategoryId(@Param("accountId") Integer accountId,@Param("categoryId") Integer categoryId);

    @Insert("<script>" +
            "insert into order_info" +
            "    <trim prefix='(' suffix=')' suffixOverrides=','>" +
            "      <if test='orderId != null'>" +
            "        order_id," +
            "      </if>" +
            "      <if test='accountId != null'>" +
            "        account_id," +
            "      </if>" +
            "      <if test='categoryId != null'>" +
            "        category_id," +
            "      </if>" +
            "      <if test='priceId != null'>" +
            "        price_id," +
            "      </if>" +
            "      <if test='buyNum != null'>" +
            "        buy_num," +
            "      </if>" +
            "      <if test='salePrice != null'>" +
            "        sale_price," +
            "      </if>" +
            "      <if test='completeNum != null'>" +
            "        complete_num," +
            "      </if>" +
            "      <if test='createTime != null'>" +
            "        create_time," +
            "      </if>" +
            "      <if test='updateTime != null'>" +
            "        update_time," +
            "      </if>" +
            "      <if test='status != null'>" +
            "        status," +
            "      </if>" +
            "      <if test='isDel != null'>" +
            "        is_del," +
            "      </if>" +
            "    </trim>" +
            "    <trim prefix='values (' suffix=')' suffixOverrides=','>" +
            "      <if test='orderId != null'>" +
            "        #{orderId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='accountId != null'>" +
            "        #{accountId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='categoryId != null'>" +
            "        #{categoryId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='priceId != null'>" +
            "        #{priceId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='buyNum != null'>" +
            "        #{buyNum,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='salePrice != null'>" +
            "        #{salePrice,jdbcType=DOUBLE}," +
            "      </if>" +
            "      <if test='completeNum != null'>" +
            "        #{completeNum,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='createTime != null'>" +
            "        #{createTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test='updateTime != null'>" +
            "        #{updateTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test='status != null'>" +
            "        #{status,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='isDel != null'>" +
            "        #{isDel,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </trim>" +
            "</script>")
    @Options(useGeneratedKeys = true, keyProperty = "orderId", keyColumn = "order_id")
    void insertOrderInfo(OrderInfo orderInfo);

    @Update("<script> " +
            "update order_info" +
            "    <set>" +
            "      <if test='accountId != null'>" +
            "        account_id = #{accountId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='categoryId != null'>" +
            "        category_id = #{categoryId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='priceId != null'>" +
            "        price_id = #{priceId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='buyNum != null'>" +
            "        buy_num = #{buyNum,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='salePrice != null'>" +
            "        sale_price = #{salePrice,jdbcType=DOUBLE}," +
            "      </if>" +
            "      <if test='completeNum != null'>" +
            "        complete_num = #{completeNum,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='createTime != null'>" +
            "        create_time = #{createTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test='updateTime != null'>" +
            "        update_time = #{updateTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test='status != null'>" +
            "        status = #{status,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='isDel != null'>" +
            "        is_del = #{isDel,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </set>" +
            "    where order_id = #{orderId,jdbcType=INTEGER}" +
            "</script> ")
    void updateOrderInfo(OrderInfo orderInfo);
}
