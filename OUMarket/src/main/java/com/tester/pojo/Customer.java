/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.pojo;

import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author LENOVO
 */
public class Customer {

    private String id;
    private String name;
    private String phone;
    private LocalDate birthday;
    private LocalDate joinDate;

    public Customer() {
    }

    {
        this.id = UUID.randomUUID().toString();
        this.joinDate = LocalDate.now();
    }

    public Customer(String name, String phone, LocalDate birthday) {
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
    }

    public Customer(String id, String name, String phone, LocalDate birthday, LocalDate joinDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.birthday = birthday;
        this.joinDate = joinDate;
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

    /**
     * @return the birthday
     */
    public LocalDate getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s - %s - %s", this.id, this.name, this.phone, this.birthday);
    }

    /**
     * @return the joinDate
     */
    public LocalDate getJoinDate() {
        return joinDate;
    }

    /**
     * @param joinDate the joinDate to set
     */
    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }
}
