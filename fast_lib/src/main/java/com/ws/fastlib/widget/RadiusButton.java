package com.ws.fastlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.ws.fastlib.R;
import com.ws.fastlib.utils.ShapeUtils;

public class RadiusButton extends AlphaTextView {

    private float leftTopRadius;
    private float leftBottomRadius;
    private float rightTopRadius;
    private float rightBottomRadius;
    private int color;

    public RadiusButton(Context context) {
        super(context);
    }

    public RadiusButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadiusButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs, defStyleAttr);
        initView();
    }


    private void initAttrs(AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.RadiusButton
                , defStyleAttr, 0);
        float radius = ta.getDimension(R.styleable.RadiusButton_radius, 0);
        leftTopRadius = ta.getDimension(R.styleable.RadiusButton_left_top_radius, radius);
        leftBottomRadius = ta.getDimension(R.styleable.RadiusButton_left_bottom_radius, radius);
        rightTopRadius = ta.getDimension(R.styleable.RadiusButton_right_top_radius, radius);
        rightBottomRadius = ta.getDimension(R.styleable.RadiusButton_right_bottom_radius, radius);
        color = ta.getColor(R.styleable.RadiusButton_bg_color, 0);
        ta.recycle();
    }

    private void initView() {
        Drawable drawable = ShapeUtils.getRadiusDrawable(color, rightTopRadius);
        setBackground(drawable);
    }
}
