/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.Customer;
import com.tester.service.CustomerService;
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
import java.sql.Date;
/**
 *
 * @author LENOVO
 */
public class CustomerServiceImpl implements CustomerService {

    @Override
    public List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM customer");
            while (rs.next()) {
                Customer c = new Customer(rs.getString("id"),
                        rs.getNString("name"),
                        rs.getString("phone"),
                        rs.getDate("birthday").toLocalDate(),
                        rs.getDate("join_date").toLocalDate());
                customers.add(c);
            }
            return customers;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Customer getCustomerByPhone(String phone) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String query = "SELECT * FROM customer WHERE phone = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, phone);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new Customer(rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getDate("birthday").toLocalDate(),
                        rs.getDate("join_date").toLocalDate());
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public Customer addCustomer(Customer cus) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            String query = "INSERT INTO customer(id, name, phone, birthday, join_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, cus.getId());
            stm.setString(2, cus.getName());
            stm.setString(3, cus.getPhone());
            stm.setObject(4, cus.getBirthday());
            stm.setObject(5, cus.getJoinDate());
            int r = stm.executeUpdate();
            conn.commit();
            return r != 0 ? cus : null;
        } catch (SQLException ex) {
            Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int updateCustomer(Customer customer) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String query = "UPDATE customer SET name = ?, phone = ?, birthday = ? WHERE id = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, customer.getName());
            stm.setString(2, customer.getPhone());
            stm.setObject(3, customer.getBirthday());
            stm.setString(4, customer.getId());
            return stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

}
