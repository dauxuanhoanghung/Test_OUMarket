/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.utils;

import com.tester.constant.DBConstant;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author LENOVO
 */
public class MySQLConnectionUtil {
    static {
        try {
            //B1: Nap driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MySQLConnectionUtil.class.getName()).log(Level.SEVERE, 
                    "There's something error in MySQLConnectionUtil!!!", ex);
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBConstant.DB_URL, DBConstant.USER, DBConstant.PASSWORD);
    }
}
