/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.Employee;
import com.tester.service.EmployeeService;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public Employee getEmployeeByUsername(String username) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String query = "SELECT * FROM employee WHERE username = '?'";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs != null) {
                return new Employee(rs.getString("id"), rs.getString("name"), 
                        username, rs.getString("password"), 
                        rs.getString("role"), 
                        rs.getInt("branch_id"));
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
