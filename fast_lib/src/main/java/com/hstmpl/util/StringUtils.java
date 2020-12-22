package com.hstmpl.util;


import com.hstmpl.convert.Convert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StringUtils {

    public static boolean isEmpty(CharSequence c) {
        return c == null || c.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence c) {
        return !isEmpty(c);
    }

    public static boolean hasLength(String str) {
        return (str != null && !str.isEmpty());
    }

    public static String listToString(List<?> list) {
        return listToString(list, ",");
    }

    public static String listToString(List<?> list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (Object o : list) {
            sb.append(o.toString()).append(separator);
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static String getRandomStringByLength(int length) {
        return getRandomString("abcdefghijklmnopqrstuvwxyz0123456789", length);
    }

    public static String getRandomNumberByLength(int length) {
        return getRandomString("0123456789", length);
    }

    public static String getRandomString(String seed, int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(seed.length());
            sb.append(seed.charAt(number));
        }
        return sb.toString();
    }

    public static Map<String, Object> parseQueryString(String queryString) {
        Map<String, Object> param = new HashMap<>();
        if (isEmpty(queryString)) {
            return param;
        }
        for (String queryStr : queryString.split("&")) {
            String[] split = queryStr.split("=");
            if (split.length == 2) {
                param.put(split[0], split[1]);
            }
        }
        return param;
    }


    public static String toQueryString(Map<?, ?> param) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : param.entrySet()) {
            sb.append(Convert.toString(entry.getKey()))
                    .append("=")
                    .append(Convert.toString(entry.getValue()))
                    .append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }


    public static String convertUnicode(String ori) {
        char aChar;
        int len = ori.length();
        StringBuilder outBuffer = new StringBuilder(len);
        for (int x = 0; x < len; ) {
            aChar = ori.charAt(x++);
            if (aChar == '\\') {
                aChar = ori.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = ori.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }


}
