/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.tester.pojo.Category;
import com.tester.service.CategoryService;
import com.tester.service.impl.CategoryServiceImpl;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
/**
 * 1.   Các danh mục SP không trùng tên nhau
 * 2.   Tên các danh mục không rỗng
 * @author LENOVO
 */
public class CategoryTester {
    private static Connection conn;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = MySQLConnectionUtil.getConnection() ;
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
    @DisplayName(value = "Kiem tra ten danh muc khong trung nhau")
    public void testNameNotDuplicated() throws SQLException {
        CategoryService s = new CategoryServiceImpl();
        List<Category> cates = s.getCategories();
        List<String> catesName = cates.stream().
                map(mapper -> mapper.getName()).collect(Collectors.toList());
        Set<String> setCatesName = new HashSet<>(catesName);
        
        Assertions.assertEquals(catesName.size(), setCatesName.size());
    }
    
    @Test
    @DisplayName(value = "Kiem tra ten danh muc khong rong")
    public void testNotANullOrEmptyName() throws SQLException {
        CategoryService s = new CategoryServiceImpl();
        List<Category> cates = s.getCategories();
        
        long t = cates.stream().filter(c -> c.getName().isBlank()).count();
        
        Assertions.assertTrue(t == 0);     
    }
}
