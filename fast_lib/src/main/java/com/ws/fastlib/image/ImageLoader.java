package com.ws.fastlib.image;

import android.content.Context;
import android.widget.ImageView;

import com.ws.fastlib.R;


public class ImageLoader {

    private static ImageLoaderStrategy strategy = new GlideImageLoaderStrategy();

    public static void loadImage(Context cxt, ImageLoaderBuilder imageLoaderBuilder) {
        strategy.loadImage(cxt, imageLoaderBuilder);
    }

    public static void loadImage(Context cxt, String url, ImageView iv) {
        strategy.loadImage(cxt, url, iv);
    }

    public static void loadAvatar(Context cxt, String url, ImageView iv) {
        strategy.loadImage(cxt, new ImageLoaderBuilder.Builder()
                .load(url)
                .placeHolder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(iv)
                .build());
    }
}
