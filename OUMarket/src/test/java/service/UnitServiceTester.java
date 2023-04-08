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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public void TestGetUnits()
    {
        UnitService us=new UnitServiceImpl();
        List<Unit> lu=us.getUnits();
        Assertions.assertNotNull(lu);
    }
    /*Kiểm tra thêm đơn vị*/
    @Test
    public void TestAddUnit()
    {
        int i=0;
        UnitService us=new UnitServiceImpl();
        Unit u=new Unit(5, "lạng");
        i=us.addUnit(u);
        Assertions.assertNotEquals(0, i);
    }
    /*Tìm unit theo id*/
    @Test
    public void TestGetUnitByID()
    {
        UnitService us=new UnitServiceImpl();
        Unit u=us.getUnitById(1);
        Assertions.assertNotNull(u);
    }
}
