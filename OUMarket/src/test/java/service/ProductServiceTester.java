/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.tester.service.ProductService;
import com.tester.pojo.Product;
import com.tester.service.BranchMarketService;
import com.tester.service.EmployeeService;
import com.tester.service.impl.BranchMarketServiceImpl;
import com.tester.service.impl.EmployeeServiceImpl;
import com.tester.service.impl.ProductServiceImpl;
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

/**
 *
 * @author ADmin
 */
public class ProductServiceTester {

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

    /*Kiểm tra danh sách sản phẩm nạp lên thành công hay không */
    @Test
    public void TestListProduct() {
        ProductService prod = new ProductServiceImpl();
        List<Product> p = prod.getProducts("");
        Assertions.assertNotNull(p);
    }

    /*Kiểm tra xem việc thêm product có thành công hay không*/
    @Test
    public void testAddProduct() {
        ProductService prod = new ProductServiceImpl();
        Product p1 = new Product("Xà phòng", "Siêu thơm", 24000, "BHX", 2, 4);
        int i = prod.addProduct(p1);
        Assertions.assertNotEquals(-1, i);
    }

    /* Kiểm tra sản phẩm theo ID*/
    @Test
    public void testGetProductByID() {
        ProductService prod = new ProductServiceImpl();
        Product p = prod.getProductById("847");
        Assertions.assertNotNull(p);
    }

    /*Kiểm tra cập nhật sản phẩm thành công hay không*/
    @Test
    public void testUpdateProduct() {
        Product p1 = new Product();
        ProductService prod = new ProductServiceImpl();
        Product p = new Product("847", "Xà phòng", "Siêu thơm", 26000, "BHX", 1, 3);
        int i = prod.updateProduct(p);
        Assertions.assertNotEquals(-1, i);
    }


    /*Kiểm tra giá tiền của sản phầm phải lớn hơn không*/
    @Test
    public void testPriceOfProduct() {
        float j = 0;
        ProductService prod = new ProductServiceImpl();
        List<Product> products = prod.getProducts("");
        List<Product> actualValidProducts = products.stream()
                .filter(product -> product.getPrice() > 0)
                .collect(Collectors.toList());

        Assertions.assertEquals(products.size(), actualValidProducts.size());
    }


    /*Kiểm tra sản phẩm có bị trùng hay không */
    @Test
    public void testProductDuplicate() {

        ProductService s = new ProductServiceImpl();
        List<Product> p = s.getProducts("");
        List<String> ProductName = p.stream().
                map(mapper -> mapper.getName()).collect(Collectors.toList());
        Set<String> setProductName = new HashSet<>(ProductName);

        Assertions.assertEquals(ProductName.size(), setProductName.size());
    }

}
