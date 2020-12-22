package com.hstmpl.net.observer;

import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import com.hstmpl.net.error.RequestError;
import com.hstmpl.util.LogUtils;
import com.hstmpl.util.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class DefaultObserver<T> implements SingleObserver<T> {


//    private static ConcurrentMap<Activity, CompositeDisposable> map = new ConcurrentHashMap<>();


    @Override
    public void onSubscribe(Disposable d) {
//        Activity activity = ActivityUtils.getTopActivity();
//        if (activity != null) {
//            CompositeDisposable compositeDisposable = map.get(activity);
//            if (compositeDisposable == null) {
//                compositeDisposable = new CompositeDisposable();
//                map.put(activity, compositeDisposable);
//            }
//            compositeDisposable.add(d);
//        }
//        compositeDisposable.add(d);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ConnectException || e instanceof UnknownHostException) {
            ToastUtils.show("请检查当前的网络环境！");
        } else if (e instanceof SocketTimeoutException) {
            ToastUtils.show("网络连接超时,请稍候重试!");
        } else if (e instanceof HttpException) {
            ToastUtils.show("服务器繁忙,请稍候再试!");
        } else if (e instanceof MalformedJsonException || e instanceof JsonSyntaxException) {
            LogUtils.e("json解析异常---->>>" + e.getMessage());
        } else if (e instanceof RequestError) {
            ToastUtils.show(e.getMessage());
        }
        e.printStackTrace();
    }


}
