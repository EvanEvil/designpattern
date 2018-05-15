package com.ivan.microtalk.designpatterns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;


import com.ivan.microtalk.androidlib.widget.DoubleCache;
import com.ivan.microtalk.androidlib.widget.ImageLoader;
import com.ivan.microtalk.androidlib.widget.ImageLoaderTwo;
import com.ivan.microtalk.designpatterns.R;

public class MainActivity extends AppCompatActivity {

    private ImageView ivBg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivBg = (ImageView) findViewById(R.id.iv_bg);

        loadGirl2();

    }

    private void loadGirl1() {
        ImageLoader imageLoader = new ImageLoader();
        String url = "http://img3.myhsw.cn/2016-11-16/e2kab22f.jpg";
        imageLoader.displayImage(url,ivBg);
    }
    private void loadGirl2() {
        ImageLoaderTwo imageLoader = ImageLoaderTwo.getInstance();
        ImageLoaderTwo imageLoader2 = ImageLoaderTwo.getInstance();
        Log.d("ivan", "imageLoader: "+imageLoader);
        Log.d("ivan", "imageLoader2: "+imageLoader2);

        String url = "http://img1.utuku.china.com/uploadimg/ent/20171127/297150e8-e2cd-4a3e-9a69-7cf072619d30.jpg";
        imageLoader.setImageCache(new DoubleCache(this));
        imageLoader.displayImage(url,ivBg);
    }
}
