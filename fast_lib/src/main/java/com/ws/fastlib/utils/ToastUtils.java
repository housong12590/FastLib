package com.ws.fastlib.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.LayoutRes;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/29
 *     desc  : 吐司相关工具类
 * </pre>
 */
public final class ToastUtils {

    private static Toast sToast;
    private static int gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
    private static int xOffset = 0;
    private static int yOffset = (int) (64 * Utils.getContext().getResources().getDisplayMetrics().density + 0.5);
    @SuppressLint("StaticFieldLeak")
    private static View customView;
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 设置吐司位置
     *
     * @param gravity 位置
     * @param xOffset x偏移
     * @param yOffset y偏移
     */
    public static void setGravity(int gravity, int xOffset, int yOffset) {
        ToastUtils.gravity = gravity;
        ToastUtils.xOffset = xOffset;
        ToastUtils.yOffset = yOffset;
    }

    /**
     * 设置吐司view
     *
     * @param layoutId 视图
     */
    public static void setView(@LayoutRes int layoutId) {
        LayoutInflater inflate = (LayoutInflater) Utils.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ToastUtils.customView = inflate.inflate(layoutId, null);
    }

    /**
     * 设置吐司view
     *
     * @param view 视图
     */
    public static void setView(View view) {
        ToastUtils.customView = view;
    }

    /**
     * 获取吐司view
     *
     * @return view 自定义view
     */
    public static View getView() {
        if (customView != null) return customView;
        if (sToast != null) return sToast.getView();
        return null;
    }

    /**
     * 显示短时吐司
     *
     * @param text 显示文本
     */
    public static void show(final CharSequence text) {
        if (Thread.currentThread() != sHandler.getLooper().getThread()) {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    show(text, Toast.LENGTH_SHORT);
                }
            });
        } else {
            show(text, Toast.LENGTH_SHORT);
        }
    }


    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void show(final int resId) {
        if (Thread.currentThread() != sHandler.getLooper().getThread()) {
            sHandler.post(new Runnable() {
                @Override
                public void run() {
                    show(Utils.getContext().getResources().getString(resId));
                }
            });
        } else {
            show(Utils.getContext().getResources().getString(resId));
        }
    }


    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private static void show(CharSequence text, int duration) {
        cancel();
        if (customView != null) {
            sToast = new Toast(Utils.getContext());
            sToast.setView(customView);
            sToast.setDuration(duration);
        } else {
            sToast = Toast.makeText(Utils.getContext(), null, duration);
            sToast.setText(text);
        }
        sToast.setGravity(gravity, xOffset, yOffset);
        sToast.show();

    }

    /**
     * 取消吐司显示
     */
    public static void cancel() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}