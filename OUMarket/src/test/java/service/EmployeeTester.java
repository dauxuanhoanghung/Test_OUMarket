/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.tester.pojo.Customer;
import com.tester.pojo.Employee;
import com.tester.service.CustomerService;
import com.tester.service.EmployeeService;
import com.tester.service.impl.CustomerServiceImpl;
import com.tester.service.impl.EmployeeServiceImpl;
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
public class EmployeeTester {

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

    @Test
    /*Kiểm tra xem danh sách nhân viên có rỗng không*/
    public void TestListEmployeeNotNull() throws SQLException {
        EmployeeService e = new EmployeeServiceImpl();
        List<Employee> cus = e.getEmployees("1");
        List<Employee> Null = null;
        Assertions.assertNotEquals(Null, e);

    }

    @Test
    /*Kiểm tra tim thông tin nhân viên theo tên đăng nhập*/
    public void TestGetEmployeeByName() {
        int i = 0;
        String str = "hungnewbie";
        EmployeeService e = new EmployeeServiceImpl();
        Employee emp = new Employee();
        emp = e.getEmployeeByUsername(str);
        if (emp != null) {
            i = 1;
        }
        Assertions.assertEquals(1, i);
    }

//    @Test
//    /*Kiểm tra tim thông tin username và mật khẩu*/
//    public void TestAuthencateEmployee() {
//        int i = 0;
//        String username = "hungnewbie1";
//        String password = "123456789";
//        EmployeeService e = new EmployeeServiceImpl();
//        Employee emp = new Employee();
//        emp = e.authencateEmployee(username, password);
//        if (emp != null) {
//            i = 1;
//        }
//        Assertions.assertEquals(1, i);
//        
//    }

    @Test
    /*Kiểm tra xem có thêm nhân viên được không*/
    public void TestAddEmployee() {
        int i = 0;
        Date joinDate1 = new Date(2022, 3, 1);
        Date birthday1 = new Date(1990, 5, 15);
        EmployeeService e = new EmployeeServiceImpl();
        Employee emp = new Employee("123", "Nguyễn Hoài Nam", "HoaiNam123", "123456", joinDate1, birthday1, true, "9853719712", "EMPLOYEE", 1);
        i = e.addEmployee(emp);

        Assertions.assertNotEquals(0, i);
    }

    @Test
    /*Kiểm tra xem có cập nhật nhân viên được không*/
    public void TestUpdateEmployee() {
        int i = 0;
        Date joinDate1 = new Date(2022, 3, 1);
        Date birthday1 = new Date(1990, 5, 15);
        EmployeeService e = new EmployeeServiceImpl();
        Employee emp = new Employee("123", "Nguyễn Hoài Nam", "HoaiNam123", "123456", joinDate1, birthday1, true, "9853719715", "EMPLOYEE", 1);
        i = e.updateEmployee(emp);
        Assertions.assertNotEquals(0, i);
    }

    @Test
    /*Kiểm tra xem coi username, password có rống hay khoong*/
    public void TestUsernamePassword() {
        int i = 0;
        EmployeeService e = new EmployeeServiceImpl();
        List<Employee> ListEmp = e.getEmployees("1");

        for (Employee emp : ListEmp) {
            if (emp.getUsername() == null || emp.getPassword() == null) {
                i = 1;
            }
        }
        Assertions.assertEquals(0, i);
    }

    public static LocalDate dateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Test
    /* Kiểm tra coi nhân viên phải >18 tuổi */
    public void TestOldEmployee() {
        int i = 0;
        LocalDate now = LocalDate.now();
        EmployeeService e = new EmployeeServiceImpl();
        List<Employee> ListEmp = e.getEmployees("1");

        for (Employee emp : ListEmp) {
            Date t=emp.getBirthday();
            Date Now=Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());
            int age = now.getYear() - t.getYear();
            if (now.getMonthValue() < t.getMonth()
                    || (now.getMonthValue() == t.getMonth()&& now.getDayOfMonth() < t.getDay())) {
                age--;
            }
            if (age < 18) {
                i = 1;
                break;
            }
        }
        Assertions.assertEquals(0, i);
        
    }
}
