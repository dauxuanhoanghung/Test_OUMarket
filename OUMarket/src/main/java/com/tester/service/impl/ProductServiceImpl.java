/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.BranchMarket;
import com.tester.pojo.Product;
import com.tester.service.ProductService;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class ProductServiceImpl implements ProductService {

    @Override
    public List<Product> getProducts(String kw) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            List<Product> products = new ArrayList<>();
            String sql = "SELECT * FROM product ";
            if (kw != null && !kw.isBlank()) {
                sql += " WHERE name LIKE concat('%', ?, '%') OR description LIKE concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (kw != null && !kw.isBlank()) {
                stm.setString(1, kw);
                stm.setString(2, kw);
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getString("origin"),
                        rs.getInt("category_id"),
                        rs.getInt("unit_id"));
                products.add(p);
            }
            return products;
        } catch (SQLException ex) {
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Product> getProductsByBranch(BranchMarket branch) {
        return getProductsByBranch(branch.getId());
    }

    @Override
    public List<Product> getProductsByBranch(int id) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            List<Product> products = new ArrayList<>();
            String sql = "SELECT * FROM product WHERE id IN (SELECT product_id FROM branch_product WHERE branch_id = ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getString("origin"),
                        rs.getInt("category_id"),
                        rs.getInt("unit_id"));
                products.add(p);
            }
            return products;
        } catch (SQLException ex) {
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Product> getUnsetProductsByBranch(BranchMarket branch, String kw) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            List<Product> products = new ArrayList<>();
            String sql = "SELECT * FROM product WHERE id NOT IN (SELECT product_id FROM branch_product WHERE branch_id = ?) AND name LIKE concat('%', ?, '%')";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, branch.getId());
            stm.setString(2, kw);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getString("origin"),
                        rs.getInt("category_id"),
                        rs.getInt("unit_id"));
                products.add(p);
            }
            return products;
        } catch (SQLException ex) {
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<Product> getUnsetProductsByBranch(BranchMarket branch) {
        return getUnsetProductsByBranch(branch.getId());
    }

    @Override
    public List<Product> getUnsetProductsByBranch(int id) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            List<Product> products = new ArrayList<>();
            String sql = "SELECT * FROM product WHERE id NOT IN (SELECT product_id FROM branch_product WHERE branch_id = ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getString("origin"),
                        rs.getInt("category_id"),
                        rs.getInt("unit_id"));
                products.add(p);
            }
            return products;
        } catch (SQLException ex) {
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public List<Product> getProducts(Map<String, String> params) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            List<Product> products = new ArrayList<>();
            String sql = "SELECT * FROM product WHERE 1 = 1 ";
            List<Object> values = new ArrayList<>();
            if (params.containsKey("id")) {
                sql += "AND id LIKE concat('%', ?, '%') ";
                values.add(params.get("id"));
            }
            if (params.containsKey("branch_id")) {
                sql += "AND id IN (SELECT product_id FROM branch_product WHERE branch_id = ? )";
                values.add(Integer.valueOf(params.get("branch_id")));
            }
            System.out.println(sql);
            PreparedStatement stm = conn.prepareStatement(sql);
            for (int i = 0; i < values.size(); i++) {
                stm.setObject(i + 1, values.get(i));
            }
            System.out.println(stm);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Product p = new Product(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getString("origin"),
                        rs.getInt("category_id"),
                        rs.getInt("unit_id"));
                products.add(p);
            }
            return products;
        } catch (SQLException ex) {
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int addProduct(Product p) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO product(id, name, description, price, origin, category_id, unit_id) "
                    + "VALUES (?, ?, ?, ?, ? , ?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, p.getId());
            stm.setString(2, p.getName());
            stm.setString(3, p.getDescription());
            stm.setFloat(4, p.getPrice());
            stm.setString(5, p.getOrigin());
            stm.setInt(6, p.getCategoryId());
            stm.setInt(7, p.getUnitId());

            int r = stm.executeUpdate();
            conn.commit();

            return r;
        } catch (SQLException ex) {
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public Product getProductById(String id) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM product WHERE id = ?";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new Product(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getString("origin"),
                        rs.getInt("category_id"),
                        rs.getInt("unit_id"));
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int updateProduct(Product p) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "UPDATE product "
                    + " SET name = ?, description = ?, price = ?, origin = ?, category_id = ? , unit_id = ? "
                    + " WHERE id = ?";
            PreparedStatement stm = conn.prepareCall(sql);

            stm.setString(1, p.getName());
            stm.setString(2, p.getDescription());
            stm.setFloat(3, p.getPrice());
            stm.setString(4, p.getOrigin());
            stm.setInt(5, p.getCategoryId());
            stm.setInt(6, p.getUnitId());
            stm.setString(7, p.getId());
            int r = stm.executeUpdate();
            conn.commit();

            return r;
        } catch (SQLException ex) {
            Logger.getLogger(ProductServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

}
