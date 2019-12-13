package com.ws.fastlib.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

public class ShapeUtils {

    public static Drawable getRadiusDrawable(int color, float radius) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(DensityUtils.dp2px(Utils.getContext(), radius));
        return drawable;
    }

    public static Drawable getDrawable(int color) {
        return getRadiusDrawable(color, 100);
    }

}
