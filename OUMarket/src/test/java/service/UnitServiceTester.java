/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.tester.pojo.Unit;
import com.tester.service.UnitService;
import com.tester.service.impl.UnitServiceImpl;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;
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
public class UnitServiceTester {

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

    /*Kiểm tra danh sách đơn vị */
    @Test
    public void testGetUnits() {
        UnitService us = new UnitServiceImpl();
        List<Unit> lu = us.getUnits();
        Assertions.assertNotNull(lu);
    }

    /*Kiểm tra thêm đơn vị*/
    @Test
    public void testAddUnit() {
        UnitService us = new UnitServiceImpl();
        Unit u = new Unit(5, "lạng");
        int i = us.addUnit(u);
        Assertions.assertNotEquals(0, i);
    }

    /*Tìm unit theo id*/
    @Test
    public void testGetUnitByID() {
        UnitService us = new UnitServiceImpl();
        Unit u = us.getUnitById(1);
        Assertions.assertNotNull(u);
    }
    
    @Test
    public void testUnitNotNullOrEmptyName() {
        UnitService s = new UnitServiceImpl();
        List<Unit> cates = s.getUnits();
        long t = cates.stream().filter(c -> !c.getName().isBlank()).count();
        Assertions.assertTrue(t == cates.size());  
    }
}
