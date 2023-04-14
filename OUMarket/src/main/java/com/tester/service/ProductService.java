/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service;

import com.tester.pojo.BranchMarket;
import com.tester.pojo.Product;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LENOVO
 */
public interface ProductService {
    List<Product> getProducts(String kw);
    List<Product> getProducts(Map<String, String> params);
    List<Product> getProductsByBranch(BranchMarket branch);
    List<Product> getProductsByBranch(int id);
    List<Product> getUnsetProductsByBranch(BranchMarket branch);
    List<Product> getUnsetProductsByBranch(BranchMarket branch, String kw);
    List<Product> getUnsetProductsByBranch(int id);
    int addProduct(Product p);
    Product getProductById(String id);
    int updateProduct(Product p);
}
