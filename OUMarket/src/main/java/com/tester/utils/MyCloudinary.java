/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.utils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
/**
 *
 * @author LENOVO
 */
public class MyCloudinary {
    private static Cloudinary cloudinary;
    static {
        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dexvnphga",
                "api_key", "388299334739685",
                "api_secret", "rl5C5v_zZp_OEEB1IunCoRou82w"
        ));
    }
    
    private MyCloudinary() {}

    public static synchronized Cloudinary getObject() {
        if (cloudinary == null) {
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dexvnphga",
                "api_key", "388299334739685",
                "api_secret", "rl5C5v_zZp_OEEB1IunCoRou82w"
            ));
        }
        return cloudinary;
    }
}
