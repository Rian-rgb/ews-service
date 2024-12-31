package com.ews.service.util;

import java.util.Base64;

public class Base64Encryption {

    public static String encrypt(String plainText) {
        return Base64.getEncoder().encodeToString(plainText.getBytes());
    }
}
