package com.example.aiyang.allhttp_networkrequestdemo.feature;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aiyang.allhttp_networkrequestdemo.R;
import com.example.aiyang.allhttp_networkrequestdemo.ui.TopBarBaseActivity;
import com.example.aiyang.allhttp_networkrequestdemo.util.ToastUtil;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by aiyang on 2017/5/8.
 * Client请求
 */

public class HttpClientActivity extends TopBarBaseActivity{
    private HttpClientActivity thisContext=this;
    private Button send_button;
    private TextView response_tv;
    @Override
    protected int getConentView() {
        return R.layout.httplayout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("HttpClient");
        setTopLeftButton(R.mipmap.back_whait, new onClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        send_button= (Button) findViewById(R.id.send_button);
        response_tv= (TextView) findViewById(R.id.response_tv);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestGet();
            }
        });
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what==1){
                response_tv.setText(msg.obj.toString());
            }else{
                ToastUtil.Show(thisContext,"请求出错");
            }
        }
    };
    private void sendRequestGet(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //用HttpClient发送请求，分为五步
                //第一步：创建HttpClient对象   （AS需要到Gradle中配置jar包引用）
                HttpClient httpCient = new DefaultHttpClient();
                //第二步：创建代表请求的对象,参数是访问的服务器地址
                HttpGet httpGet = new HttpGet("http://www.baidu.com");

                //第三步：执行请求，获取服务器发还的相应对象
                try {
                    HttpResponse httpResponse = httpCient.execute(httpGet);
                    //第四步：检查相应的状态是否正常：检查状态码的值是200表示正常
                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                        //第五步：从相应对象当中取出数据，放到entity当中
                        HttpEntity entity = httpResponse.getEntity();
                        String response = EntityUtils.toString(entity,"utf-8");//将entity当中的数据转换为字符串

                        sendMsg(1,response);

                    }else{
                        sendMsg(0,"");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void sendMsg(int what,String response){
        Message message=new Message();
        message.what=what;
        message.obj=response;
        handler.sendMessage(message);
    }

}
