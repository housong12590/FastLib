package com.ws.fastlib.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.ws.fastlib.R;

import java.io.File;

public class ImageLoaderBuilder {

    private File file;
    private int errorImg;
    private String url;
    private int placeHolder;
    private ImageView imgView;
    private boolean isCache;
    private Transformation<Bitmap> transformation;

    private ImageLoaderBuilder() {

    }

    private ImageLoaderBuilder(Builder builder) {
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.errorImg = builder.errorImg;
        this.imgView = builder.imgView;
        this.file = builder.file;
        this.isCache = builder.isCache;
        this.transformation = builder.transformation;
    }

    public File getFile() {
        return file;
    }

    public int getErrorImg() {
        return errorImg;
    }

    public String getUrl() {
        return url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public boolean isCache() {
        return isCache;
    }

    public Transformation<Bitmap> getTransformation() {
        return transformation;
    }

    public static class Builder {

        private int placeHolder;
        private ImageView imgView;
        private String url;
        private int errorImg;
        private File file;
        private boolean isCache;
        private Transformation<Bitmap> transformation;

        public Builder() {
            placeHolder = R.drawable.ic_placeholder; //设置默认的图片,不用每次都写
            errorImg = R.drawable.ic_placeholder;
            isCache = true; //默认缓存
        }

        public Builder transformation(Transformation<Bitmap> transformation) {
            this.transformation = transformation;
            return this;
        }

        public Builder error(int resId) {
            this.errorImg = resId;
            return this;
        }

        public Builder placeHolder(int resId) {
            this.placeHolder = resId;
            return this;
        }

        public Builder load(String url) {
            this.url = url;
            return this;
        }

        public Builder load(File file) {
            this.file = file;
            return this;
        }

        public Builder into(ImageView iv) {
            this.imgView = iv;
            return this;
        }

        public ImageLoaderBuilder build() {
            return new ImageLoaderBuilder(this);
        }

        public Builder isCache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }
    }

}
