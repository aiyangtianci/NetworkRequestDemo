package com.example.aiyang.allhttp_networkrequestdemo.feature;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aiyang.allhttp_networkrequestdemo.R;
import com.example.aiyang.allhttp_networkrequestdemo.ui.TopBarBaseActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp3
 * Created by aiyang on 2017/5/12.
 */

public class OkHttp3Activity extends TopBarBaseActivity implements View.OnClickListener {
    private OkHttpClient mOkHttpClient;//okHttpClient 实例
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");
    Button ok_get_bt;
    Button ok_post_bt;
    @Override
    protected int getConentView() {
        return R.layout.activity_okhttp3_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("OkHttp3");
        setTopLeftButton(R.mipmap.back_whait, new onClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        init();
        getOkHttpInstance();
    }

    private void init() {
        ok_get_bt= (Button) findViewById(R.id.ok_get_bt);
        ok_get_bt.setOnClickListener(this);
        ok_post_bt= (Button) findViewById(R.id.ok_post_bt);
        ok_post_bt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ok_get_bt:
                getAsynHttp();
                break;
            case R.id.ok_post_bt:
                postAsynHttp();
                break;
        }
    }
    //获得实例  取消请求call.cancel()
    private void getOkHttpInstance(){
        File sdcache = getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(20, TimeUnit.SECONDS)//设置写入超时时间
//                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize))//设置文件缓存
                .build();
    }
    //异步GET请求
    private void getAsynHttp() {
        mOkHttpClient=new OkHttpClient();
        final CacheControl.Builder builder = new CacheControl.Builder();
        builder.maxAge(10, TimeUnit.MILLISECONDS);
        CacheControl cache = new CacheControl.Builder().maxAge(10,TimeUnit.SECONDS) .build();
        Request.Builder requestBuilder = new Request.Builder().cacheControl(cache).url("http://www.baidu.com");
        //可以省略，默认是GET请求
        requestBuilder.method("GET",null);
        Request request = requestBuilder.build();
        Call mcall= mOkHttpClient.newCall(request);
        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    Log.i("wangshu", "cache---" + str);
                } else {
                    response.body().string();
                    String str = response.networkResponse().toString();
                    Log.i("wangshu", "network---" + str);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    //异步POST请求
    //OkHttp3异步POST请求和OkHttp2.x有一些差别就是没有FormEncodingBuilder这个类，替代它的是功能更加强大的FormBody
    private void postAsynHttp() {
        mOkHttpClient=new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("size", "10")
                .build();
        Request request = new Request.Builder()
                .url("http://api.1-blog.com/biz/bizserver/article/list.do")
                .post(formBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.i("wangshu", str);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }
    //将sdcard根目录的wangshu.txt文件上传到服务器上：
    private void postAsynFile() {
        mOkHttpClient=new OkHttpClient();
        File file = new File("/sdcard/wangshu.txt");
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("wangshu",response.body().string());
            }
        });
    }
    //异步下载文件
    private void downAsynFile() {
        mOkHttpClient = new OkHttpClient();
        String url = "http://img.my.csdn.net/uploads/201603/26/1458988468_5804.jpg";
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                InputStream inputStream = response.body().byteStream();
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(new File("/sdcard/wangshu.jpg"));
                    byte[] buffer = new byte[2048];
                    int len = 0;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, len);
                    }
                    fileOutputStream.flush();
                } catch (IOException e) {
                    Log.i("wangshu", "IOException");
                    e.printStackTrace();
                }

                Log.d("wangshu", "文件下载成功");
            }
        });
    }
    //异步上传Multipart文件
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private void sendMultipart(){
        mOkHttpClient = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "wangshu")
                .addFormDataPart("image", "wangshu.jpg",
                        RequestBody.create(MEDIA_TYPE_PNG, new File("/sdcard/wangshu.jpg")))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + "...")
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("wangshu", response.body().string());
            }
        });
    }

}
