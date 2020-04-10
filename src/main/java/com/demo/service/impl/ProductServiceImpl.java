package com.demo.service.impl;

import com.demo.dao.mapper.ProductCategoryMapper;
import com.demo.dao.mapper.ProductDiscountMapper;
import com.demo.dao.mapper.ProductPriceMapper;
import com.demo.model.ProductCategory;
import com.demo.model.ProductDiscount;
import com.demo.model.ProductPrice;
import com.demo.service.ProductService;
import com.demo.vo.ProductCategoryVo;
import com.demo.vo.ProductPriceVo;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private ProductPriceMapper productPriceMapper;
    @Autowired
    private ProductDiscountMapper productDiscountMapper;

    @Override
    public List<ProductCategoryVo> getAllProductInfo(String language) {
        //获取目录树
        List<ProductCategoryVo> categoryTree = getCategoryTree(language);
        //获取价格信息
        Multimap<Integer, ProductPrice> priceMultimap = getAllPriceGroupCategoryId(language);
        //获取折扣信息
        Multimap<Integer, ProductDiscount> discountMultimap = getAllDiscountGroupPriceId();
        //封装价格信息、折扣信息
        addExtraInfo(categoryTree,priceMultimap,discountMultimap);
        return categoryTree;
    }

    private List<ProductCategoryVo> getCategoryTree(String language){
        List<ProductCategory> categoryList = productCategoryMapper.getAllProductCategory(language);
        Multimap<Integer,ProductCategory> categoryMultimap= HashMultimap.create();
        categoryList.forEach(category -> categoryMultimap.put(category.getParentId(),category));
        Collection<ProductCategory> rootCategories = categoryMultimap.get(null);
        List<ProductCategoryVo> collect = rootCategories.stream().map(rootCategory -> {
            ProductCategoryVo rootCategoryVo = convertCategory(rootCategory);
            //构建目录树
            buildCategoryTree(categoryMultimap.get(rootCategory.getId()), rootCategoryVo, categoryMultimap);
            return rootCategoryVo;
        }).collect(Collectors.toList());
        return collect;

    }

    private void buildCategoryTree(Collection<ProductCategory> childCategories,
                                   ProductCategoryVo parentCategoryVo,
                                   Multimap<Integer,ProductCategory> categoryMultimap){
        for(ProductCategory childCategory:childCategories){
            ProductCategoryVo childCategoryVo = convertCategory(childCategory);
            parentCategoryVo.getChildren().add(childCategoryVo);
            Integer id = childCategory.getId();
            if(categoryMultimap.containsKey(id)){
                buildCategoryTree(categoryMultimap.get(id),childCategoryVo,categoryMultimap);
            }
        }
    }

    private Multimap<Integer,ProductPrice> getAllPriceGroupCategoryId(String language){
        List<ProductPrice> priceList = productPriceMapper.getAllProductPrice(language);
        Multimap<Integer,ProductPrice> priceMultimap= HashMultimap.create();
        priceList.forEach(price -> priceMultimap.put(price.getCategoryId(),price));
        return priceMultimap;
    }

    private Multimap<Integer,ProductDiscount> getAllDiscountGroupPriceId(){
        List<ProductDiscount> discountList = productDiscountMapper.getAllProductDiscount();
        Multimap<Integer,ProductDiscount> discountMultimap= HashMultimap.create();
        discountList.forEach(discount -> discountMultimap.put(discount.getPriceId(),discount));
        return discountMultimap;
    }

    private ProductCategoryVo convertCategory(ProductCategory category){
        ProductCategoryVo categoryVo=new ProductCategoryVo();
        categoryVo.setId(category.getId());
        categoryVo.setParentId(category.getParentId());
        categoryVo.setName(category.getName());
        categoryVo.setDescription(category.getDescription());
        categoryVo.setLanguage(category.getLanguage());
        categoryVo.setStatus(category.getStatus());
        return categoryVo;
    }

    //封装价格数据、折扣数据
    private void addExtraInfo(List<ProductCategoryVo> categoryTree,
                              Multimap<Integer, ProductPrice> priceMultimap,
                              Multimap<Integer, ProductDiscount> discountMultimap
                              ){
        for(ProductCategoryVo categoryVo:categoryTree){
            Integer categoryVoId = categoryVo.getId();
            if(priceMultimap.containsKey(categoryVoId)){
                List<ProductPriceVo> priceVoList = categoryVo.getProduct();
                priceMultimap.get(categoryVoId).forEach(price ->{
                    ProductPriceVo priceVo = convertProductPrice(price);
                    //添加价格数据
                    priceVoList.add(priceVo);
                    Integer priceVoId = priceVo.getId();
                    if(discountMultimap.containsKey(priceVoId)){
                        List<ProductDiscount> discountList = priceVo.getDiscount();
                        //按照minNum升序排序
                        List<ProductDiscount> sortedList = discountMultimap.get(priceVoId).stream()
                                .sorted((discount1, discount2) ->
                                        discount1.getMinNum().compareTo(discount2.getMinNum())
                                ).collect(Collectors.toList());
                        //添加折扣数据
                        discountList.addAll(sortedList);
                    }
                });
                //TODO:价格信息排序
            }
            if(!categoryVo.getChildren().isEmpty()){
                addExtraInfo(categoryVo.getChildren(),priceMultimap,discountMultimap);
            }
        }
    }

    private ProductPriceVo convertProductPrice(ProductPrice productPrice){
        ProductPriceVo priceVo=new ProductPriceVo();
        priceVo.setId(productPrice.getId());
        priceVo.setName(productPrice.getName());
        priceVo.setCpm(productPrice.getCpm());
        priceVo.setCurrency(productPrice.getCurrency());
        priceVo.setDescAgent(productPrice.getDescAgent());
        priceVo.setDescCommon(productPrice.getDescCommon());
        priceVo.setDescLogin(productPrice.getDescLogin());
        priceVo.setDescNoLogin(productPrice.getDescNoLogin());
        priceVo.setDisplayName(productPrice.getDisplayName());
        priceVo.setMinNum(productPrice.getMinNum());
        priceVo.setMaxNum(productPrice.getMaxNum());
        priceVo.setLanguage(productPrice.getLanguage());
        return priceVo;
    }
}
