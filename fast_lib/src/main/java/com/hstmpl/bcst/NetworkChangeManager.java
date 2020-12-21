package com.hstmpl.bcst;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.hstmpl.util.LogUtils;
import com.hstmpl.util.NetworkUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 网络状态变化监听管理器
 */
public class NetworkChangeManager {

    private static final Set<NetworkChangeListener> observers = new HashSet<>();
    private static NetworkChangeReceiver receiver = new NetworkChangeReceiver();

    private static void notifyObservers(NetworkUtils.NetworkType networkType) {
        for (NetworkChangeListener observer : observers) {
            observer.onChange(networkType);
        }
    }

    public static NetworkUtils.NetworkType getNetworkType() {
        return receiver.mType;
    }

    public static void registerObserver(NetworkChangeListener observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public static void unregisterObserver(NetworkChangeListener observer) {
        if (observer != null) {
            observers.remove(observer);
        }
    }

    public static void registerReceiver(Context context) {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(receiver, intentFilter);
    }

    public static void unregisterReceiver(Context context) {
        context.unregisterReceiver(receiver);
    }

    public static class NetworkChangeReceiver extends BroadcastReceiver {

        private NetworkUtils.NetworkType mType = NetworkUtils.getNetworkType();

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                NetworkUtils.NetworkType networkType = NetworkUtils.getNetworkType();
                if (mType == networkType) {
                    return;
                }
                mType = networkType;
                LogUtils.d("网络状态变化: " + networkType);
                notifyObservers(networkType);
            }
        }
    }
}
