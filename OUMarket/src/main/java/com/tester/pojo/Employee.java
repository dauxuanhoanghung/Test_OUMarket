/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.pojo;

import java.util.Date;
import java.util.UUID;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 *
 * @author LENOVO
 */
public class Employee {

    public static final String ADMIN = "ADMIN";
    public static final String EMPLOYEE = "EMPLOYEE";
    private String id;
    private String name;
    private String username;
    private String password;
    private Date joinDate;
    private Date birthday;
    private boolean active;
    private String phone;
    private String role;
    private int branchId;

    public Employee() {
    }

    {
        this.id = UUID.randomUUID().toString();
        long millis = System.currentTimeMillis();
        this.joinDate = new Date(millis);      
    }

    public Employee(String name, String username, String password, Date joinDate, String phone, String role, int branchId) {
        this(null, name, username, password, joinDate, phone, role, branchId);
    }
    
    public Employee(String name, String username, String password, String phone, String role, int branchId) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.branchId = branchId;
    }

    public Employee(String id, String name, String username, String password, Date joinDate, String phone, String role, int branchId) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.joinDate = joinDate;
        this.phone = phone;
        this.role = role;
        this.branchId = branchId;
    }

    public Employee(String id, String name, String username, String password, Date joinDate, Date birthday, boolean active, String phone, String role, int branchId) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.joinDate = joinDate;
        this.birthday = birthday;
        this.active = active;
        this.phone = phone;
        this.role = role;
        this.branchId = branchId;
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the branchId
     */
    public int getBranchId() {
        return branchId;
    }

    /**
     * @param branchId the branchId to set
     */
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    /**
     * @return the joinDate
     */
    public Date getJoinDate() {
        return joinDate;
    }

    /**
     * @param joinDate the joinDate to set
     */
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
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

    @Override
    public String toString() {
        return String.format("%s - %s - %s - %s - %s", this.id, 
                this.name, this.username, this.role, this.joinDate);
    }

    /**
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

     
}
