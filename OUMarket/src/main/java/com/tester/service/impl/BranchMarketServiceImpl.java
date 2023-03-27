/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.BranchMarket;
import com.tester.service.BranchMarketService;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class BranchMarketServiceImpl implements BranchMarketService {

    @Override
    public List<BranchMarket> getBranchMarkets() {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            List<BranchMarket> branchMarkets = new ArrayList<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM branch_market");
            while(rs.next()) {
                BranchMarket c = new BranchMarket(rs.getInt("id"), 
                        rs.getString("name"), rs.getString("location"));
                branchMarkets.add(c);
            }
            return branchMarkets;
        } catch (SQLException ex) {
            Logger.getLogger(BranchMarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
