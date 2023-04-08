/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.pojo;

import com.tester.utils.RandomIDGenerator;

/**
 *
 * @author LENOVO
 */
public class Product {

    private String id;
    private String name;
    private String description;
    private float price;
    private String origin;
    private int categoryId;
    private int unitId;

    public Product() {
    }

    {
        this.id = RandomIDGenerator.generate();
    }

    public Product(String name, String description, float price, String origin, int categoryId, int unitId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.origin = origin;
        this.categoryId = categoryId;
        this.unitId = unitId;
    }

    public Product(String id, String name, String description, float price, String origin, int categoryId, int unitId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.origin = origin;
        this.categoryId = categoryId;
        this.unitId = unitId;
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
     * @return the price
     */
    public float getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * @return the categoryId
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the unitId
     */
    public int getUnitId() {
        return unitId;
    }

    /**
     * @param unitId the unitId to set
     */
    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    @Override
    public String toString() {
        return String.format("%s - %s - %s - %s", this.id, this.name, this.price, this.unitId);
    }
}
