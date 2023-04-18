/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.tester.pojo.Event;
import com.tester.pojo.EventProduct;
import com.tester.pojo.Order;
import com.tester.pojo.OrderDetail;
import com.tester.pojo.sub.SubProduct;
import com.tester.service.EventService;
import com.tester.service.OrderService;
import com.tester.service.impl.EventServiceImpl;
import com.tester.service.impl.OrderServiceImpl;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author LENOVO
 */
public class EventTester {

    private static Connection conn;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = MySQLConnectionUtil.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(EventTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(EventTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void testAddOrder() {
        EventService eventService = new EventServiceImpl();
        Event e = new Event("Unit test", LocalDateTime.of(2024, Month.MARCH, 20, 0, 0),
                LocalDateTime.of(2023, Month.MARCH, 25, 0, 0));
        List<SubProduct> edList = new ArrayList<>();
        edList.add(new SubProduct("175784994121", "", 1000));
        edList.add(new SubProduct("2349160559374112", "", 1000));
        edList.add(new SubProduct("536610708324854", "", 1000));
        int i = eventService.addEvent(e, edList);
        Assertions.assertNotEquals(0, i);
    }
}
