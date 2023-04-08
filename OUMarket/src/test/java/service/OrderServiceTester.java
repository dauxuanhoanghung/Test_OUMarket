/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;
import com.tester.pojo.Order;
import com.tester.service.OrderService;
import com.tester.service.impl.OrderServiceImpl;
import com.tester.pojo.OrderDetail;
import com.tester.service.impl.OrderServiceImpl;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
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
    public void TestAddOrder()
    {
        int i=0;
        
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        OrderService order=new OrderServiceImpl();
        Order o=new Order("2",600000, sqlDate, "123", "124");
        List<OrderDetail> od=new ArrayList<>();
        i=order.addOrder(o, od);
        Assertions.assertNotEquals(0, i);
    }
}
