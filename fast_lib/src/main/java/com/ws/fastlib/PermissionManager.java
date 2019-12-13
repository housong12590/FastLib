package com.ws.fastlib;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.text.TextUtils;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.util.IOUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ws.fastlib.utils.AppUtils;
import com.ws.fastlib.utils.IntentUtils;
import com.ws.fastlib.utils.OSUtils;
import com.ws.fastlib.utils.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.functions.Consumer;

/**
 * @author hous on 2018/8/4.
 */

public class PermissionManager {

    public interface OnAuthorizeCallback {

        void onGrant();

        void onFailure();
    }

    /**
     * 获取联系人
     *
     * @param activity activity
     * @param callback 授权成功回调
     */
    public static void readContacts(Activity activity, OnAuthorizeCallback callback) {
        String[] permissions = {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.GET_ACCOUNTS
        };
        request(activity, true, callback, permissions);
    }

    /**
     * 获取存储权限
     *
     * @param activity activity
     * @param callback 授权成功回调
     */
    public static void storage(Activity activity, OnAuthorizeCallback callback) {
        String[] permissions = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        request(activity, true, callback, permissions);
    }

    /**
     * 使用相机权限
     *
     * @param activity activity
     * @param callback 授权成功回调
     */
    public static void camera(Activity activity, OnAuthorizeCallback callback) {
        String[] permissions = {
                Manifest.permission.CAMERA
        };
        request(activity, true, callback, permissions);
    }

    /**
     * 录音权限
     *
     * @param activity activity
     * @param callback 授权成功回调
     */
    public static void record(Activity activity, OnAuthorizeCallback callback) {
        String[] permissions = {
                Manifest.permission.RECORD_AUDIO
        };
        request(activity, true, callback, permissions);
    }


    /**
     * 请求位置权限
     *
     * @param activity activity
     * @param callback 授权成功回调
     */
    public static void location(Activity activity, OnAuthorizeCallback callback) {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        request(activity, false, callback, permissions);
    }

    public static void request(Activity activity, boolean showError, OnAuthorizeCallback callback, String... permissions) {
        if (permissions.length == 0) {
            throw new IllegalArgumentException("permissions length is zero");
        }
        new RxPermissions(activity).requestEach(permissions).toList()
                .subscribe(new Consumer<List<Permission>>() {
                    @Override
                    public void accept(List<Permission> permissions) throws Exception {
                        StringBuilder sb = new StringBuilder("");
                        for (Permission permission : permissions) {
                            String permissionName = getPermissionName(permission.name);
                            Pattern compile = Pattern.compile(permissionName);
                            Matcher matcher = compile.matcher(sb.toString());
                            if (!matcher.find() && !permission.granted) {
                                if (sb.length() != 0) {
                                    sb.append("、");
                                }
                                sb.append(permissionName);
                            }
                        }
                        String permissionName = sb.toString();
                        if (TextUtils.isEmpty(permissionName)) {
                            if (callback != null) {
                                callback.onGrant();
                            }
                        } else {
                            if (showError) {
                                showPermissionDialog(activity, sb.toString());
                            }
                            if (callback != null) {
                                callback.onFailure();
                            }
                        }
                    }
                });
    }

    private static void showPermissionDialog(Context context, String permissionName) {
        String appName = context.getString(R.string.app_name);
        String content = "{0}需要您的{1}，请在\"设置-应用管理-{0}-权限管理\"中开启相应的权限。";
        content = MessageFormat.format(content, appName, permissionName);
        new MaterialDialog.Builder(context).
                title("温馨提示")
                .content(content)
                .positiveText("传送")
                .negativeText("取消")
                .negativeColor(context.getResources().getColor(R.color.gray_999999))
                .positiveColor(context.getResources().getColor(R.color.gray_999999))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        gotoPermissionSetting();
                    }
                }).show();
    }


    private static String getPermissionName(String permission) {
        String permissionName = "";
        switch (permission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                permissionName = "储存权限";
                break;
            case Manifest.permission.SEND_SMS:
            case Manifest.permission.RECEIVE_SMS:
            case Manifest.permission.READ_SMS:
            case Manifest.permission.RECEIVE_WAP_PUSH:
            case Manifest.permission.RECEIVE_MMS:
                permissionName = "短信权限";
                break;
            case Manifest.permission.READ_PHONE_STATE:
            case Manifest.permission.CALL_PHONE:
            case Manifest.permission.READ_CALL_LOG:
            case Manifest.permission.WRITE_CALL_LOG:
            case Manifest.permission.ADD_VOICEMAIL:
            case Manifest.permission.USE_SIP:
            case Manifest.permission.PROCESS_OUTGOING_CALLS:
                permissionName = "电话权限";
                break;
            case Manifest.permission.RECORD_AUDIO:
                permissionName = "录音权限";
                break;
            case Manifest.permission.ACCESS_FINE_LOCATION:
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                permissionName = "位置权限";
                break;
            case Manifest.permission.READ_CONTACTS:
            case Manifest.permission.WRITE_CONTACTS:
            case Manifest.permission.GET_ACCOUNTS:
                permissionName = "通讯录权限";
                break;
            case Manifest.permission.CAMERA:
                permissionName = "相机权限";
                break;
            case Manifest.permission.READ_CALENDAR:
            case Manifest.permission.WRITE_CALENDAR:
                permissionName = "日历权限";
                break;
            default:
                break;
        }
        return permissionName;
    }

    public static boolean gotoPermissionSetting() {
        boolean success = true;
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String packageName = AppUtils.getAppPackageName();

        OSUtils.ROM romType = OSUtils.getRomType();
        switch (romType) {
            case EMUI: // 华为
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
                break;
            case Flyme: // 魅族
                intent.setAction("com.meizu.safe.security.SHOW_APPSEC");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra("packageName", packageName);
                break;
            case MIUI: // 小米
                String rom = getMiuiVersion();
                if ("V6".equals(rom) || "V7".equals(rom)) {
                    intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                    intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                    intent.putExtra("extra_pkgname", packageName);
                } else if ("V8".equals(rom) || "V9".equals(rom)) {
                    intent.setAction("miui.intent.action.APP_PERM_EDITOR");
                    intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
                    intent.putExtra("extra_pkgname", packageName);
                } else {
                    intent = IntentUtils.getAppDetailsSettingsIntent(packageName);
                }
                break;
            case Sony: // 索尼
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
                break;
            case ColorOS: // OPPO
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.PermissionManagerActivity"));
                break;
            case EUI: // 乐视
                intent.putExtra("packageName", packageName);
                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps"));
                break;
            case LG: // LG
                intent.setAction("android.intent.action.MAIN");
                intent.putExtra("packageName", packageName);
                ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
                intent.setComponent(comp);
                break;
            case SamSung: // 三星
            case SmartisanOS: // 锤子
                AppUtils.getAppDetailsSettings();
                break;
            default:
                intent.setAction(Settings.ACTION_SETTINGS);
                success = false;
                break;
        }
        try {
            Utils.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            // 跳转失败, 前往普通设置界面
            Intent settingIntent = IntentUtils.getSettingIntent();
            Utils.getContext().startActivity(settingIntent);
            success = false;
        }
        return success;
    }

    private static String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            IOUtils.close(input);
        }
        return line;
    }
}
