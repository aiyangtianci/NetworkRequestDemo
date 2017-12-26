package com.example.aiyang.allhttp_networkrequestdemo.feature;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aiyang.allhttp_networkrequestdemo.R;
import com.example.aiyang.allhttp_networkrequestdemo.io.NetworkActivity;
import com.example.aiyang.allhttp_networkrequestdemo.ui.TopBarBaseActivity;

/**
 * Created by aiyang on 2017/5/8.
 */

public class HttpUrlConnection extends TopBarBaseActivity implements View.OnClickListener{
    Button con_get_bt;
    Button con_post_bt;
    @Override
    protected int getConentView() {
        return R.layout.httpurlconnection_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("HttpUrlConnection");
        setTopLeftButton(R.mipmap.back_whait, new onClickListener() {
            @Override
            public void onClick() {
               finish();
            }
        });
        con_get_bt= (Button) findViewById(R.id.con_get_bt);
        con_post_bt= (Button) findViewById(R.id.con_post_bt);
        con_post_bt.setOnClickListener(this);
        con_get_bt.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.con_get_bt:
                startActivity(new Intent(HttpUrlConnection.this, NetworkActivity.class).putExtra("action","NETWORK_GET"));
                break;
            case R.id.con_post_bt:
                startActivity(new Intent(HttpUrlConnection.this, NetworkActivity.class).putExtra("action","NETWORK_POST"));
                break;
        }
    }
}
