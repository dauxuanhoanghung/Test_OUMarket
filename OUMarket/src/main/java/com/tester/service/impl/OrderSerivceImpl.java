/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.Order;
import com.tester.pojo.OrderDetail;
import com.tester.service.OrderService;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.Date;
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
public class OrderSerivceImpl implements OrderService {

    @Override
    public List<Order> getOrders(Map<String, Object> params) {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM question";
            if (params.get("kw") != null && !String.valueOf(params.get("kw")).isBlank()) {
                sql += " WHERE content LIKE concat('%', ?, '%')";
            }
            PreparedStatement stm = conn.prepareCall(sql);
            if (params.get("kw") != null && !String.valueOf(params.get("kw")).isBlank()) {
                stm.setString(1, (String) params.get("kw"));
            }
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Order o = new Order(rs.getString("id"), rs.getFloat("subtotal"),
                        rs.getDate("createdDate"), rs.getString("employee_id"),
                        rs.getString("customer_id"));
                orders.add(o);

            }
            return orders;
        } catch (SQLException ex) {
            Logger.getLogger(OrderSerivceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int addOrder(Order o, List<OrderDetail> details) {
        try ( Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            String sql = "INSERT INTO order(id, subtotal, created_date, employee_id, customer_id) "
                    + " VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(sql);
            stm.setString(1, o.getId());
            stm.setFloat(2, 0);
            stm.setDate(3, (Date) o.getCreatedDate());
            stm.setString(4, o.getEmployeeId());
            stm.setString(5, o.getCustomerId());
            int r = stm.executeUpdate();
            for (OrderDetail d : details) {
                sql = "INSERT INTO order_detail(id, quantity, current_price, order_id, product_id) "
                        + " VALUES (?, ?, ?, ?, ?)";
                PreparedStatement subStm = conn.prepareCall(sql);
                subStm.setString(1, d.getId());
                subStm.setFloat(2, d.getQuantity());
                subStm.setFloat(3, d.getCurrentPrice());
                subStm.setString(4, d.getOrderID());
                subStm.setString(5, d.getProductId());
                r += subStm.executeUpdate();
            }
            conn.commit();

            return r;
        } catch (SQLException ex) {
            Logger.getLogger(OrderSerivceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

}
