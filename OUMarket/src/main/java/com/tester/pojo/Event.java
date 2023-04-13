/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.pojo;

import java.time.LocalDateTime;

/**
 *
 * @author LENOVO
 */
public class Event {

    private int id;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public Event() {
    }
    
    public Event(String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Event(int id, String description, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the startDate
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s - %s - %s", this.id, this.startDate, 
                this.endDate, this.description);
    }
}
