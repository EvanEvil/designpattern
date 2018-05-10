package com.ivan.microtalk.androidlib.widget;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * ImageCache : 图片缓存
 */
public class ImageCache {

    LruCache<String,Bitmap> mImageCache;
    public ImageCache() {
        initImageCache();
    }

    /**
     * 设置图片缓存
     */
    private void initImageCache() {
        int maxMemory = (int) (Runtime.getRuntime().maxMemory()/1024);
        int cacheSize = maxMemory/4;
        mImageCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
    }

    public void put(String url,Bitmap bitmap){
        mImageCache.put(url,bitmap);
    }
    public Bitmap get(String url){
       return mImageCache.get(url);
    }
}
