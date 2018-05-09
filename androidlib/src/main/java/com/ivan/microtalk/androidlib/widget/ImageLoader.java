package com.ivan.microtalk.androidlib.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 知识点:
 *  1.线程池 : ExecutorService(Java中对线程池的实现) : https://blog.csdn.net/suifeng3051/article/details/49443835
 *  2.缓存 : LruCache(Android缓存类)  https://www.jianshu.com/p/b49a111147ee
 *
 * 图片加载
 * downLoadImage(String url)
 * displayImage(String url,ImageView imageView)
 * initImageCache():设置缓存
 */
public class ImageLoader {
    //图片缓存
    LruCache<String, Bitmap> mImageCache;
    //线程池,线程数量为CPU的数量
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    // UI Handler(Looper.getMainLooper()表示在主线程)
    Handler mUiHandler = new Handler(Looper.getMainLooper());


    public ImageLoader() {
        initImageCache();
    }

    /**
     * 设置图片缓存
     */
    private void initImageCache() {
        //计算可使用的最大内存
        int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024; // kb?
        //取四分之一的可用内存作为缓存
        int cacheSize = maxMemory / 4;
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //sizeOf():计算出要缓存的每张图片的大小
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

    }

    public void displayImage(final String url, final ImageView imageView) {
        imageView.setTag(url);
        //submit():这个方法接收一个Runnable实例，并且异步的执行
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downLoadImage(url);
                if (bitmap==null) {
                    return;
                }
                if(imageView.getTag().equals(url)){
                    //切换到主线程刷新imageview
                    updateImageView(imageView,bitmap);
                }
            }
        });
    }

    /**
     * 刷新imageview
     * @param imageView
     * @param bitmap
     */
    private void updateImageView(final ImageView imageView, final Bitmap bitmap) {
        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    /**
     * 下载图片
     *
     * @param imageUrl 图片URL
     * @return Bitmap
     */
    private Bitmap downLoadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
