package com.hstmpl.util;

public class XClickUtil {

    /**
     * 最近一次点击的时间
     */
    private static long mLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private static String mLastClickKey;

    /**
     * 是否是快速点击
     *
     * @param intervalMillis 时间间期（毫秒）
     * @return true:是，false:不是
     */
    public static boolean isFastDoubleClick(String key, long intervalMillis) {
        long time = System.currentTimeMillis();
        long timeInterval = Math.abs(time - mLastClickTime);
        if (timeInterval < intervalMillis && mLastClickKey.equals(key)) {
            return true;
        } else {
            mLastClickTime = time;
            mLastClickKey = key;
            return false;
        }
    }
}
