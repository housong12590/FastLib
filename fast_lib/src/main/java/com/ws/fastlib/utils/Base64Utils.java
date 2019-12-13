package com.ws.fastlib.utils;

import android.util.Base64;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Base64Utils {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static byte[] encode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.encode(src, Base64.DEFAULT);
    }

    public static byte[] decode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return Base64.decode(src, Base64.DEFAULT);
    }

    public static String encodeToString(String src) {
        byte[] bytes = encode(src.getBytes(DEFAULT_CHARSET));
        return new String(bytes, DEFAULT_CHARSET);
    }

    public static String decodeFromString(String src) {
        byte[] bytes = decode(src.getBytes(DEFAULT_CHARSET));
        return new String(bytes, DEFAULT_CHARSET);
    }

}
