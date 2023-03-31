/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.Unit;
import com.tester.service.UnitService;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class UnitServiceImpl implements UnitService {

    @Override
    public List<Unit> getUnits() {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            List<Unit> units = new ArrayList<>();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM unit");
            while (rs.next()) {
                Unit u = new Unit(rs.getInt("id"), rs.getString("name"));
                units.add(u);
            }
            return units;
        } catch (SQLException ex) {
            Logger.getLogger(UnitServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public int addUnit(Unit u) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            String query = "INSERT INTO unit(id, name) VALUES (?, ?)";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setInt(1, u.getId());
            stm.setString(2, u.getName());
            int r = stm.executeUpdate();
            conn.commit();
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(UnitServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    @Override
    public Unit getUnitById(int id) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String query = "SELECT * FROM unit WHERE id = ?";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next())
                return new Unit(rs.getInt("id"), rs.getString("name"));
            else 
                return null;
        } catch (SQLException ex) {
            Logger.getLogger(CategoryServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
