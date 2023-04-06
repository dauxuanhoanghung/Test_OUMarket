/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tester.service;

import com.tester.pojo.BranchMarket;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface BranchMarketService {
    List<BranchMarket> getBranchMarkets();
    int countEmployeesByBranchId(BranchMarket branch);
    int countEmployeesByBranchId(int branchId);
    int addBranchMarket(BranchMarket branch);
}
