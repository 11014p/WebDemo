package com.demo.dao.mapper;

import com.demo.model.Account;
import com.demo.model.ProductDiscount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductDiscountMapper {
    @Select("SELECT * FROM product_discount where IS_DEL='N'")
    //查询所有折扣数据
    List<ProductDiscount> getAllProductDiscount();

    @Select("SELECT * FROM product_discount WHERE discount_id = #{id}")
    ProductDiscount getProductDiscountById(Integer id);

    @Insert("<script>" +
            "insert into product_discount" +
            "    <trim prefix='(' suffix=')' suffixOverrides=','>" +
            "      <if test='discountId != null'>" +
            "        discount_id," +
            "      </if>" +
            "      <if test='minNum != null'>" +
            "        min_num," +
            "      </if>" +
            "      <if test='maxNum != null'>" +
            "        max_num," +
            "      </if>" +
            "      <if test='rate != null'>" +
            "        rate," +
            "      </if>" +
            "      <if test='isDel != null'>" +
            "        is_del," +
            "      </if>" +
            "    </trim>" +
            "    <trim prefix='values (' suffix=')' suffixOverrides=','>" +
            "      <if test='discountId != null'>" +
            "        #{discountId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='minNum != null'>" +
            "        #{minNum,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='maxNum != null'>" +
            "        #{maxNum,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='rate != null'>" +
            "        #{rate,jdbcType=DOUBLE}," +
            "      </if>" +
            "      <if test='isDel != null'>" +
            "        #{isDel,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </trim>" +
            "</script>")
    @Options(useGeneratedKeys=true,keyProperty="discountId",keyColumn="discount_id")
    void insertProductDiscount(ProductDiscount productDiscount);

    @Update("<script> " +
            "update product_discount" +
            "    <set>" +
            "      <if test='minNum != null'>" +
            "        min_num = #{minNum,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='maxNum != null'>" +
            "        max_num = #{maxNum,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='rate != null'>" +
            "        rate = #{rate,jdbcType=DOUBLE}," +
            "      </if>" +
            "      <if test='isDel != null'>" +
            "        is_del = #{isDel,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </set>" +
            "    where discount_id = #{discountId,jdbcType=INTEGER}"+
            "</script> ")
    void updateProductDiscount(ProductDiscount productDiscount);
}
