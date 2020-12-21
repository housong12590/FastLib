package com.hstmpl.convert;

public class CharacterConverter implements Converter<Character> {

    public static Converter<Character> INSTANCE = new CharacterConverter();

    @Override
    public Character convert(Object value, Character defValue) {
        if (value == null) {
            return defValue;
        }
        if (value instanceof Character) {
            return (Character) value;
        }
        if (value instanceof String && ((String) value).length() == 1) {
            return ((String) value).charAt(0);
        }
        return defValue;
    }
}
