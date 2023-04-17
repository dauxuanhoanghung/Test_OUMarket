/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tester.service;

import com.tester.pojo.Employee;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface EmployeeService {
    List<Employee> getEmployees(String kw);
    Employee getEmployeeByUsername(String username);
    Employee getEmployeeByPhone(String phone);
    Employee authencateEmployee(String username, String password);
    int addEmployee(Employee employee);
    int updateEmployee(Employee employee);
    int saveImage(String empId, String url);
}
