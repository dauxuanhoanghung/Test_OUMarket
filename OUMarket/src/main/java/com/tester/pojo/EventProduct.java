/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.pojo;

/**
 *
 * @author LENOVO
 */
public class EventProduct {

    private int id;
    private float discountPrice;
    private int eventId;
    private String productId;

    public EventProduct() {
    }

    public EventProduct(int id, float discountPrice, int eventId, String productId) {
        this.id = id;
        this.discountPrice = discountPrice;
        this.eventId = eventId;
        this.productId = productId;
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
     * @return the discountPrice
     */
    public float getDiscountPrice() {
        return discountPrice;
    }

    /**
     * @param discountPrice the discountPrice to set
     */
    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }

    /**
     * @return the eventId
     */
    public int getEventId() {
        return eventId;
    }

    /**
     * @param eventId the eventId to set
     */
    public void setEventId(int eventId) {
        this.eventId = eventId;
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
