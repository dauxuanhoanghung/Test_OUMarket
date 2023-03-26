/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tester.service;

import com.tester.pojo.Unit;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface UnitService {
    List<Unit> getUnits();
    /**
     * 
     * @param u
     * @return >0 nếu add thành công, -1 nếu lỗi
     */
    int addUnit(Unit u);
}
