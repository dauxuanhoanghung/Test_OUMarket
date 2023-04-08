/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.pojo;

/**
 *
 * @author LENOVO
 */
public class BranchMarket {

    private int id;
    private String name;
    private String location;
    private String phone;

    public BranchMarket() {
    }

    public BranchMarket(int id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public BranchMarket(int id, String name, String location, String phone) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
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
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return String.format("%s - %s - %s", this.id, this.name, this.location);
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
