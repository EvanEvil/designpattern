package com.ivan.microtalk.androidlib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 硬盘缓存
 */
public class DiskCache implements ImageCache {

    String cacheDir;


    public DiskCache(Context context) {
        cacheDir = context.getExternalCacheDir().getAbsolutePath()+ File.separator;
    }

    //从缓存中获取图片
    public Bitmap get(String url){
        File file = new File(cacheDir+url);
        if(file.exists()){

            return BitmapFactory.decodeFile(cacheDir+url);
        }
        return null;
    }

    //将图片缓存到硬盘中
    public void put(String url,Bitmap bitmap){
         FileOutputStream fos = null;

        try {
            File file = new File(cacheDir+url);
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
