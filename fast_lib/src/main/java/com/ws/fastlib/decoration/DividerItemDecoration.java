package com.ws.fastlib.decoration;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Author: 30453
 * Date: 2016/7/27 18:24
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int DEFAULT_DIVIDER_COLOR = Color.parseColor("#f1f1f1");
    private static final int DEFAULT_ORIENTATION = LinearLayoutManager.VERTICAL;

    private int mOrientation;
    private float mDividerHeight = 0.8f;
    private Paint mPaint;
    private int mDividerColor;
    private float margeSize = 0;

    public DividerItemDecoration(int margeSize) {
        this(margeSize, DEFAULT_ORIENTATION);
    }

    public DividerItemDecoration() {
        this(0, DEFAULT_ORIENTATION);
    }


    public DividerItemDecoration(int margeSize, int orientation) {
        this(margeSize, orientation, DEFAULT_DIVIDER_COLOR);
    }

    public DividerItemDecoration(int margeSize, int orientation, int color) {
        this.margeSize = dp2px(margeSize);
        this.mOrientation = orientation;
        this.mDividerColor = color;
        if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
            throw new IllegalArgumentException("please use LinearLayoutManager.VERTICAL or LinearLayoutManager.HORIZONTAL");
        }
        mDividerHeight = dp2px(mDividerHeight);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mDividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setDividerHeight(float height) {
        this.mDividerHeight = dp2px(height);
    }

    public void setDividerColor(int color) {
        this.mDividerColor = color;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        float left = parent.getPaddingLeft() + margeSize;
        float right = parent.getMeasuredWidth() - parent.getPaddingRight() - margeSize;
        int childSize = parent.getChildCount();
        for (int i = 0; i < childSize - 1; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            float top = child.getBottom() + layoutParams.bottomMargin;
            float bottom = top + mDividerHeight;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        float top = parent.getPaddingTop();
        float bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            float left = child.getRight() + layoutParams.rightMargin;
            float right = left + mDividerHeight;
            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, (int) (mDividerHeight + 0.5f));
        } else {
            outRect.set(0, 0, (int) (mDividerHeight + 0.5f), 0);
        }
    }

    private float dp2px(float space) {
        return Resources.getSystem().getDisplayMetrics().density * space;
    }
}
