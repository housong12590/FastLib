package com.hstmpl.net.observer;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import com.hstmpl.net.error.RequestError;

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
            ToastUtils.showShort("请检查当前的网络环境！");
        } else if (e instanceof SocketTimeoutException) {
            ToastUtils.showShort("网络连接超时,请稍候重试!");
        } else if (e instanceof HttpException) {
            ToastUtils.showShort("服务器繁忙,请稍候再试!");
        } else if (e instanceof MalformedJsonException || e instanceof JsonSyntaxException) {
            LogUtils.e("json解析异常---->>>" + e.getMessage());
        } else if (e instanceof RequestError) {
            ToastUtils.showShort(e.getMessage());
        }
        e.printStackTrace();
    }


}
