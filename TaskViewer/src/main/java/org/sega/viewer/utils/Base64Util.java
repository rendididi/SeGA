package org.sega.viewer.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Base64Util {
    public static String decode(String b64) throws UnsupportedEncodingException {
        if (b64 == null)
            return null;
        return new String(Base64.getDecoder().decode(b64), "UTF-8");
    }

    public static String encode(String str) throws UnsupportedEncodingException {
        if (str == null)
            return null;
        return new String(Base64.getEncoder().encode(str.getBytes("UTF-8")), "UTF-8");
    }
}