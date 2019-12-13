package com.ws.fastlib.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.List;
import java.util.Stack;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/09/23
 *     desc  : Activity相关工具类
 * </pre>
 */
public final class ActivityUtils {

    private static Stack<Activity> mActivityStack;

    /**
     * 添加一个Activity到堆栈中
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        if (null == mActivityStack) {
            mActivityStack = new Stack<>();
        }
        mActivityStack.add(activity);
    }

    /**
     * 从堆栈中移除指定的Activity
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (mActivityStack != null && activity != null) {
            mActivityStack.remove(activity);
        }
    }

    /**
     * 查询activity 是否存在
     *
     * @param className 全类名
     * @return 查找到的activity
     */
    public static Activity queryActivity(String className) {
        if (mActivityStack != null && !TextUtils.isEmpty(className)) {
            for (Activity activity : mActivityStack) {
                if (activity.getClass().getName().equals(className)) {
                    return activity;
                }
            }
        }
        return null;
    }

    /**
     * 获取顶部的Activity
     *
     * @return 顶部的Activity
     */
    public static Activity getTopActivity() {
        if (mActivityStack == null && mActivityStack.isEmpty()) {
            return null;
        } else {
            return mActivityStack.get(mActivityStack.size() - 1);
        }
    }

    /**
     * 结束所有的Activity，退出应用
     */
    public static void removeAllActivity() {
        if (mActivityStack != null && mActivityStack.size() > 0) {
            for (Activity activity : mActivityStack) {
                activity.finish();
            }
        }
    }

    /**
     * 判断是否存在Activity
     *
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(String packageName, String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(Utils.getContext().getPackageManager().resolveActivity(intent, 0) == null ||
                intent.resolveActivity(Utils.getContext().getPackageManager()) == null ||
                Utils.getContext().getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }

    /**
     * 启动Activity
     *
     * @param activity activity
     * @param cls      activity类
     */
    public static void startActivity(Activity activity, Class<?> cls) {
        startActivity(activity, null, activity.getPackageName(), cls.getName(), null);
    }

    /**
     * 启动Activity
     *
     * @param extras   extras
     * @param activity activity
     * @param cls      activity类
     */
    public static void startActivity(Bundle extras, Activity activity, Class<?> cls) {
        startActivity(activity, extras, activity.getPackageName(), cls.getName(), null);
    }

    /**
     * 启动Activity
     *
     * @param activity  activity
     * @param cls       activity类
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void startActivity(Activity activity, Class<?> cls, int enterAnim, int exitAnim) {
        startActivity(activity, null, activity.getPackageName(), cls.getName(), null);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 启动Activity
     *
     * @param extras    extras
     * @param activity  activity
     * @param cls       activity类
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void startActivity(Bundle extras, Activity activity, Class<?> cls, int enterAnim, int exitAnim) {
        startActivity(activity, extras, activity.getPackageName(), cls.getName(), null);
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 启动Activity
     *
     * @param activity activity
     * @param cls      activity类
     * @param options  跳转动画
     */
    public static void startActivity(Activity activity, Class<?> cls, Bundle options) {
        startActivity(activity, null, activity.getPackageName(), cls.getName(), options);
    }

    /**
     * 启动Activity
     *
     * @param extras   extras
     * @param activity activity
     * @param cls      activity类
     * @param options  跳转动画
     */
    public static void startActivity(Bundle extras, Activity activity, Class<?> cls, Bundle options) {
        startActivity(activity, extras, activity.getPackageName(), cls.getName(), options);
    }

    /**
     * 启动Activity
     *
     * @param pkg 包名
     * @param cls 全类名
     */
    public static void startActivity(String pkg, String cls) {
        startActivity(Utils.getContext(), null, pkg, cls, null);
    }

    /**
     * 启动Activity
     *
     * @param extras extras
     * @param pkg    包名
     * @param cls    全类名
     */
    public static void startActivity(Bundle extras, String pkg, String cls) {
        startActivity(Utils.getContext(), extras, pkg, cls, extras);
    }

    /**
     * 启动Activity
     *
     * @param pkg     包名
     * @param cls     全类名
     * @param options 动画
     */
    public static void startActivity(String pkg, String cls, Bundle options) {
        startActivity(Utils.getContext(), null, pkg, cls, options);
    }

    /**
     * 启动Activity
     *
     * @param extras  extras
     * @param pkg     包名
     * @param cls     全类名
     * @param options 动画
     */
    public static void startActivity(Bundle extras, String pkg, String cls, Bundle options) {
        startActivity(Utils.getContext(), extras, pkg, cls, options);
    }

    private static void startActivity(Context context, Bundle extras, String pkg, String cls, Bundle options) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null) intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (options == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            context.startActivity(intent);
        } else {
            context.startActivity(intent, options);
        }
    }

    /**
     * 获取launcher activity
     *
     * @param packageName 包名
     * @return launcher activity
     */
    public static String getLauncherActivity(String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager pm = Utils.getContext().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent, 0);
        for (ResolveInfo aInfo : info) {
            if (aInfo.activityInfo.packageName.equals(packageName)) {
                return aInfo.activityInfo.name;
            }
        }
        return "no " + packageName;
    }
}
