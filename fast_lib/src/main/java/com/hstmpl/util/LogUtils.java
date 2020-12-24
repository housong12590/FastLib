package com.hstmpl.util;

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

    private static final String TAG = "CIIN";
    private static Level level = Level.INFO;

    public static void setLevel(Level level) {
        LogUtils.level = level;
    }

    public enum Level {
        NONE(0),
        ERROR(1),
        WARN(2),
        DEBUG(3),
        INFO(4);

        int value;

        Level(int value) {
            this.value = value;
        }
    }

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

    public static void w(String msg) {
        w(TAG, msg);
    }


    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (level.value >= Level.INFO.value) {
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
        if (level.value >= Level.DEBUG.value) {
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
        if (level.value >= Level.ERROR.value) {
            int strLen = msg.length();
            int index = 0;
            while (strLen - index > LOG_MAX_LENGTH) {
                Log.e(TAG, msg.substring(index, index + LOG_MAX_LENGTH));
                index += LOG_MAX_LENGTH;
            }
            Log.e(tag, msg.substring(index));
        }

    }

    public static void w(String tag, String msg) {
        if (level.value >= Level.WARN.value) {
            int strLen = msg.length();
            int index = 0;
            while (strLen - index > LOG_MAX_LENGTH) {
                Log.e(TAG, msg.substring(index, index + LOG_MAX_LENGTH));
                index += LOG_MAX_LENGTH;
            }
            Log.w(tag, msg.substring(index));
        }
    }
}