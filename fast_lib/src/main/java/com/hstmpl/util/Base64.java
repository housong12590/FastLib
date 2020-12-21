package com.hstmpl.util;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Base64 {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static byte[] encode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return android.util.Base64.encode(src, android.util.Base64.DEFAULT);
    }

    public static byte[] decode(byte[] src) {
        if (src.length == 0) {
            return src;
        }
        return android.util.Base64.decode(src, android.util.Base64.DEFAULT);
    }

    public static String encode(String src) {
        byte[] bytes = encode(src.getBytes(DEFAULT_CHARSET));
        return new String(bytes, DEFAULT_CHARSET);
    }

    public static String decode(String src) {
        byte[] bytes = decode(src.getBytes(DEFAULT_CHARSET));
        return new String(bytes, DEFAULT_CHARSET);
    }

}
