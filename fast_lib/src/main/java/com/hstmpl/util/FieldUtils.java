package com.hstmpl.util;

import com.hstmpl.convert.Convert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldUtils {

    public static boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    public static boolean isPublic(Field field) {
        return Modifier.isPublic(field.getModifiers());
    }

    public static boolean isFinal(Field field) {
        return Modifier.isFinal(field.getModifiers());
    }

    public static boolean isPrivate(Field field) {
        return Modifier.isPrivate(field.getModifiers());
    }

    public static List<Field> getFields(Object bean) {
        return getFields(bean.getClass());
    }

    public static List<Field> getFields(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        List<Field> result = new ArrayList<>();
        for (Field field : fields) {
            if (!isStatic(field)) {
                result.add(field);
            }
        }
        return result;
    }

    public static Map<String, Field> getFieldMap(Object obj) {
        return getFieldMap(obj.getClass());
    }

    public static Map<String, Field> getFieldMap(Class<?> cls) {
        Field[] fields = cls.getDeclaredFields();
        Map<String, Field> result = new HashMap<>();
        for (Field field : fields) {
            if (!isStatic(field)) {
                result.put(field.getName(), field);
            }
        }
        return result;
    }

    public static Field getField(Class<?> cls, String name) {
        try {
            return cls.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static <T> T getValue(Field field, Object instance) {
        try {
            if (isPrivate(field)) {
                field.setAccessible(true);
            }
            return Convert.cast(field.get(instance));
        } catch (Exception ignored) {

        }
        return null;
    }

    public static void setValue(Field field, Object value, Object instance) {
        if (value == null) {
            return;
        }
        try {
            if (isPrivate(field)) {
                field.setAccessible(true);
            }
            Class<?> fieldType = field.getType();
            if (fieldType.isInstance(value)) {
                field.set(instance, value);
            } else if (ClassUtils.isPrimitive(fieldType)) {
                value = Convert.cast(fieldType, value);
                field.set(instance, value);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getValue(Class<?> cls, String fieldName, Object instance) {
        Field field = getField(cls, fieldName);
        return getValue(field, instance);
    }

    public static Class<?> getParameterizedType(Field f) {
        Type fc = f.getGenericType(); // 关键的地方得到其Generic的类型
        if (fc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) fc;
            Type[] types = pt.getActualTypeArguments();
            if (types.length > 0) {
                return (Class<?>) types[0];
            }
        }
        return null;
    }
}
