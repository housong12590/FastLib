package com.ws.fastlib.network.callback;


public interface Callback<T> {

    void success(T t);

    void failure(Throwable e);
}
