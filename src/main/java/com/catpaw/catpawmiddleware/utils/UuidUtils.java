package com.catpaw.catpawmiddleware.utils;

import java.util.UUID;

public class UuidUtils {

    public static String createUuid() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}
