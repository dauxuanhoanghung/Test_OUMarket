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
    public void TestListEmployeeNotNull() throws SQLException
    {
        EmployeeService e= new EmployeeServiceImpl();
        List<Employee> cus=e.getEmployees("1");
        List<Employee> Null=null;
        Assertions.assertNotEquals(Null, e);
                
    }
    
    @Test
    /*Kiểm tra tim thông tin nhân viên theo tên đăng nhập*/
    public void TestGetEmployeeByName()
    {
        int i = 0;
        String str="hungnewbie";
         EmployeeService e= new EmployeeServiceImpl();
         Employee emp=new Employee();
         emp=e.getEmployeeByUsername(str);
         if(emp != null)
         {
             i=1;
         }
         Assertions.assertNEquals(1, i);
    }
    @Test
    /*Kiểm tra tim thông tin username và mật khẩu*/
    public void TestAuthencateEmployee()
    {
        int i = 0;
        String username="hungnewbie1";
        String password="123456789";
         EmployeeService e= new EmployeeServiceImpl();
         Employee emp=new Employee();
         emp=e.authencateEmployee(username, password);
         if(emp != null)
         {
             i=1;
         }
         Assertions.assertEquals(1, i);
    }
}
