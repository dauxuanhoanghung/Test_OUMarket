/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.Category;
import com.tester.service.CategoryService;
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
            while(rs.next()) {
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
}
