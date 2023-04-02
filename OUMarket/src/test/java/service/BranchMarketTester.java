/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.tester.pojo.BranchMarket;
import com.tester.service.BranchMarketService;
import com.tester.service.impl.BranchMarketServiceImpl;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 1.   Các chi nhánh không trùng tên nhau
 * 2.   Tên các chi nhánh không rỗng
 * @author LENOVO
 */
public class BranchMarketTester {
    private static Connection conn;
    
    @BeforeAll
    public static void beforeAll() {
        try {
            conn = MySQLConnectionUtil.getConnection() ;
        } catch (SQLException ex) {
            Logger.getLogger(BranchMarketTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(BranchMarketTester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    @DisplayName(value = "Kiem tra ten danh muc khong trung nhau")
    public void testNameNotDuplicated() throws SQLException {
        BranchMarketService s = new BranchMarketServiceImpl();
        List<BranchMarket> branches = s.getBranchMarkets();
        List<String> branchesName = branches.stream().
                map(mapper -> mapper.getName()).collect(Collectors.toList());
        Set<String> setBranchesName = new HashSet<>(branchesName);
        
        Assertions.assertEquals(branchesName.size(), setBranchesName.size());
    }
    
    @Test
    @DisplayName(value = "Kiem tra ten danh muc khong rong")
    public void testNotANullOrEmptyName() throws SQLException {
        BranchMarketService s = new BranchMarketServiceImpl();
        List<BranchMarket> branches = s.getBranchMarkets();
        
        long t = branches.stream().filter(b -> b.getName().isBlank()).count();
        Assertions.assertTrue(t == 0);     
    }
}
