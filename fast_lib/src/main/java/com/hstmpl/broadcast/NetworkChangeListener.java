package com.hstmpl.broadcast;


import com.blankj.utilcode.util.NetworkUtils;

public interface NetworkChangeListener {

    void onChange(NetworkUtils.NetworkType networkType);
}
