package com.ws.fastlib.utils;

public class ClassUtils {

    public static boolean isPrimitive(Class<?> cls) {
        return isInt(cls)
                || isString(cls)
                || isLong(cls)
                || isChar(cls)
                || isShort(cls)
                || isDouble(cls)
                || isBool(cls)
                || isByte(cls)
                || isFloat(cls);
    }

    public static boolean isPrimitive(Object obj) {
        return isPrimitive(obj.getClass());
    }

    public static boolean isInt(Object obj) {
        return obj instanceof Integer;
    }

    public static boolean isInt(Class<?> cls) {
        return cls == Integer.TYPE || cls == Integer.class;
    }

    public static boolean isString(Object obj) {
        return obj instanceof String;
    }

    public static boolean isString(Class<?> cls) {
        return cls == String.class;
    }

    public static boolean isBool(Object obj) {
        return obj instanceof Boolean;
    }

    public static boolean isBool(Class<?> cls) {
        return cls == Boolean.TYPE || cls == Boolean.class;
    }

    public static boolean isFloat(Object obj) {
        return obj instanceof Float;
    }

    public static boolean isFloat(Class<?> cls) {
        return cls == Float.TYPE || cls == Float.class;
    }

    public static boolean isDouble(Object obj) {
        return obj instanceof Double;
    }

    public static boolean isDouble(Class<?> cls) {
        return cls == Double.TYPE || cls == Double.class;
    }

    public static boolean isByte(Object obj) {
        return obj instanceof Byte;
    }

    public static boolean isByte(Class<?> cls) {
        return cls == Byte.TYPE || cls == Byte.class;
    }

    public static boolean isChar(Object obj) {
        return obj instanceof Character;
    }

    public static boolean isChar(Class<?> cls) {
        return cls == Character.TYPE || cls == Character.class;
    }

    public static boolean isShort(Object obj) {
        return obj instanceof Short;
    }

    public static boolean isShort(Class<?> cls) {
        return cls == Short.TYPE || cls == Short.class;
    }

    public static boolean isLong(Object obj) {
        return obj instanceof Long;
    }

    public static boolean isLong(Class<?> cls) {
        return cls == Long.TYPE || cls == Long.class;
    }
}
