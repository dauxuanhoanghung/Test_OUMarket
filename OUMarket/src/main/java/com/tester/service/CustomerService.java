/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.service;

import com.tester.pojo.Customer;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface CustomerService {
    List<Customer> getCustomers();
    Customer getCustomerByPhone(String phone);
    Customer addCustomer(Customer customer);
    Customer updateCustomer(Customer customer);
}
