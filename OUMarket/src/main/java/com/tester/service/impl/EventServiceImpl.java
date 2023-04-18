/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.Event;
import com.tester.pojo.EventProduct;
import com.tester.pojo.Product;
import com.tester.pojo.sub.SubProduct;
import com.tester.service.EventService;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class EventServiceImpl implements EventService {
    
    @Override
    public int addEvent(Event evt, List<SubProduct> eventProducts) {
        int numRowsAffected = 0;
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false); // start transaction
            String sql = "INSERT INTO event (description, start_date, end_date) VALUES (?, ?, ?)";
            try (PreparedStatement eventStatement = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS)) {
                eventStatement.setString(1, evt.getDescription());
                eventStatement.setObject(2, evt.getStartDate());
                eventStatement.setObject(3, evt.getEndDate());
                numRowsAffected += eventStatement.executeUpdate();
                if (numRowsAffected > 0) {
                    try (ResultSet generatedKeys = eventStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int eventId = generatedKeys.getInt(1);
                            try (PreparedStatement eventProductStatement = conn.prepareStatement(
                                    "INSERT INTO event_product (discount_price, event_id, product_id) VALUES (?, ?, ?)")) {
                                for (SubProduct eventProduct : eventProducts) {
                                    eventProductStatement.setFloat(1, eventProduct.getPrice());
                                    eventProductStatement.setInt(2, eventId);
                                    eventProductStatement.setString(3, eventProduct.getId());
                                    int numEventProductRowsAffected = eventProductStatement.executeUpdate();
                                    numRowsAffected += numEventProductRowsAffected;
                                }
                            }
                        } else {
                            throw new SQLException("Failed to retrieve auto-generated keys for event insertion");
                        }
                    }
                } else {
                    throw new SQLException("Failed to insert event");
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numRowsAffected;
    }
    
    @Override
    public Event getCurrentEvent() {
        LocalDateTime now = LocalDateTime.now();
        String sql = "SELECT * FROM event WHERE start_date <= ? AND end_date >= ? LIMIT 1";
        Event event = null;
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setObject(1, now);
            statement.setObject(2, now);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                LocalDateTime startDate = resultSet.getObject("start_date", LocalDateTime.class);
                LocalDateTime endDate = resultSet.getObject("end_date", LocalDateTime.class);
                event = new Event(id, description, startDate, endDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }
    
    public EventProduct getEventProduct(int eventId, String productId) {
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String query = "SELECT * FROM event_product WHERE event_id = ? AND product_id = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, eventId);
            stm.setString(2, productId);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return new EventProduct(rs.getInt("id"), rs.getFloat("discount_price"),
                        rs.getInt("event_id"), rs.getString("product_id"));
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(EventServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    @Override
    public EventProduct getEventProduct(Event event, Product product) {
        return getEventProduct(event.getId(), product.getId());
    }

    @Override
    public List<Event> getEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM event";
        Event event = null;
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("description");
                LocalDateTime startDate = resultSet.getObject("start_date", LocalDateTime.class);
                LocalDateTime endDate = resultSet.getObject("end_date", LocalDateTime.class);
                event = new Event(id, description, startDate, endDate);
                events.add(event);
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<EventProduct> getEventProductsByEvent(Event event) {
        List<EventProduct> edList = new ArrayList<>();
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            String query = "SELECT * FROM event_product WHERE event_id = ?";
            PreparedStatement stm = conn.prepareStatement(query);
            stm.setInt(1, event.getId());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                EventProduct ed = new EventProduct(rs.getInt("id"), rs.getFloat("discount_price"),
                        rs.getInt("event_id"), rs.getString("product_id"));
                edList.add(ed);
            }
            return edList;
        } catch (SQLException ex) {
            Logger.getLogger(EventServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
}
