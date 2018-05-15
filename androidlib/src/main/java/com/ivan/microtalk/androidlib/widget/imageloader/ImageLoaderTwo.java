package com.ivan.microtalk.androidlib.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * ImageLoaderTwo:改造后,符合单一职责原则(ImageCache类被单独抽取出来)
 *      使用静态内部类方式来实现单例
 *
 * 知识点:
 *  1.线程池 : ExecutorService(Java中对线程池的实现) : https://blog.csdn.net/suifeng3051/article/details/49443835
 *  2.缓存 : LruCache(Android缓存类)  https://www.jianshu.com/p/b49a111147ee
 *
 * 图片加载
 * downLoadImage(String url)
 * displayImage(String url,ImageView imageView)
 * initImageCache():设置缓存
 */
public class ImageLoaderTwo {
    private static ImageLoader instance;
    ImageCache imageCache;
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    Handler mUiHander = new Handler(Looper.getMainLooper());


    private static class ImageLoaderHolder{
        private static final ImageLoaderTwo mInstance = new ImageLoaderTwo();
    }

    public static ImageLoaderTwo getInstance(){
        return ImageLoaderHolder.mInstance;
    }

    //私有构造方法
    private ImageLoaderTwo() {
    }

    public void displayImage(final String url, final ImageView imageView){

       final String tempUrl = url.substring(url.lastIndexOf("/")+1);
         Bitmap bitmap = imageCache.get(tempUrl);
        if (bitmap!=null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        Log.d("tag", "网络缓存: ");
        imageView.setTag(url);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap mBitmap = downLoadImage(url);
                if(mBitmap==null){
                    return;
                }
                if(imageView.getTag().equals(url)){
                    //切换到主线程刷新imageview
                    updateImageView(imageView,mBitmap);
                }

                imageCache.put(tempUrl,mBitmap);
            }
        });



    }

    private void updateImageView(final ImageView imageView, final Bitmap mBitmap) {
        mUiHander.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(mBitmap);
            }
        });
    }

    private Bitmap downLoadImage(String imageUrl){
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

    public void setImageCache(ImageCache imageCache){
        this.imageCache = imageCache;
    }
}
