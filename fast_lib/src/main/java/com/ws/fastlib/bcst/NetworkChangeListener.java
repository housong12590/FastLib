package com.ws.fastlib.bcst;


import com.ws.fastlib.utils.NetworkUtils;

public interface NetworkChangeListener {

    void onChange(NetworkUtils.NetworkType networkType);
}
