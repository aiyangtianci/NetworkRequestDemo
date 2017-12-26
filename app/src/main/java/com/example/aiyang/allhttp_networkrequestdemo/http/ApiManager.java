package com.example.aiyang.allhttp_networkrequestdemo.http;

import com.example.aiyang.allhttp_networkrequestdemo.model.GankBean;
import com.example.aiyang.allhttp_networkrequestdemo.model.User;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by aiyang on 2017/5/17.
 */

public interface ApiManager {
    //一、Get
    /**
     * 无参数请求
     */

    //第二种是结合工厂GsonFromat自动解析实体类
    @GET("api/data/Android/10/1")
    Call<GankBean> getAndroidInfo();

    /**
     * 带参
     */
    //第一种  单一参数
    @GET("onebox/weather/query?cityname=深圳")
    Call<ResponseBody> getWeather(@Query("key") String key);

    //第二种  多参数
    @GET("onebox/weather/query?")
    Call<ResponseBody> getWeather2(@QueryMap Map<String,String > parmas);


    //二、Post
    /**
     * 表单
     *
     * @Field关键字
     * @Body参数
     */
    @POST("user/info")
    Call<ResponseBody> editUser(@Field("id") int id,@Field("name") String name);

    //三、RxJava

//    @POST("user/login")
//    Call<User> login(@Field("username") String user, @Field("password") String password);
//    @GET("user/info")
//    Call<User> getUser(@Query("id") String id);

//    @POST("user/login")
//    rx.Observable<User> loginForRX(@Body User user);
//
//    @GET("user/info")
//    rx.Observable<User> getUserForRX(@Query("id") String id);
}
