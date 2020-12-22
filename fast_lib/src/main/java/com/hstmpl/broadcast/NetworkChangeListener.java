package com.hstmpl.broadcast;


import com.hstmpl.util.NetworkUtils;

public interface NetworkChangeListener {

    void onChange(NetworkUtils.NetworkType networkType);
}
