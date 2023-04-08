/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.utils;

import java.util.Random;

/**
 *
 * @author LENOVO
 */
public class RandomIDGenerator {
    private static final int MIN_LENGTH = 10;
    private static final int MAX_LENGTH = 20;
    private static final String DIGITS = "0123456789";

    public static String generate() {
        Random random = new Random();
        int length = random.nextInt(MAX_LENGTH - MIN_LENGTH + 1) + MIN_LENGTH;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(DIGITS.length());
            sb.append(DIGITS.charAt(index));
        }
        return sb.toString();
    }
}
