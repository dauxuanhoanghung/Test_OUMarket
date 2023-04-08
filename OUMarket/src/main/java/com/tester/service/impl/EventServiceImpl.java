/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service.impl;

import com.tester.pojo.Event;
import com.tester.pojo.EventProduct;
import com.tester.service.EventService;
import com.tester.utils.MySQLConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class EventServiceImpl implements EventService {

    @Override
    public int addEvent(Event evt, List<EventProduct> eventProducts) {
        int numRowsAffected = 0;
        try (Connection conn = MySQLConnectionUtil.getConnection()) {
            conn.setAutoCommit(false); // start transaction
            try (PreparedStatement eventStatement = conn.prepareStatement(
                    "INSERT INTO events (description, start_date, end_date) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                eventStatement.setString(1, evt.getDescription());
                eventStatement.setObject(2, evt.getStartDate());
                eventStatement.setObject(3, evt.getEndDate());
                int numEventRowsAffected = eventStatement.executeUpdate();
                numRowsAffected += numEventRowsAffected;
                if (numEventRowsAffected == 1) {
                    try (ResultSet generatedKeys = eventStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int eventId = generatedKeys.getInt(1);
                            try (PreparedStatement eventProductStatement = conn.prepareStatement(
                                    "INSERT INTO event_products (discount_price, event_id, product_id) VALUES (?, ?, ?)")) {
                                for (EventProduct eventProduct : eventProducts) {
                                    eventProductStatement.setFloat(1, eventProduct.getDiscountPrice());
                                    eventProductStatement.setInt(2, eventId);
                                    eventProductStatement.setString(3, eventProduct.getProductId());
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
                conn.commit(); // commit transaction
            } catch (SQLException e) {
                conn.rollback(); // rollback transaction on error
                throw e;
            } finally {
                conn.setAutoCommit(true); // reset auto-commit to true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numRowsAffected;
    }

    @Override
    public Event getCurrentEvent() {
        LocalDateTime now = LocalDateTime.now();
        String sql = "SELECT * FROM events WHERE start_date <= ? AND end_date >= ? LIMIT 1";
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
}
