package com.itheima.progressbutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressButton progressButton = (ProgressButton) findViewById(R.id.progressButton);
        progressButton.setMax(200);
        progressButton.setProgress(40);
        progressButton.setText("20%");
    }
}
