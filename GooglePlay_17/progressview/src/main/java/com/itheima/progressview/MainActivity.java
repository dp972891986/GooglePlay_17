package com.itheima.progressview;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressView progressView = (ProgressView) findViewById(R.id.progressView);

        progressView.setIcon(R.drawable.ic_pause);
        //想要进入viewGroup子类里面的onDraw方法必须定义一个背景
        progressView.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        progressView.setMax(2000);
        progressView.setProgress(1000);
        progressView.setNote("50%");

    }
}
