package com.hstmpl.image;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.hstmpl.util.LogUtils;

public class GlideImageLoaderStrategy implements ImageLoaderStrategy, RequestListener<Drawable> {

    private String url;

    @Override
    public void loadImage(Context cxt, String url, ImageView iv) {
        this.url = url;
        if (TextUtils.isEmpty(url)) {
            LogUtils.e("GlideImageLoaderStrategy", "url->" + url);
            return;
        }
        Glide.with(cxt).load(url).listener(this)
                .apply(getOptions(new ImageLoaderBuilder.Builder().build()))
                .into(iv);
    }

    @Override
    public void loadImage(Context cxt, ImageLoaderBuilder imageLoaderBuilder) {
        this.url = imageLoaderBuilder.getUrl();
        if (TextUtils.isEmpty(url)) {
            LogUtils.e("GlideImageLoaderStrategy", "url->" + url);
            return;
        }
        Glide.with(cxt).load(imageLoaderBuilder.getUrl())
                .listener(this)
                .apply(getOptions(imageLoaderBuilder))
                .into(imageLoaderBuilder.getImgView());
    }

    @SuppressLint("CheckResult")
    private RequestOptions getOptions(ImageLoaderBuilder loader) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(loader.getPlaceHolder())
                .error(loader.getErrorImg())
                .skipMemoryCache(!loader.isCache());
        requestOptions.diskCacheStrategy(loader.isCache() ? DiskCacheStrategy.DATA : DiskCacheStrategy.NONE);
        if (loader.getTransformation() != null) {
            requestOptions.transform(loader.getTransformation());
        }
        return requestOptions;

    }


    @Override
    public boolean onLoadFailed(GlideException e, Object model, Target target, boolean isFirstResource) {
//        ToastUtils.showShort("图片加载失败");
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        return false;
    }
}
