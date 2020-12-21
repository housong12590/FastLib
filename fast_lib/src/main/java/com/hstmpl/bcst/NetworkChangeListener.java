package com.hstmpl.bcst;


import com.hstmpl.util.NetworkUtils;

public interface NetworkChangeListener {

    void onChange(NetworkUtils.NetworkType networkType);
}
