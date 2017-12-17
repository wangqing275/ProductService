package com.qq.service;

import com.qq.service.bean.IProductService;
import com.qq.service.bean.Product;

/**
 * Created by Qing on 2017/12/17.
 */
public class ProductService implements IProductService {
    public Product queryById(long id) {
        Product product = new Product();
        product.setId(1);
        product.setName("wangqing");
        product.setPrice(111);
        return product;
    }
}
