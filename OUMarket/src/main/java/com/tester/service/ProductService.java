/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service;

import com.tester.pojo.Product;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface ProductService {
    List<Product> getProducts(String kw);
    int addProduct(Product p);
    Product getProductById(String id);
    Product updateProduct(Product p);
}
