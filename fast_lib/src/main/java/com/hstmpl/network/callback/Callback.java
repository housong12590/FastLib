package com.hstmpl.network.callback;


public interface Callback<T> {

    void success(T t);

    void failure(Throwable e);
}
