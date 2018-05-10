package com.ivan.microtalk.designpatterns.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;


import com.ivan.microtalk.androidlib.widget.ImageLoader;
import com.ivan.microtalk.designpatterns.R;

public class MainActivity extends AppCompatActivity {

    private ImageView ivBg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivBg = (ImageView) findViewById(R.id.iv_bg);

        loadGirl1();

    }

    private void loadGirl1() {
        ImageLoader imageLoader = new ImageLoader();
        String url = "http://img3.myhsw.cn/2016-11-16/e2kab22f.jpg";
        imageLoader.displayImage(url,ivBg);
    }
//    private void loadGirl2() {
//        imageloader2 imageLoader = new imageloadert();
//        String url = "http://img3.myhsw.cn/2016-11-16/e2kab22f.jpg";
//        imageLoader.displayImage(url,ivBg);
//    }
}
