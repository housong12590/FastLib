package com.hstmpl.image;

import android.content.Context;
import android.widget.ImageView;


public interface ImageLoaderStrategy {

    void loadImage(Context cxt, String url, ImageView iv);


    void loadImage(Context cxt, ImageLoaderBuilder imageLoaderBuilder);
}
