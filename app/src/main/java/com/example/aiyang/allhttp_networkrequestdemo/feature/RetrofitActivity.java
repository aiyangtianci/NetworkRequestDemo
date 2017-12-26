package com.example.aiyang.allhttp_networkrequestdemo.feature;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aiyang.allhttp_networkrequestdemo.R;
import com.example.aiyang.allhttp_networkrequestdemo.http.ApiManager;
import com.example.aiyang.allhttp_networkrequestdemo.model.GankBean;
import com.example.aiyang.allhttp_networkrequestdemo.ui.TopBarBaseActivity;
import com.example.aiyang.allhttp_networkrequestdemo.util.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aiyang on 2017/5/17.
 */

public class RetrofitActivity extends TopBarBaseActivity implements View.OnClickListener{
    Button retrofit_get;
    Button retrofit_get2;
    Button retrofit_post3;
    TextView response_tv;
    @Override
    protected int getConentView() {
        return 0;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("Retrofit");
        setTopLeftButton(R.mipmap.back_whait, new onClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });

        bindUi();
    }

    private void bindUi() {
        retrofit_get= (Button) findViewById(R.id.retrofit_get);
        retrofit_get.setOnClickListener(this);
        retrofit_get2= (Button) findViewById(R.id.retrofit_get2);
        retrofit_get2.setOnClickListener(this);
        retrofit_post3= (Button) findViewById(R.id.retrofit_post3);
        retrofit_post3.setOnClickListener(this);
        response_tv=(TextView) findViewById(R.id.response_tv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.retrofit_get:
                retrofit_get();
                break;
            case R.id.retrofit_get2:
                retrofit_get2();
                break;
            case R.id.retrofit_post3:
                retrofit_post();
                break;
        }
    }

    private void retrofit_get(){
        //创建retrofit
        Retrofit retrofit =new Retrofit.Builder()
                        .baseUrl("http://gank.io/")
                        .addConverterFactory(GsonConverterFactory.create())//使用gson工厂
                        .build();
        //create接口
        ApiManager api =retrofit.create(ApiManager.class);//create实际上是通过代理的方式拿到的

        //得到call
        Call<GankBean> call =api.getAndroidInfo();
        //异步请求
        call.enqueue(new Callback<GankBean>() {
            @Override
            public void onResponse(Call<GankBean> call, Response<GankBean> response) {
                GankBean.ResultsBean bean=response.body().getResults().get(0);
                response_tv.setText( "_id:" + bean.get_id() + "\n"
                        + "createdAt：" + bean.getCreatedAt() + "\n"
                        + "desc：" + bean.getDesc() + "\n"
                        + "images:" + bean.getImages() + "\n"
                        + "publishedAt:" + bean.getPublishedAt() + "\n"
                        + "source" + bean.getSource() + "\n"
                        + "type:" + bean.getType() + "\n"
                        + "url: " + bean.getUrl() + "\n"
                        + "who:" + bean.getWho());
            }

            @Override
            public void onFailure(Call<GankBean> call, Throwable t) {

            }
        });
    }

    private void retrofit_get2(){
        Retrofit  retrofit =new Retrofit.Builder()
                        .baseUrl("http://op.juhe.cn/")
                        .build();
        ApiManager api =retrofit.create(ApiManager.class);
//        Call<ResponseBody> call =api.getWeather("4ea58de8a7573377cec0046f5e2469d5");
        Map<String ,String > params= new HashMap<>();
        params.put("cityname","深圳");
        params.put("key","4ea58de8a7573377cec0046f5e2469d5");
        Call<ResponseBody> call =api.getWeather2(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String info =response.body().string();
                    response_tv.setText(info);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void retrofit_post(){
        Retrofit retrofit =new Retrofit.Builder()
                        .baseUrl("")
                        .build();
        ApiManager apiManager =retrofit.create(ApiManager.class);
        apiManager.editUser(2,"myname").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.body().string()!=""){
                        ToastUtil.Show(RetrofitActivity.this,"成功");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

//    private void rxjava_post(){
//        Retrofit retrofit =new Retrofit.Builder()
//                .baseUrl("")
//                .build();
//        retrofit.create(ApiManager.class).getUserForRX("id").subscribe(new Action1<User>() {
//            @Override
//            public void call(User user) {
//                ToastUtil.Show(RetrofitActivity.this,"成功");
//            }
//        });
//    }
}
