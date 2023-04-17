/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tester.utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.Url;
import com.cloudinary.utils.ObjectUtils;
import com.tester.pojo.Employee;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private MyCloudinary() {
    }

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

    public static String upload(File selectedFile) throws IOException {
        Map<String, Object> uploadResult = cloudinary.uploader().upload(selectedFile, ObjectUtils.emptyMap());
        String cloudinaryUrl = (String) uploadResult.get("secure_url");
        if (cloudinaryUrl.isBlank()) {
            return "";
        }
        return cloudinaryUrl;
    }

    public static boolean deleteFile(Employee employee) {
        String urlStr = employee.toString();
        String publicId = urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.lastIndexOf("."));
        System.out.println(publicId);
        try {
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return result.get("result").equals("ok");
        } catch (IOException ex) {
            Logger.getLogger(MyCloudinary.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static void main(String[] args) {
        deleteFile(null);
    }
}
