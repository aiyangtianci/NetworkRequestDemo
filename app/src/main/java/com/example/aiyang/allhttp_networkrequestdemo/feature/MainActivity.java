package com.example.aiyang.allhttp_networkrequestdemo.feature;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aiyang.allhttp_networkrequestdemo.R;
import com.example.aiyang.allhttp_networkrequestdemo.ui.TopBarBaseActivity;
import com.example.aiyang.allhttp_networkrequestdemo.util.ToastUtil;

public class MainActivity extends TopBarBaseActivity implements View.OnClickListener{
    MainActivity thisContxt =this;
    Button client_bt;
    Button connection_bt;
    Button volley_bt;
    Button okhttp_bt;
    Button rxjava_bt;
    @Override
    protected int getConentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("艾一");
        setTopLeftButton(R.mipmap.back_whait, new onClickListener() {
            @Override
            public void onClick() {
                ToastUtil.Show(MainActivity.this,"返回按钮");
            }
        });

        client_bt= (Button) findViewById(R.id.client_bt);
        client_bt.setOnClickListener(this);
        connection_bt= (Button) findViewById(R.id.connection_bt);
        connection_bt.setOnClickListener(this);
        volley_bt= (Button) findViewById(R.id.volley_bt);
        volley_bt.setOnClickListener(this);
        okhttp_bt= (Button) findViewById(R.id.okhttp_bt);
        okhttp_bt.setOnClickListener(this);
        rxjava_bt= (Button) findViewById(R.id.rxjava_bt);
        rxjava_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.client_bt:
                startActivity(new Intent(thisContxt,HttpClientActivity.class));
                break;
            case R.id.connection_bt:
                startActivity(new Intent(thisContxt,HttpUrlConnection.class));
                break;
            case R.id.volley_bt:
                startActivity(new Intent(thisContxt,VolleyActivity.class));
                break;
            case R.id.okhttp_bt:
                startActivity(new Intent(thisContxt,OkHttp3Activity.class));
                break;
            case R.id.rxjava_bt:
                startActivity(new Intent(thisContxt,RxJavaActivity.class));
                break;
        }
    }
}
