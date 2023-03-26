/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.pojo;

import java.util.UUID;

/**
 *
 * @author LENOVO
 */
public class OrderDetail {

    private String id;
    private float quantity;
    private float currentPrice;
    private String orderID;
    private String productId;

    public OrderDetail() {
    }

    {
        this.setId(UUID.randomUUID().toString());
    }

    public OrderDetail(float quantity, float currentPrice, String orderID, String productId) {
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.orderID = orderID;
        this.productId = productId;
    }

    public OrderDetail(String id, float quantity, float currentPrice, String orderID, String productId) {
        this.id = id;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.orderID = orderID;
        this.productId = productId;
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
     * @return the quantity
     */
    public float getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the currentPrice
     */
    public float getCurrentPrice() {
        return currentPrice;
    }

    /**
     * @param currentPrice the currentPrice to set
     */
    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    /**
     * @return the orderID
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * @param orderID the orderID to set
     */
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /**
     * @return the productId
     */
    public String getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(String productId) {
        this.productId = productId;
    }

}
