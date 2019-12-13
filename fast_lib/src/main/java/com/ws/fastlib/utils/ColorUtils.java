package com.ws.fastlib.utils;

import android.graphics.Color;

import java.util.Random;

public class ColorUtils {

    public static int getTranslucentColor(float percent, int color) {
        int alpha = color >>> 24;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        return Color.argb(Math.round(alpha * percent), red, green, blue);
    }

    public static int getColorRed(int color) {
        return color >> 16 & 0xff;
    }

    public static int getColorGreen(int color) {
        return color >> 8 & 0xff;
    }

    public static int getColorBlue(int color) {
        return color & 0xff;
    }

    public static int getRandColor() {
        Random random = new Random();
        int red = random.nextInt(200) + 30;
        int green = random.nextInt(200) + 30;
        int blue = random.nextInt(200) + 30;
        return Color.argb(255, red, green, blue);
    }
}
