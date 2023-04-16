/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.pojo;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author LENOVO
 */
public class Order {

    private String id;
    private float subtotal;
    private LocalDateTime createdDate;
    private String employeeId;
    private String customerId;

    public Order() {
    }

    {
        this.setId(UUID.randomUUID().toString());
        createdDate = LocalDateTime.now();
    }

    public Order(float subtotal, String employeeId, String customerId) {
        this.subtotal = subtotal;
        this.employeeId = employeeId;
        this.customerId = customerId;
    }

    public Order(String id, float subtotal, LocalDateTime createdDate, String employeeId, String customerId) {
        this.id = id;
        this.subtotal = subtotal;
        this.createdDate = createdDate;
        this.employeeId = employeeId;
        this.customerId = customerId;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the subtotal
     */
    public float getSubtotal() {
        return subtotal;
    }

    /**
     * @param subtotal the subtotal to set
     */
    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * @return the createdDate
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

}
