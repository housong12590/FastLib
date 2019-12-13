package com.ws.fastlib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ws.fastlib.R;


public class LoadingDialog extends Dialog {


    public LoadingDialog(Context context) {
        this(context, R.style.loadingDialog);
        init();
    }


    public LoadingDialog(Context context, int val) {
        super(context, val);
        init();
    }


    private void init() {
        View view = View.inflate(getContext(), R.layout.dialog_loading_layout, null);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
//        params.dimAmount = 0.3f;
            window.setAttributes(params);
            setCanceledOnTouchOutside(false);
            setContentView(view);
        }
    }
}