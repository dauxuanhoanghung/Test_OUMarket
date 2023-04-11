/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.Category;
import com.tester.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {

    /**
     *
     * @return
     */
    @Override
    public List<Category> getCategories() {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            List<Category> categories = new ArrayList<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM category");
            while (rs.next()) {
                Category c = new Category(rs.getInt("id"),
                        rs.getString("name"), rs.getString("description"));
                categories.add(c);
            }
            return categories;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Category getCategoryById(int id) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String query = "SELECT id, name, description FROM category WHERE id = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new Category(rs.getInt("id"),
                        rs.getString("name"), rs.getString("description"));
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public boolean updateCategory(Category category) {
        String sql = "UPDATE categories SET name = ?, description = ? WHERE id = ?";
        try (Connection conn = MySQLConnectionUtil.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, category.getName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getId());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteCategory(int id) {
    String sql = "DELETE FROM categories WHERE id = ?";
    try (Connection conn = MySQLConnectionUtil.getConnection();
         PreparedStatement statement = conn.prepareStatement(sql)) {
        statement.setInt(1, id);
        int rowsDeleted = statement.executeUpdate();
        return rowsDeleted > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}
}
