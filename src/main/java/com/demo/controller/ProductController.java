package com.demo.controller;

import com.demo.enums.AccountStatusEnum;
import com.demo.model.Account;
import com.demo.service.ProductService;
import com.demo.vo.ProductCategoryVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    private static final String LANGUAGE = "language";
    private static final String LANGUAGE_DEFAULT = "zh";

    @Autowired
    private ProductService productService;
    @Autowired
    private HttpServletRequest request;

    @GetMapping(path = "/product/category")
    public List<ProductCategoryVo> getProductCategory() {
        String language = request.getHeader(LANGUAGE);
        logger.info("headerLanguage:{}", language);
        if(StringUtils.isEmpty(language)){
            language=LANGUAGE_DEFAULT;
        }
        Account account=new Account();
        account.setStatus(AccountStatusEnum.AGENT);
        request.getSession().setAttribute("account",account);
        List<ProductCategoryVo> categoryVoList = productService.getAllProductInfo(language);
        return categoryVoList;
    }

    @GetMapping(path = "/product/category/single")
    public ProductCategoryVo getProductCategoryByName(@RequestParam("name") String name) {
        String language = request.getHeader(LANGUAGE);
        logger.info("headerLanguage:{}", language);
        if(StringUtils.isEmpty(language)){
            language=LANGUAGE_DEFAULT;
        }
        ProductCategoryVo categoryVo = productService.getProductByName(name, language);
        return categoryVo;
    }

    @GetMapping(path = "/product/saleprice")
    public ProductCategoryVo getProductSalePrice(@RequestParam("categoryId") int categoryId,
                                                 @RequestParam("priceId") int priceId,
                                                 @RequestParam("count") int count) {
        String language = request.getHeader(LANGUAGE);
        logger.info("headerLanguage:{}", language);
        if(StringUtils.isEmpty(language)){
            language=LANGUAGE_DEFAULT;
        }
        ProductCategoryVo productSalePrice = productService.getProductSalePrice(categoryId,priceId, count,language);
        return productSalePrice;
    }

}

