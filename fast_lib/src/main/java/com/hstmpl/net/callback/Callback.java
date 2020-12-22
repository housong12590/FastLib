package com.hstmpl.net.callback;


public interface Callback<T> {

    void success(T t);

    void failure(Throwable e);
}
