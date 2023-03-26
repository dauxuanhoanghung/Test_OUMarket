/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.Category;
import com.tester.pojo.Employee;
import com.tester.service.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public List<Employee> getEmployees(String kw) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            List<Employee> employees = new ArrayList<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM employee");
            while (rs.next()) {
                Employee c = new Employee(rs.getString("id"), rs.getString("name"),
                        rs.getString("username"), rs.getString("password"),
                        rs.getString("role"),
                        rs.getInt("branch_id"));
                employees.add(c);
            }
            return employees;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String query = "SELECT * FROM employee WHERE username = '?'";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.first()) {
                return new Employee(rs.getString("id"), rs.getString("name"),
                        username, rs.getString("password"),
                        rs.getString("role"),
                        rs.getInt("branch_id"));
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Employee authencateEmployee(String username, String password) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String query = "SELECT * FROM employee WHERE username = ? AND password = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs != null && rs.next()) {
                return new Employee(rs.getString("id"), rs.getString("name"),
                        username, rs.getString("password"),
                        rs.getString("role"),
                        rs.getInt("branch_id"));
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
