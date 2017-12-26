package com.example.aiyang.allhttp_networkrequestdemo.feature;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aiyang.allhttp_networkrequestdemo.R;
import com.example.aiyang.allhttp_networkrequestdemo.io.BitmapCache;
import com.example.aiyang.allhttp_networkrequestdemo.ui.TopBarBaseActivity;
import com.example.aiyang.allhttp_networkrequestdemo.util.LogUtil;
import com.example.aiyang.allhttp_networkrequestdemo.util.ToastUtil;

import org.json.JSONObject;

/**
 * Created by aiyang on 2017/5/10.
 */

public class VolleyActivity extends TopBarBaseActivity implements View.OnClickListener{
    VolleyActivity thisContent =this;
    ImageView imageView;
    NetworkImageView network_image_view;
    TextView txt;
    Button string_txt;
    Button json_txt;
    Button request_img;
    Button loader_img;
    Button network_img;

    //创建RequestQueue对象
    /**
     * 使用Volley的时候会构建一个RequestQueue。 然后发送请求。
     * 我们知道RequestQueue 内部会构造5个线程（1个缓存线程，4个网络线程）去不断的循环发送请求。
     * 如果这个时候有多个界面，每打开一个界面构建一个RequestQueue。那后台会构建很多个线程。所以最好写单例
     */
    RequestQueue mQueue ;

    @Override
    protected int getConentView() {
        return R.layout.activity_volley_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("Volley框架");
        setTopLeftButton(R.mipmap.back_whait, new onClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        findview();
        mQueue = Volley.newRequestQueue(this);
    }

    private  void findview(){
        imageView= (ImageView) findViewById(R.id.iv);
        network_image_view= (NetworkImageView) findViewById(R.id.network_image_view);
        txt= (TextView) findViewById(R.id.txt);
        string_txt= (Button) findViewById(R.id.string_txt);
        string_txt.setOnClickListener(this);
        json_txt= (Button) findViewById(R.id.json_txt);
        json_txt.setOnClickListener(this);
        request_img= (Button) findViewById(R.id.request_img);
        request_img.setOnClickListener(this);
        loader_img= (Button) findViewById(R.id.loader_img);
        loader_img.setOnClickListener(this);
        network_img= (Button) findViewById(R.id.network_img);
        network_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.string_txt:
                stringRequest();
                txt_visible();
                string_txt.setEnabled(false);
                json_txt.setEnabled(true);
                request_img.setEnabled(true);
                loader_img.setEnabled(true);
                network_img.setEnabled(true);
                break;
            case R.id.json_txt:
                jsonRequest();
                txt_visible();
                string_txt.setEnabled(true);
                json_txt.setEnabled(false);
                request_img.setEnabled(true);
                loader_img.setEnabled(true);
                network_img.setEnabled(true);
                break;
            case R.id.request_img:
                image_visible();
                ImageRequest();
                string_txt.setEnabled(true);
                json_txt.setEnabled(true);
                request_img.setEnabled(false);
                loader_img.setEnabled(true);
                network_img.setEnabled(true);
                break;

            case R.id.loader_img:
                image_visible();
                ImageLoader();
                string_txt.setEnabled(true);
                json_txt.setEnabled(true);
                request_img.setEnabled(true);
                loader_img.setEnabled(false);
                network_img.setEnabled(true);
                break;

            case R.id.network_img:
                net_image_visible();
                NetworkImageView();
                string_txt.setEnabled(true);
                json_txt.setEnabled(true);
                request_img.setEnabled(true);
                loader_img.setEnabled(true);
                network_img.setEnabled(false);
                break;
        }
    }
    private void txt_visible(){
        txt.setText("");
        txt.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        network_image_view.setVisibility(View.GONE);
    }
    private void image_visible(){
        imageView.setImageResource(0);
        imageView.setVisibility(View.VISIBLE);
        txt.setVisibility(View.GONE);
        network_image_view.setVisibility(View.GONE);
    }
    private void net_image_visible(){
        network_image_view.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        txt.setVisibility(View.GONE);
    }
    private void stringRequest(){ //默认是GET方式
        StringRequest stringRequest = new StringRequest("https://www.baidu.com",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtil.logShow("StringRequest");
                        txt.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.Show(thisContent,"StringRequest = ERROR");
            }
        });
        mQueue.add(stringRequest);
        //post方式
//        StringRequest stringRequest = new StringRequest(Method.POST, url,  listener, errorListener) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("params1", "value1");
//                map.put("params2", "value2");
//                return map;
//            }
//        };
    }
    private void jsonRequest(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://api.map.baidu.com/telematics/v3/weather?location=嘉兴&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtil.logShow("JsonObjectRequest");
                        txt.setText(response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastUtil.Show(thisContent,"JsonObjectRequest = ERROR");
            }
        });
        mQueue.add(jsonObjectRequest);
    }

    private void ImageRequest(){
        ImageRequest imageRequest = new ImageRequest(
                "http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                        LogUtil.logShow("ImageRequest");
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {//ARGB_8888可以展示最好的颜色属性，每个图片像素占据4个字节的大小，而RGB_565则表示每个图片像素占据2个字节大小
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(R.mipmap.as);
            }
        });
        mQueue.add(imageRequest);
    }
   // ImageLoader明显要比ImageRequest更加高效，因为它不仅可以帮我们对图片进行缓存，还可以过滤掉重复的链接，避免重复发送请求。
    private void ImageLoader(){
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            }

            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }
        });
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                R.mipmap.ic_launcher, R.mipmap.as);
//      imageLoader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", listener);
        imageLoader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", listener, 200, 200);
        LogUtil.logShow("ImageLoader");
    }

    //加载图片的时候它会自动获取自身的宽高，然后对比网络图片的宽度，再决定是否需要对图片进行压缩
    private void NetworkImageView(){
        ImageLoader imageLoader=new ImageLoader(mQueue,new BitmapCache());//使用cache缓存

        network_image_view.setDefaultImageResId(R.mipmap.ic_launcher);
        network_image_view.setErrorImageResId(R.mipmap.as);
        network_image_view.setImageUrl("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",
                imageLoader);
        LogUtil.logShow("NetworkImageView");
    }

}
