package com.ivan.microtalk.androidlib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * 双缓存(内存和SD卡缓存)
 */
public class DoubleCache implements ImageCache{

    private final MemoryCache memoryCache;
    private final DiskCache diskCache;

    public DoubleCache(Context context) {
        memoryCache = new MemoryCache();
        diskCache = new DiskCache(context);
    }


    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap==null) {
            Log.d("tag", "内存缓存: "+bitmap);
            bitmap = diskCache.get(url);
            Log.d("tag", "SD卡缓存: "+bitmap);
        }else {
            Log.d("tag", "内存缓存: ");
        }
        return bitmap;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        memoryCache.put(url,bitmap);
        diskCache.put(url,bitmap);
    }
}
