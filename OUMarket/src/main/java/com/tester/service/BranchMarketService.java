/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tester.service;

import com.tester.pojo.BranchMarket;
import com.tester.pojo.BranchProduct;
import com.tester.pojo.Product;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface BranchMarketService {
    List<BranchMarket> getBranchMarkets();
    public BranchMarket getBranchMarketById(int id);
    int countEmployeesByBranchId(BranchMarket branch);
    int countEmployeesByBranchId(int branchId);
    int addBranchMarket(BranchMarket branch);
    int updateBranchMarket(BranchMarket branch);
    int addProductToBranch(BranchMarket branch, Product... products);
    int addProductToBranch(BranchMarket branch, List<Product> products);
    int updateProductInBranch(BranchProduct product);
    int updateProductInBranch(BranchMarket branch, Product product);
    BranchProduct getBranchProduct(BranchMarket branch, Product product);
}
