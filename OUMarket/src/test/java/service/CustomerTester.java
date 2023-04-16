/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.tester.pojo.Customer;
import com.tester.service.CustomerService;
import com.tester.service.impl.CustomerServiceImpl;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author ADmin
 */
public class CustomerTester {

    private static Connection conn;

    @BeforeAll
    public static void beforeAll() {
        try {
            conn = MySQLConnectionUtil.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    /*Kiểm tra xem danh sách khách hàng có rỗng không*/
    public void TestListCustomerNotNull() throws SQLException {
        CustomerService c = new CustomerServiceImpl();
        List<Customer> cus = c.getCustomers();
        List<Customer> Null = null;
        Assertions.assertNotEquals(Null, c);

    }

    @Test
    public void TestPhoneNotDupplication() {
        CustomerService c = new CustomerServiceImpl();
        List<Customer> cus = c.getCustomers();
        List<String> cusPhone = cus.stream().
                map(mapper -> mapper.getPhone()).collect(Collectors.toList());
        Set<String> setCusPhone = new HashSet<>(cusPhone);

        Assertions.assertEquals(setCusPhone.size(), cusPhone.size());
    }

    /* Kiểm tra việc tra cứu khách hàng theo số điện thoại có hợp lệ hay không? */
    @Test
    public void TestGetCustomerPhone() {
        String str = "094568465";
        CustomerService cus = new CustomerServiceImpl();
        Customer c1 = cus.getCustomerByPhone(str);
        Assertions.assertNotNull(c1);
    }

    /* Kiểm tra việc thêm khách hàng có hợp lệ hay không */
    @Test
    public void TestAddCustomer() {
  
        String str = "5996325847";
        Customer newCus = new Customer("128", "C", "5996325847", null, null);
        CustomerService cus = new CustomerServiceImpl();
        cus.addCustomer(newCus);
        Assertions.assertNotNull(cus);
    }

    /*Kiểm tra xem việc thay đổi thông tin khách hàng cơ hợp lệ hay không*/
    @Test
    public void TestEditAccount() {
        int i;
        Customer c1 = new Customer("128", "D", "3426546389", null, null);
        CustomerService cus = new CustomerServiceImpl();
        i=cus.updateCustomer(c1);
        Assertions.assertEquals(-1, i);
    }
    
    /*Kiểm tra xem số điện thoại có hợp lệ hay không*/
    @Test
    public void TestPhoneNumberLegit()
    {
        boolean expected =true;
        CustomerService cus=new CustomerServiceImpl();
        List<Customer>ListCus=cus.getCustomers();
        for(Customer c : ListCus)
        {
            if(c.getPhone().length()!=10)
            {
                expected=false;
            }
        }
        Assertions.assertTrue(expected);
    }

}
