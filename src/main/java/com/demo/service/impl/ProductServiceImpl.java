package com.demo.service.impl;

import com.demo.dao.mapper.*;
import com.demo.enums.AccountStatusEnum;
import com.demo.model.*;
import com.demo.service.OrderService;
import com.demo.service.ProductService;
import com.demo.vo.ProductCategoryVo;
import com.demo.vo.ProductPriceVo;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.demo.enums.AccountStatusEnum.AGENT;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductCategoryMapper productCategoryMapper;
    @Autowired
    private CategoryPriceMappingMapper categoryPriceMappingMapper;
    @Autowired
    private ProductPriceMapper productPriceMapper;
    @Autowired
    private PriceDiscountMappingMapper priceDiscountMappingMapper;
    @Autowired
    private ProductDiscountMapper productDiscountMapper;
    @Autowired
    private HttpSession session;
    @Autowired
    private OrderService orderService;

    @Override
    public List<ProductCategoryVo> getAllProductInfo(String language) {
        //获取目录树
        List<ProductCategoryVo> categoryTree = getCategoryTree(language);
        //获取价格信息
        Multimap<Integer, ProductPrice> priceMultimap = getAllPriceGroupCategoryId(language);
        //获取折扣信息
        Multimap<Integer, ProductDiscount> discountMultimap = getAllDiscountGroupPriceId();
        //封装价格信息、折扣信息
        addExtraInfo(categoryTree, priceMultimap, discountMultimap);
        return categoryTree;
    }

    @Override
    public ProductCategoryVo getProductByName(String name, String language) {
        List<ProductCategoryVo> allProductInfo = getAllProductInfo(language);
        ProductCategoryVo categoryVo = allProductInfo.stream()
                .filter(vo -> vo.getName().equals(name))
                .findFirst().orElse(null);
        Preconditions.checkNotNull(categoryVo, "can't find root product category by name:" + name);
        return categoryVo;
    }

    @Override
    public ProductCategoryVo getProductSalePrice(int categoryId, int priceId, int buyCount, String language) {
        List<ProductCategoryVo> allProductInfo = getAllProductInfo(language);
        ProductCategoryVo categoryVo = getChildRecursion(allProductInfo, categoryId, priceId);
        Preconditions.checkNotNull(categoryVo, "can't find product category by categoryId:" + categoryId);
        //计算售价
        salePriceCalculate(categoryVo.getProduct().get(0), categoryId, buyCount);
        return categoryVo;
    }

    private ProductCategoryVo getChildRecursion(List<ProductCategoryVo> productCategoryVoList, int categoryId, int priceId) {
        for (ProductCategoryVo vo : productCategoryVoList) {
            if (vo.getCategoryId() == categoryId) {
                List<ProductPriceVo> priceVoList = vo.getProduct();
                List<ProductPriceVo> collect = priceVoList.stream()
                        .filter(priceVo -> priceVo.getPriceId() == priceId)
                        .collect(Collectors.toList());
                //覆盖原有集合
                vo.setProduct(collect);
                return vo;
            } else {
                if (!vo.getChildren().isEmpty()) {
                    //递归查询子节点
                    return getChildRecursion(vo.getChildren(), categoryId, priceId);
                }
            }
        }
        return null;
    }

    private void salePriceCalculate(ProductPriceVo vo, int categoryId, int count) {
        Account account = (Account) session.getAttribute("account");
        AccountStatusEnum status = null;
        boolean isPromoUsed = false;
        if (account == null) {
            status = AccountStatusEnum.NO_LOGIN;
        } else {
            status = account.getStatus();
            Integer accountId = account.getId();
            List<OrderInfo> orderInfos = orderService.getOrderInfo(accountId, categoryId);
            //当前产品分类下已有购买记录，不再享受首次优惠
            if (!orderInfos.isEmpty()) {
                isPromoUsed = true;
            }
        }
        //获取用户等级
        switch (status) {
            case AGENT:
                Double salePrice = count * vo.getAgentDiscount() * vo.getCpm() / 1000;
                vo.setSalePrice(salePrice);
                break;
            case LOGIN:
                ProductDiscount discount = getDiscountRate(vo, count);
                double discountRate = discount.getRate();
                //首次优惠已使用,使用普通折扣计算售价
                if (isPromoUsed) {
                    salePrice = count * discountRate * vo.getCpm() / 1000;
                } else {
                    //首次优惠未使用,优先使用促销折扣计算售价
                    Double promoRate = discount.getPromoRate();
                    double finalRate = promoRate != null ? promoRate : discountRate;
                    salePrice = count * finalRate * vo.getCpm() / 1000;
                }
                vo.setSalePrice(salePrice);
                break;
            case NO_LOGIN:
            default:
                ProductDiscount discountDefault = getDiscountRate(vo, count);
                double commonRate = discountDefault.getRate();
                salePrice = count * commonRate * vo.getCpm() / 1000;
                vo.setSalePrice(salePrice);
                break;
        }

    }

    private ProductDiscount getDiscountRate(ProductPriceVo vo, int count) {
        for (ProductDiscount discount : vo.getDiscount()) {
            if (count >= discount.getMinNum() && count <= discount.getMaxNum()) {
                return discount;
            }
        }
        throw new RuntimeException("can't find match price discount rate,count:" + count);
    }

    private List<ProductCategoryVo> getCategoryTree(String language) {
        List<ProductCategory> categoryList = productCategoryMapper.getAllProductCategory(language);
        Multimap<Integer, ProductCategory> categoryMultimap = HashMultimap.create();
        categoryList.forEach(category -> categoryMultimap.put(category.getParentId(), category));
        Collection<ProductCategory> rootCategories = categoryMultimap.get(null);
        Account account = (Account) session.getAttribute("account");
        List<ProductCategoryVo> collect = rootCategories.stream().map(rootCategory -> {
            ProductCategoryVo rootCategoryVo = convertCategory(rootCategory);
            //添加用户状态
            if (account != null) {
                rootCategoryVo.setStatus(account.getStatus());
            }
            //构建目录树
            buildCategoryTree(categoryMultimap.get(rootCategory.getCategoryId()), rootCategoryVo, categoryMultimap);
            return rootCategoryVo;
        }).collect(Collectors.toList());
        return collect;

    }

    private void buildCategoryTree(Collection<ProductCategory> childCategories,
                                   ProductCategoryVo parentCategoryVo,
                                   Multimap<Integer, ProductCategory> categoryMultimap) {
        for (ProductCategory childCategory : childCategories) {
            ProductCategoryVo childCategoryVo = convertCategory(childCategory);
            parentCategoryVo.getChildren().add(childCategoryVo);
            Integer categoryId = childCategory.getCategoryId();
            if (categoryMultimap.containsKey(categoryId)) {
                buildCategoryTree(categoryMultimap.get(categoryId), childCategoryVo, categoryMultimap);
            }
        }
    }

    private Multimap<Integer, ProductPrice> getAllPriceGroupCategoryId(String language) {
        List<CategoryPriceMapping> categoryPriceMappings = categoryPriceMappingMapper.getAllCategoryPriceMapping();
        List<ProductPrice> priceList = productPriceMapper.getAllProductPrice(language);
        Map<Integer, ProductPrice> priceMap = new HashMap<>();
        priceList.forEach(price -> priceMap.put(price.getPriceId(), price));
        Multimap<Integer, ProductPrice> priceMultimap = HashMultimap.create();
        categoryPriceMappings.forEach(mapping -> priceMultimap.put(mapping.getCategoryId(), priceMap.get(mapping.getPriceId())));
        return priceMultimap;
    }

    private Multimap<Integer, ProductDiscount> getAllDiscountGroupPriceId() {
        List<PriceDiscountMapping> priceDiscountMappings = priceDiscountMappingMapper.getAllPriceDiscountMapping();
        List<ProductDiscount> discountList = productDiscountMapper.getAllProductDiscount();
        Map<Integer, ProductDiscount> discountMap = new HashMap<>();
        discountList.forEach(discount -> discountMap.put(discount.getDiscountId(), discount));
        Multimap<Integer, ProductDiscount> discountMultimap = HashMultimap.create();
        priceDiscountMappings.forEach(mapping -> discountMultimap.put(mapping.getPriceId(), discountMap.get(mapping.getDiscountId())));
        return discountMultimap;
    }

    private ProductCategoryVo convertCategory(ProductCategory category) {
        ProductCategoryVo categoryVo = new ProductCategoryVo();
        categoryVo.setCategoryId(category.getCategoryId());
        categoryVo.setParentId(category.getParentId());
        categoryVo.setName(category.getName());
        categoryVo.setDescription(category.getDescription());
        categoryVo.setLanguage(category.getLanguage());
        return categoryVo;
    }

    //封装价格数据、折扣数据
    private void addExtraInfo(List<ProductCategoryVo> categoryTree,
                              Multimap<Integer, ProductPrice> priceMultimap,
                              Multimap<Integer, ProductDiscount> discountMultimap
    ) {
        for (ProductCategoryVo categoryVo : categoryTree) {
            Integer categoryVoId = categoryVo.getCategoryId();
            if (priceMultimap.containsKey(categoryVoId)) {
                List<ProductPriceVo> priceVoList = categoryVo.getProduct();
                priceMultimap.get(categoryVoId).forEach(price -> {
                    ProductPriceVo priceVo = convertProductPrice(price);
                    //添加价格数据
                    priceVoList.add(priceVo);
                    Integer priceVoId = priceVo.getPriceId();
                    if (discountMultimap.containsKey(priceVoId)) {
                        List<ProductDiscount> discountList = priceVo.getDiscount();
                        //按照minNum升序排序
                        List<ProductDiscount> sortedList = discountMultimap.get(priceVoId).stream()
                                .sorted((discount1, discount2) ->
                                        discount1.getMinNum().compareTo(discount2.getMinNum())
                                ).collect(Collectors.toList());
                        //添加折扣数据
                        discountList.addAll(sortedList);
                        //添加最大折扣数据
                        priceVo.setAgentDiscount(sortedList.get(sortedList.size() - 1).getRate());
                    }
                });
                //TODO:价格信息排序
            }
            if (!categoryVo.getChildren().isEmpty()) {
                addExtraInfo(categoryVo.getChildren(), priceMultimap, discountMultimap);
            }
        }
    }

    private ProductPriceVo convertProductPrice(ProductPrice productPrice) {
        ProductPriceVo priceVo = new ProductPriceVo();
        priceVo.setPriceId(productPrice.getPriceId());
        priceVo.setName(productPrice.getName());
        priceVo.setSellerProductId(productPrice.getSellerProductId());
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
