package com.demo.dao.mapper;

import com.demo.model.Account;
import com.demo.model.ProductPrice;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductPriceMapper {
    @Select("SELECT * FROM product_price where language = #{language} and IS_DEL='N'")
    //查询所有价格信息
    List<ProductPrice> getAllProductPrice(String language);

    @Select("SELECT * FROM product_price WHERE id = #{id}")
    ProductPrice getProductPriceById(Integer id);

    @Insert("<script>" +
            "insert into product_price" +
            "    <trim prefix='(' suffix=')' suffixOverrides=','>" +
            "      <if test='id != null'>" +
            "        id," +
            "      </if>" +
            "      <if test='categoryId != null'>" +
            "        category_id," +
            "      </if>" +
            "      <if test='name != null'>" +
            "        name," +
            "      </if>" +
            "      <if test='displayName != null'>" +
            "        display_name," +
            "      </if>" +
            "      <if test='language != null'>" +
            "        language," +
            "      </if>" +
            "      <if test='cpm != null'>" +
            "        cpm," +
            "      </if>" +
            "      <if test='currency != null'>" +
            "        currency," +
            "      </if>" +
            "      <if test='minNum != null'>" +
            "        min_num," +
            "      </if>" +
            "      <if test='maxNum != null'>" +
            "        max_num," +
            "      </if>" +
            "      <if test='descCommon != null'>" +
            "        desc_common," +
            "      </if>" +
            "      <if test='descNoLogin != null'>" +
            "        desc_no_login," +
            "      </if>" +
            "      <if test='descLogin != null'>" +
            "        desc_login," +
            "      </if>" +
            "      <if test='descAgent != null'>" +
            "        desc_agent," +
            "      </if>" +
            "      <if test='createTime != null'>" +
            "        create_time," +
            "      </if>" +
            "      <if test='updateTime != null'>" +
            "        update_time," +
            "      </if>" +
            "      <if test='isDel != null'>" +
            "        is_del," +
            "      </if>" +
            "    </trim>" +
            "    <trim prefix='values (' suffix=')' suffixOverrides=','>" +
            "      <if test='id != null'>" +
            "        #{id,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='categoryId != null'>" +
            "        #{categoryId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='name != null'>" +
            "        #{name,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='displayName != null'>" +
            "        #{displayName,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='language != null'>" +
            "        #{language,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='cpm != null'>" +
            "        #{cpm,jdbcType=DOUBLE}," +
            "      </if>" +
            "      <if test='currency != null'>" +
            "        #{currency,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='minNum != null'>" +
            "        #{minNum,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='maxNum != null'>" +
            "        #{maxNum,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='descCommon != null'>" +
            "        #{descCommon,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='descNoLogin != null'>" +
            "        #{descNoLogin,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='descLogin != null'>" +
            "        #{descLogin,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='descAgent != null'>" +
            "        #{descAgent,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='createTime != null'>" +
            "        #{createTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test='updateTime != null'>" +
            "        #{updateTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test='isDel != null'>" +
            "        #{isDel,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </trim>" +
            "</script>")
    @Options(useGeneratedKeys=true,keyProperty="id",keyColumn="id")
    void insertProductPrice(ProductPrice productPrice);

    @Update("<script> " +
            "update product_price" +
            "    <set>" +
            "      <if test='categoryId != null'>" +
            "        category_id = #{categoryId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='name != null'>" +
            "        name = #{name,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='displayName != null'>" +
            "        display_name = #{displayName,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='language != null'>" +
            "        language = #{language,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='cpm != null'>" +
            "        cpm = #{cpm,jdbcType=DOUBLE}," +
            "      </if>" +
            "      <if test='currency != null'>" +
            "        currency = #{currency,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='minNum != null'>" +
            "        min_num = #{minNum,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='maxNum != null'>" +
            "        max_num = #{maxNum,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='descCommon != null'>" +
            "        desc_common = #{descCommon,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='descNoLogin != null'>" +
            "        desc_no_login = #{descNoLogin,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='descLogin != null'>" +
            "        desc_login = #{descLogin,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='descAgent != null'>" +
            "        desc_agent = #{descAgent,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='createTime != null'>" +
            "        create_time = #{createTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test='updateTime != null'>" +
            "        update_time = #{updateTime,jdbcType=TIMESTAMP}," +
            "      </if>" +
            "      <if test='isDel != null'>" +
            "        is_del = #{isDel,jdbcType=VARCHAR}," +
            "      </if>" +
            "    </set>" +
            "    where id = #{id,jdbcType=INTEGER}"+
            "</script> ")
    void updateProductPrice(ProductPrice productPrice);
}
