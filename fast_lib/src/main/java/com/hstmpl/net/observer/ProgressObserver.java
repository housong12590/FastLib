package com.hstmpl.net.observer;


import com.hstmpl.net.dialog.ProgressCancelListener;
import com.hstmpl.net.dialog.ProgressDialogHandler;
import com.hstmpl.util.ActivityUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public abstract class ProgressObserver<T> extends DefaultObserver<T> implements ProgressCancelListener {


    private final ProgressDialogHandler dialog;
    //请求取消后是否关闭当前界面

    private Disposable disposable;

    public ProgressObserver() {
        dialog = new ProgressDialogHandler(ActivityUtils.getTopActivity(), this, true);
    }

    private void showProgressDialog() {
        dialog.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
    }

    private void dismissProgressDialog() {
        dialog.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        super.onSubscribe(d);
        disposable = d;
        if (!d.isDisposed()) {
            showProgressDialog();
        }
    }


    @Override
    public void onSuccess(T t) {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        super.onError(e);
    }


    @Override
    public void onCancelProgress() {
        if (!disposable.isDisposed()) {
            this.disposable.dispose();
            onCancel();
        }
    }

    public void onCancel() {

    }
}
