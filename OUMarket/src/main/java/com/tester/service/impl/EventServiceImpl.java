/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.BranchMarket;
import com.tester.pojo.Event;
import com.tester.service.EventService;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class EventServiceImpl implements EventService {
    
    @Override
    public int addEvent(Event evt, List<BranchMarket> branches) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false);
            String query = "INSERT INTO event(id, description, start_date, end_date) VALUES (null, ?, ?, ?)";
            PreparedStatement stm = conn.prepareCall(query);
            stm.setString(1, evt.getDescription());
            stm.setDate(2, (Date) evt.getStartDate());
            stm.setDate(3, (Date) evt.getEndDate());
            int r = stm.executeUpdate();
            conn.commit();
            return r;
        } catch (SQLException ex) {
            Logger.getLogger(EventServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

}
