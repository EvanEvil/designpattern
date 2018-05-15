package com.ivan.microtalk.androidlib.widget;

import android.graphics.Bitmap;

/**
 * 图片缓存
 */
public interface ImageCache {

    Bitmap get(String url);
    void put(String url,Bitmap bitmap);
}
