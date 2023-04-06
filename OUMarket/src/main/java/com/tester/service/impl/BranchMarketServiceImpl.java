/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.BranchMarket;
import com.tester.service.BranchMarketService;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
            while (rs.next()) {
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

    @Override
    public int countEmployeesByBranchId(BranchMarket branch) {
        return countEmployeesByBranchId(branch.getId());
    }

    @Override
    public int countEmployeesByBranchId(int branchId) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String sql = "SELECT COUNT(*) FROM employee WHERE branch_id = ?";
            int count = 0;
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setInt(1, branchId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            } else {
                return 0;
            }
            return count;
        } catch (SQLException ex) {
            Logger.getLogger(BranchMarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int addBranchMarket(BranchMarket branch) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO branch_market(name, location) VALUES (?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, branch.getName());
            stm.setString(2, branch.getLocation());
            int r = stm.executeUpdate();
            conn.commit();

            return r;
        } catch (SQLException ex) {
            Logger.getLogger(BranchMarketServiceImpl.class.getName()).log(Level.SEVERE, 
                    "There's a error in BranchMarketServiceImpl", ex);
            return -1;
        }
    }

}
