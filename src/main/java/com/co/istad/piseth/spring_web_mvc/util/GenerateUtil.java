package com.co.istad.piseth.spring_web_mvc.util;

public class GenerateUtil {
    public static String generateUUID() {
        String prefix = "P";
        int randomNumber = (int) (Math.random() * 9000) + 1000;
        return prefix + randomNumber;
    }
}
