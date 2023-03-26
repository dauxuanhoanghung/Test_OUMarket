/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tester.service;

import com.tester.pojo.BranchMarket;
import com.tester.pojo.Event;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public interface EventService {
    /**
     * 
     * @param evt
     * @param branches
     * @return >0 nếu add thành công, -1 nếu có lỗi
     */
    int addEvent(Event evt, List<BranchMarket> branches);
}
