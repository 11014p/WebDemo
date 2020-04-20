package com.demo.dao.mapper;

import com.demo.model.Account;
import com.demo.model.ProductCategory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductCategoryMapper {
    @Select("SELECT * FROM product_category where language = #{language} and IS_DEL='N'")
    //查询所有产品
    List<ProductCategory> getAllProductCategory(String language);

    @Select("SELECT * FROM product_category WHERE category_id = #{id}")
    ProductCategory getProductCategoryById(Integer id);

    @Insert("<script>" +
            "insert into product_category" +
            "    <trim prefix='(' suffix=')' suffixOverrides=','>" +
            "      <if test='categoryId != null'>" +
            "        category_id," +
            "      </if>" +
            "      <if test='parentId != null'>" +
            "        parent_id," +
            "      </if>" +
            "      <if test='name != null'>" +
            "        name," +
            "      </if>" +
            "      <if test='description != null'>" +
            "        description," +
            "      </if>" +
            "      <if test='language != null'>" +
            "        language," +
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
            "      <if test='categoryId != null'>" +
            "        #{categoryId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='parentId != null'>" +
            "        #{parentId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='name != null'>" +
            "        #{name,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='description != null'>" +
            "        #{description,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='language != null'>" +
            "        #{language,jdbcType=VARCHAR}," +
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
    @Options(useGeneratedKeys=true,keyProperty="categoryId",keyColumn="category_id")
    void insertProductCategory(ProductCategory productCategory);

    @Update("<script> " +
            "update product_category" +
            "    <set>" +
            "      <if test='parentId != null'>" +
            "        parent_id = #{parentId,jdbcType=INTEGER}," +
            "      </if>" +
            "      <if test='name != null'>" +
            "        name = #{name,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='description != null'>" +
            "        description = #{description,jdbcType=VARCHAR}," +
            "      </if>" +
            "      <if test='language != null'>" +
            "        language = #{language,jdbcType=VARCHAR}," +
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
            "    where category_id = #{categoryId,jdbcType=INTEGER}"+
            "</script> ")
    void updateProductCategory(ProductCategory productCategory);
}
