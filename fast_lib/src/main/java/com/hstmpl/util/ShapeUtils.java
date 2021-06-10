package com.hstmpl.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.Utils;

public class ShapeUtils {

    public static Drawable getRadiusDrawable(int color, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(SizeUtils.dp2px(radius));
        return drawable;
    }

    public static Drawable getDrawable(int color) {
        return getRadiusDrawable(color, 100);
    }

}
