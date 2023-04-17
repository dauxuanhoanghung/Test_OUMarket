/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.utils;

import com.tester.pojo.sub.CartItem;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author LENOVO
 */
public class Utils {

    public static String hashPassword(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static boolean verifyPassword(String enteredPassword, String storedHashedPassword) {
        String hashedEnteredPassword = hashPassword(enteredPassword);
        return hashedEnteredPassword.equalsIgnoreCase(storedHashedPassword);
    }
    
    public static Float calculate(List<CartItem> cartItems) {
        Float subTotal = 0f;
        for(CartItem c : cartItems)
            subTotal += (c.getQuantity() * c.getPrice());
        
        return subTotal;
    }
    
    public static void main(String[] args) {
        System.out.println(hashPassword("admin@123"));
    }

}
