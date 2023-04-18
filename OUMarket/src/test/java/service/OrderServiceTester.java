/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.tester.pojo.Order;
import com.tester.service.OrderService;
import com.tester.pojo.OrderDetail;
import com.tester.service.impl.OrderServiceImpl;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ADmin
 */
public class OrderServiceTester {

    private static Connection conn;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = MySQLConnectionUtil.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CategoryTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*Kiểm tra thêm Order thành công hay không*/
    @Test
    public void testAddOrder() {
        OrderService orderService = new OrderServiceImpl();
        Order o = new Order(600000, 
                "115dd543-2ef0-417e-941a-177756665f64", "34869609-086a-4a2a-8d13-eff266f46183");
        List<OrderDetail> odList = new ArrayList<>();
        OrderDetail od1 = new OrderDetail(2, 100000, o.getId(), "0209408506947819600");
        OrderDetail od2 = new OrderDetail(1, 400000, o.getId(), "020940850694781960004");
        odList.add(od2);
        odList.add(od1);
        int i = orderService.addOrder(o, odList); 
        Assertions.assertNotEquals(0, i);
    }
    
    @Test
    public void testOrderHasDetail() {
        OrderService orderService = new OrderServiceImpl();
        List<Order> orders = orderService.getOrders(null);
        for (Order order: orders) {
            List odList = orderService.getOrderDetails(order);
            Assertions.assertNotNull(odList);
        }
    }
    
    @Test
    public void testOrderSubTotal() {
        OrderService orderService = new OrderServiceImpl();
        List<Order> orders = orderService.getOrders(null);
        for (Order order: orders) {
            Assertions.assertNotEquals(0f, order.getSubtotal());
        }
    }
    
    @Test
    public void testOrderHasEmployeeId() {
        OrderService orderService = new OrderServiceImpl();
        List<Order> orders = orderService.getOrders(null);
        for (Order order: orders) {
            Assertions.assertNotNull(order.getEmployeeId());
        }
    }
}
