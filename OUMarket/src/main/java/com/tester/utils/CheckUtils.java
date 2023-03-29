/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.utils;

import com.tester.pojo.Product;
import com.tester.service.ProductService;
import com.tester.service.impl.ProductServiceImpl;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author LENOVO
 */
public class CheckUtils {

    public static boolean isNotNullAndBlankText(TextInputControl... controls) {
        for (TextInputControl control : controls) {
            if (control.getText() == null || control.getText().isBlank()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotNullAndBlankText(String... texts) {
        for (String text : texts) {
            if (text == null || text.isBlank()) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param phoneNumber
     * @return 
     * 1: số điện thoại hợp lệ 
     * 0: chuỗi rỗng 
     * -1: sđt ko dài = 10 
     * -2: có chứa kí tự lạ không phải là số 
     * -3: không bắt đầu bằng số 0
     */
    public static int isValidPhoneNumber(String phoneNumber) {
        if (!isNotNullAndBlankText(phoneNumber)) {
            return 0;
        }
        if (phoneNumber.length() != 10) {
            return -1;
        }
        if (!isOnlyNumber(phoneNumber)) {
            return -2;
        }
        if (phoneNumber.charAt(0) != '0') {
            return -3;
        }
        return 1;
    }

    private static boolean isOnlyNumber(String phoneNumber) {
        for (int i = 0; i < phoneNumber.length(); i++) {
            if (!Character.isDigit(phoneNumber.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param name
     * @return 
     * 1: hợp lệ 
     * 0: chuỗi rỗng 
     * -1: tên có chứa số, kí tự đặc biệt 
     * -2: độ dài lớn hơn 50 được quy định bên Db
     */
    public static int isValidName(String name) {
        if (!isNotNullAndBlankText(name)) 
            return 0;
        if (!name.matches("^[\\p{L}]+$")) 
            return -1;
        if (name.length() > 50) 
            return -2;
        return 1;
    }

    /**
     *
     * @param p
     * @param quantity
     * @return 
     * 0: chuỗi rỗng 
     * -1: không khớp là double hoặc int, check ở đây là loại bỏ các kí tự 
     * -2: đơn vị cho các loại khác kg là số nguyên, check double
     * -3: đơn vị là số âm, cho các loại đơn vị kg và các loại khác
     */
    public static int isValidQuantity(Product p, String quantity) {
        if (isNotNullAndBlankText(quantity))
            return 0;
        if (!quantity.matches("-?\\d+(\\.\\d+)?"))
            return -1; //có thể là double or int
        if (p.getUnitId() != 1) 
            if (!quantity.matches("-?\\d+"))
                if (!quantity.matches("\\d+"))
                    return -3; //số nguyên âm
                else 
                    return 1; //valid
            else 
                return -2; //số double
        else //unit = 1, kg
        if (!quantity.matches("\\d+(\\.\\d+)?")) 
            return -3;
        else 
            return 1;
        
    }
    
    /**
     * 
     * @param productId
     * @param quantity
     * @return 
     * -1000: bổ sung cho trường hợp không tìm thấy
     */
    public static int isValidQuantity(String productId, String quantity) {
        ProductService s = new ProductServiceImpl();
        Product p = s.getProductById(productId);
        if (p != null) 
            return isValidQuantity(p, quantity);
        else 
            return -1000;
    }
}
