package com.ws.fastlib.utils;

import android.util.Log;

/**
 * author：hs
 * date: 2017/6/9 0009 14:27
 */

public class LogUtils {

    private static final int LOG_MAX_LENGTH = 3000;

    private LogUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;
    private static final String TAG = "ciin";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void i(Object obj) {
        i(TAG, obj.toString());
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(Object obj) {
        e(TAG, obj.toString());
    }

    public static void v(String msg) {
        v(TAG, msg);
    }


    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug) {
            int strLen = msg.length();
            int index = 0;
            while (strLen - index > LOG_MAX_LENGTH) {
                Log.i(TAG, msg.substring(index, index + LOG_MAX_LENGTH));
                index += LOG_MAX_LENGTH;
            }
            Log.i(tag, msg.substring(index));
        }

    }

    public static void d(String tag, String msg) {
        if (isDebug) {
            int strLen = msg.length();
            int index = 0;
            while (strLen - index > LOG_MAX_LENGTH) {
                Log.d(TAG, msg.substring(index, index + LOG_MAX_LENGTH));
                index += LOG_MAX_LENGTH;
            }
            Log.d(tag, msg.substring(index));
        }

    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            int strLen = msg.length();
            int index = 0;
            while (strLen - index > LOG_MAX_LENGTH) {
                Log.e(TAG, msg.substring(index, index + LOG_MAX_LENGTH));
                index += LOG_MAX_LENGTH;
            }
            Log.e(tag, msg.substring(index));
        }

    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            int strLen = msg.length();
            int index = 0;
            while (strLen - index > LOG_MAX_LENGTH) {
                Log.v(TAG, msg.substring(index, index + LOG_MAX_LENGTH));
                index += LOG_MAX_LENGTH;
            }
            Log.v(tag, msg.substring(index));
        }

    }
}