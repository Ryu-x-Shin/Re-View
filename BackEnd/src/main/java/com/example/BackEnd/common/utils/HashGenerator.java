package com.example.BackEnd.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class HashGenerator {

    private HashGenerator() {}

    public static String makeSha256(String inputString) {
        return DigestUtils.sha256Hex(inputString);
    }
}
