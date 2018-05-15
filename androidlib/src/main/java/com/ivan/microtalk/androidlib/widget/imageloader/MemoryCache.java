package com.ivan.microtalk.androidlib.widget;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 内存缓存
 */
public class MemoryCache implements ImageCache {
    private LruCache<String, Bitmap> lruCache;

    public MemoryCache() {
        initMemoryCache();
    }

    private void initMemoryCache() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024;
        int sizeCache = maxMemory / 4;
        lruCache = new LruCache<String, Bitmap>(sizeCache) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }
    @Override
    public Bitmap get(String url){
        return lruCache.get(url);
    }
    @Override
    public void put(String url,Bitmap bitmap){
        lruCache.put(url,bitmap);
    }

}
