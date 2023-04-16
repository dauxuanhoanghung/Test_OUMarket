/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.pojo.sub;

/**
 *
 * @author LENOVO
 */
public class CartItem {

    private String id;
    private String name;
    private Float price;
    private Float originPrice;
    private Float quantity;

    public CartItem() {
    }

    public CartItem(String id, String name, Float price, Float quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public CartItem(String id, String name, Float price, Float originPrice, Float quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.originPrice = originPrice;
        this.quantity = quantity;
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public Float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Float price) {
        this.price = price;
    }

    /**
     * @return the number
     */
    public Float getQuantity() {
        return quantity;
    }

    /**
     * @param quantity
     */
    public void setQuantity(Float quantity) {
        this.quantity = quantity;
    }
    
    public boolean isSameId(CartItem item) {
        return (this.id == null ? item.getId() == null : this.id.equals(item.getId()));
    }

    /**
     * @return the originPrice
     */
    public Float getOriginPrice() {
        return originPrice;
    }

    /**
     * @param originPrice the originPrice to set
     */
    public void setOriginPrice(Float originPrice) {
        this.originPrice = originPrice;
    }
}
