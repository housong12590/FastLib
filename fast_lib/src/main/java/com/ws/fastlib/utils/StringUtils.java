package com.ws.fastlib.utils;


import android.util.Base64;

import com.ws.fastlib.common.HMAC;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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

    public static boolean hasText(CharSequence str) {
        return (str != null && str.length() > 0 && containsText(str));
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
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

    public static String arrayToString(Object[] array) {
        return arrayToString(array, ",");
    }

    public static String arrayToString(Object[] array, String separator) {
        if (array == null || array.length == 0) {
            return null;
        }
        if (array.length == 1) {
            return ConvertUtils.toString(array[0]);
        }
        StringBuilder sb = new StringBuilder();
        for (Object obj : array) {
            sb.append(ConvertUtils.toString(obj)).append(separator);
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static String lowerFirstChar(String value) {
        if (isEmpty(value)) {
            return null;
        }
        String firstChar = value.substring(0, 1).toLowerCase();
        return firstChar + value.substring(1);
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

    public static String toQueryString(Map<String, Object> queryMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : queryMap.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.substring(0, sb.length() - 1);
    }

    public static String ISO8859ToUTF8(String text) {
        if (isNotEmpty(text)) {
            try {
                if (Charset.forName("ISO8859-1").newEncoder().canEncode(text)) {
                    return new String(text.getBytes("ISO8859-1"), StandardCharsets.UTF_8);
                }
                return text;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String md5(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(value.getBytes(StandardCharsets.UTF_8));
            return toHexString(bytes).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String hmacToHex(String data, String key, HMAC hmac) {
        return toHexString(hmac(data, key, hmac));
    }

    public static String hmacToBase64(String data, String key, HMAC hmac) {
        byte[] bytes = hmac(data, key, hmac);
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public static byte[] hmac(String data, String key, HMAC hmac) {
        try {
            Mac mac = Mac.getInstance(hmac.name());
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), hmac.name());
            mac.init(secret_key);
            return mac.doFinal(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static String toHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            ret.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return ret.toString();
    }

    public static String getRandomStringByLength(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    public static String[] splitLongText(String text, int size) {
        List<String> result = new ArrayList<>();
        if (text.length() <= size) {
            result.add(splitFormat(text, false));
            return result.toArray(new String[0]);
        }
        text = splitFormat(text, true);
        StringBuilder sb = new StringBuilder();
        String[] textParts = text.split("\\|");
        for (String textPart : textParts) {
            if (StringUtils.isEmpty(textPart)) {
                continue;
            }
            if (sb.length() + textPart.length() <= size) {
                sb.append(textPart);
            } else {
                if (sb.length() != 0) {
                    result.add(sb.toString());
                    sb.delete(0, sb.length());
                }
                if (textPart.length() <= size) {
                    sb.append(textPart);
                } else {
                    boolean flag;
                    do {
                        String newStr = textPart.substring(0, size);
                        result.add(newStr);
                        textPart = textPart.substring(size);
                        if (textPart.length() > size) {
                            flag = true;
                        } else if (textPart.length() == size) {
                            result.add(textPart);
                            flag = false;
                        } else {
                            sb.append(textPart);
                            flag = false;
                        }
                    } while (flag);
                }
            }
        }
        if (sb.length() != 0) {
            result.add(sb.toString());
        }
        return result.toArray(new String[0]);
    }

    private static String splitFormat(String value, boolean split) {
        char separator = '|';
        Pattern pattern = Pattern.compile("([，。？！；,.?!;\\n\\s\\t])");
        Matcher matcher = pattern.matcher(value);
        StringBuilder sb = new StringBuilder();
        int position = 0;
        while (matcher.find()) {
            sb.append(value, position, matcher.start());
            position = matcher.end();
            char g = matcher.group().charAt(0);
            if (sb.length() != 0) {
                char c = sb.charAt(sb.length() - 1);
                if (c == separator) continue;
            }
            if (g == ' ') {
                sb.append('，');
            } else if (g == '\n') {
                sb.append("。");
            } else {
                sb.append(g);
            }
            sb.append(separator);
        }
        sb.append(value, position, value.length());
        String text = sb.toString();
        if (!split) {
            text = text.replaceAll("\\|", "");
        }
        return text;
    }

    public static boolean hasChinese(String value) {
        // 汉字的Unicode取值范围
        String regex = "[\u4e00-\u9fa5]";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(value);
        return match.find();
    }
}
