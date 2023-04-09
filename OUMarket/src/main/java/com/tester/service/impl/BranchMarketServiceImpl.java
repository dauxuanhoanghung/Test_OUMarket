/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.BranchMarket;
import com.tester.pojo.BranchProduct;
import com.tester.pojo.Product;
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
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("phone"));
                branchMarkets.add(c);
            }
            return branchMarkets;
        } catch (SQLException ex) {
            Logger.getLogger(BranchMarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public BranchMarket getBranchMarketById(int id) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String query = "SELECT * FROM branch_market WHERE id = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new BranchMarket(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("location"),
                        rs.getString("phone"));
            }
            return null;
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
            String sql = "INSERT INTO branch_market(name, location, phone) VALUES (?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, branch.getName());
            stm.setString(2, branch.getLocation());
            stm.setString(3, branch.getPhone());
            int r = stm.executeUpdate();
            conn.commit();

            return r;
        } catch (SQLException ex) {
            Logger.getLogger(BranchMarketServiceImpl.class.getName()).log(Level.SEVERE, 
                    "There's a error in BranchMarketServiceImpl", ex);
            return -1;
        }
    }

    @Override
    public int updateBranchMarket(BranchMarket branch) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            String query = "UPDATE branch_market SET name = ?, location = ?, phone = ? WHERE id = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, branch.getName());
            stm.setString(2, branch.getLocation());
            stm.setString(3, branch.getPhone());           
            stm.setInt(4, branch.getId());
            int r = stm.executeUpdate();
            conn.commit();
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(BranchMarketServiceImpl.class.getName()).log(Level.SEVERE, "Hệ thống có lỗi!!!", ex);
            return -1;
        }
    }

    @Override
    public int addProductToBranch(BranchMarket branch, Product... products) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            int rowSuccess = 0;
            String sql = "INSERT INTO branch_product(branch_id, product_id, active) VALUES (?, ?, ?)";
            for (Product p : products) {
                PreparedStatement stm = conn.prepareCall(sql);
                stm.setInt(1, branch.getId());
                stm.setString(2, p.getId());
                stm.setBoolean(3, true);
                rowSuccess += stm.executeUpdate();
            }
            conn.commit();

            return rowSuccess;
        } catch (SQLException ex) {
            Logger.getLogger(BranchMarketServiceImpl.class.getName()).log(Level.SEVERE, 
                    "There's a error in BranchMarketServiceImpl", ex);
            return -1;
        }
    }

    @Override
    public int addProductToBranch(BranchMarket branch, List<Product> products) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            int rowSuccess = 0;
            String sql = "INSERT INTO branch_product(branch_id, product_id, active) VALUES (?, ?, ?)";
            for (Product p : products) {
                PreparedStatement stm = conn.prepareCall(sql);
                stm.setInt(1, branch.getId());
                stm.setString(2, p.getId());
                stm.setBoolean(3, true);
                rowSuccess += stm.executeUpdate();
            }
            conn.commit();

            return rowSuccess;
        } catch (SQLException ex) {
            Logger.getLogger(BranchMarketServiceImpl.class.getName()).log(Level.SEVERE, 
                    "There's a error in BranchMarketServiceImpl", ex);
            return -1;
        }
    }

    @Override
    public int updateProductInBranch(BranchProduct branchProduct) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            String query = "UPDATE branch_product SET active = ? WHERE id = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setBoolean(1, branchProduct.isActive());
            stm.setInt(2, branchProduct.getId());
            int r = stm.executeUpdate();
            conn.commit();
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeServiceImpl.class.getName()).log(Level.SEVERE, "Hệ thống có lỗi!!!", ex);
            return -1;
        }
    }

    @Override
    public int updateProductInBranch(BranchMarket branch, Product product) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            String query = "UPDATE branch_product SET active = ? WHERE branch_id = ? AND product_id = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setInt(1, branch.getId());
            stm.setString(3, product.getId());
            int r = stm.executeUpdate();
            conn.commit();
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeServiceImpl.class.getName()).log(Level.SEVERE, "Hệ thống có lỗi!!!", ex);
            return -1;
        }
    }

    @Override
    public BranchProduct getBranchProduct(BranchMarket branch, Product product) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String query = "SELECT * FROM branch_product WHERE branch_id = ? AND product_id = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setInt(1, branch.getId());
            stm.setString(2, product.getId());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new BranchProduct(rs.getInt("id"),
                        rs.getInt("branch_id"),
                        rs.getString("product_id"),
                        rs.getBoolean("active"));
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(BranchMarketServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
