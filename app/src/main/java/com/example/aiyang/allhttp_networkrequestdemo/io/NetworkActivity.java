package com.example.aiyang.allhttp_networkrequestdemo.io;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.aiyang.allhttp_networkrequestdemo.R;
import com.example.aiyang.allhttp_networkrequestdemo.model.Person;
import com.example.aiyang.allhttp_networkrequestdemo.ui.TopBarBaseActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by aiyang on 2017/5/8.
 * httpurlconnetion 跳转请求页面
 */

public class NetworkActivity extends TopBarBaseActivity{
    private NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask();
    private TextView tvUrl = null;
    private TextView tvRequestHeader = null;
    private TextView tvRequestBody = null;
    private TextView tvResponseHeader = null;
    private TextView tvResponseBody = null;

    @Override
    protected int getConentView() {
        return R.layout.activity_network;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvUrl = (TextView) findViewById(R.id.tvUrl);
        tvRequestHeader = (TextView) findViewById(R.id.tvRequestHeader);
        tvRequestBody = (TextView) findViewById(R.id.tvRequestBody);
        tvResponseHeader = (TextView) findViewById(R.id.tvResponseHeader);
        tvResponseBody = (TextView) findViewById(R.id.tvResponseBody);
        setTopLeftButton(R.mipmap.back_whait, new onClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            String networkAction = intent.getStringExtra("action");
            setTitle(networkAction);
            networkAsyncTask.execute(networkAction);
        }
    }

    /**
     *   线程内部类
     *   用于进行网络请求的AsyncTask
     */
    class NetworkAsyncTask extends AsyncTask<String, Integer, Map<String, Object>> {

        public static final String NETWORK_GET = "NETWORK_GET";
        public static final String NETWORK_POST = "NETWORK_POST";
        public static final String NETWORK_POST_XML = "NETWORK_POST_XML";
        public static final String NETWORK_POST_JSON = "NETWORK_POST_JSON";

        @Override
        protected Map<String, Object> doInBackground(String... params) {
            Map<String,Object> result = new HashMap<>();
            URL url = null;//请求的URL地址
            HttpURLConnection conn = null;
            String requestHeader = null;//请求头
            byte[] requestBody = null;//请求体
            String responseHeader = null;//响应头
            byte[] responseBody = null;//响应体
            String action = params[0];//http请求的操作类型

            try {
                if (NETWORK_GET.equals(action)) {
                    //1、发送GET请求
                    url = new URL("http://www.baidu.com");
                    conn = (HttpURLConnection) url.openConnection();
                    //2、HttpURLConnection默认就是用GET发送请求，所以下面的setRequestMethod可以省略
                    conn.setRequestMethod("GET");
                    //HttpURLConnection默认也支持从服务端读取结果流，所以下面的setDoInput也可以省略
                    conn.setDoInput(true);
//                    //传递自定义参数
//                    conn.setRequestProperty("action", NETWORK_GET);
                    // 设置字符集
                    conn.setRequestProperty("Charset", "UTF-8");
                    // 设置文件类型
                    conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
                    //禁用网络缓存
                    conn.setUseCaches(false);
                    //获取请求头
                    requestHeader = getReqeustHeader(conn);
                    //获取响应验证200
                    int code =conn.getResponseCode();
                    //在对各种参数配置完成后，通过调用connect方法建立TCP连接，但是并未真正获取数据
                    //conn.connect()方法不必显式调用，当调用conn.getInputStream()方法时内部也会自动调用connect方法
                    conn.connect();
                    //调用getInputStream方法后，服务端才会收到请求，并阻塞式地接收服务端返回的数据
                    InputStream is = conn.getInputStream();
                    //将InputStream转换成byte数组,getBytesByInputStream会关闭输入流
                    responseBody = getBytesByInputStream(is);
                    //获取响应头
                    responseHeader = getResponseHeader(conn);
                } else if (NETWORK_POST.equals(action)) {
                    //用POST发送键值对数据
                    url = new URL("http://www.baidu.com");
                    conn = (HttpURLConnection) url.openConnection();
                    //通过setRequestMethod将conn设置成POST方法
                    conn.setRequestMethod("POST");
                    //调用conn.setDoOutput()方法以显式开启请求体，容许输出
                    conn.setDoOutput(true);
                    //传递自定义参数
                    conn.setRequestProperty("action", NETWORK_POST);
                    //获取请求头
                    requestHeader = getReqeustHeader(conn);
                    //上次参数
//                    OutputStream os = conn.getOutputStream();
//                    requestBody = new String("name=孙群&age=27").getBytes("UTF-8");
//                    os.write(requestBody);    //将请求体写入到conn的输出流中
//                    os.flush();
//                    os.close();
                    // 上传一张图片
//                    FileInputStream file = new FileInputStream(Environment.getExternalStorageDirectory().getPath()
//                            + "/Pictures/Screenshots/Screenshot_2015-12-19-08-40-18.png");
//                    OutputStream os = connection.getOutputStream();
//                    int count = 0;
//                    while((count=file.read()) != -1){
//                        os.write(count);
//                    }
//                    os.flush();
//                    os.close();
                    //判断验证200
                    int code =conn.getResponseCode();
                    //当调用getInputStream方法时才真正将请求体数据上传至服务器
                    InputStream is = conn.getInputStream();
                    //获得响应体的字节数组
                    responseBody = getBytesByInputStream(is);
                    //获得响应头
                    responseHeader = getResponseHeader(conn);
                } else if (NETWORK_POST_XML.equals(action)) {
                    //用POST发送XML数据
                    url = new URL("http://192.168.31.200:8080/HttpServer/MyServlet");
                    conn = (HttpURLConnection) url.openConnection();
                    //通过setRequestMethod将conn设置成POST方法
                    conn.setRequestMethod("POST");
                    //调用conn.setDoOutput()方法以显式开启请求体
                    conn.setDoOutput(true);
                    //用setRequestProperty方法设置一个自定义的请求头:action，由于后端判断
                    conn.setRequestProperty("action", NETWORK_POST_XML);
                    //获取请求头
                    requestHeader = getReqeustHeader(conn);
                    //获取conn的输出流
                    OutputStream os = conn.getOutputStream();
                    //读取assets目录下的person.xml文件，将其字节数组作为请求体
                    requestBody = getBytesFromAssets("Person.xml");
                    //将请求体写入到conn的输出流中
                    os.write(requestBody);
                    //记得调用输出流的flush方法
                    os.flush();
                    //关闭输出流
                    os.close();
                    //当调用getInputStream方法时才真正将请求体数据上传至服务器
                    InputStream is = conn.getInputStream();
                    //获得响应体的字节数组
                    responseBody = getBytesByInputStream(is);
                    //获得响应头
                    responseHeader = getResponseHeader(conn);
                } else if (NETWORK_POST_JSON.equals(action)) {
                    //用POST发送JSON数据
                    url = new URL("http://192.168.31.200:8080/HttpServer/MyServlet");
                    conn = (HttpURLConnection) url.openConnection();
                    //通过setRequestMethod将conn设置成POST方法
                    conn.setRequestMethod("POST");
                    //调用conn.setDoOutput()方法以显式开启请求体
                    conn.setDoOutput(true);
                    //用setRequestProperty方法设置一个自定义的请求头:action，由于后端判断
                    conn.setRequestProperty("action", NETWORK_POST_JSON);
                    //获取请求头
                    requestHeader = getReqeustHeader(conn);
                    //获取conn的输出流
                    OutputStream os = conn.getOutputStream();
                    //读取assets目录下的person.json文件，将其字节数组作为请求体
                    requestBody = getBytesFromAssets("Person.json");
                    //将请求体写入到conn的输出流中
                    os.write(requestBody);
                    //记得调用输出流的flush方法
                    os.flush();
                    //关闭输出流
                    os.close();
                    //当调用getInputStream方法时才真正将请求体数据上传至服务器
                    InputStream is = conn.getInputStream();
                    //获得响应体的字节数组
                    responseBody = getBytesByInputStream(is);
                    //获得响应头
                    responseHeader = getResponseHeader(conn);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //最后将conn断开连接
                if (conn != null) {
                    conn.disconnect();
                }
            }

            result.put("url", url.toString());
            result.put("action", action);
            result.put("requestHeader", requestHeader);
            result.put("requestBody", requestBody);
            result.put("responseHeader", responseHeader);
            result.put("responseBody", responseBody);
            return result;
        }

        @Override
        protected void onPostExecute(Map<String, Object> result) {
            super.onPostExecute(result);
            String url = (String)result.get("url");//请求的URL地址
            String action = (String) result.get("action");//http请求的操作类型
            String requestHeader = (String) result.get("requestHeader");//请求头
            byte[] requestBody = (byte[]) result.get("requestBody");//请求体
            String responseHeader = (String) result.get("responseHeader");//响应头
            byte[] responseBody = (byte[]) result.get("responseBody");//响应体

            //更新tvUrl，显示Url
            tvUrl.setText(url);

            //更新tvRequestHeader，显示请求头
            if (requestHeader != null) {
                tvRequestHeader.setText(requestHeader);
            }

            //更新tvRequestBody，显示请求体
            if(requestBody != null){
                try{
                    String request = new String(requestBody, "UTF-8");
                    tvRequestBody.setText(request);
                }catch (UnsupportedEncodingException e){
                    e.printStackTrace();
                }
            }

            //更新tvResponseHeader，显示响应头
            if (responseHeader != null) {
                tvResponseHeader.setText(responseHeader);
            }

            //更新tvResponseBody，显示响应体
            if (NETWORK_GET.equals(action)) {
                String response = getStringByBytes(responseBody);
                tvResponseBody.setText(response);
            } else if (NETWORK_POST.equals(action)) {
                String response = getStringByBytes(responseBody);
                tvResponseBody.setText(response);
            } else if (NETWORK_POST_XML.equals(action)) {
                //将表示xml的字节数组进行解析
                String response = parseXmlResultByBytes(responseBody);
                tvResponseBody.setText(response);
            } else if (NETWORK_POST_JSON.equals(action)) {
                //将表示json的字节数组进行解析
                String response = parseJsonResultByBytes(responseBody);
                tvResponseBody.setText(response);
            }
        }

        //读取请求头
        private String getReqeustHeader(HttpURLConnection conn) {
            Map<String, List<String>> requestHeaderMap = conn.getRequestProperties();
            Iterator<String> requestHeaderIterator = requestHeaderMap.keySet().iterator();
            StringBuilder sbRequestHeader = new StringBuilder();
            while (requestHeaderIterator.hasNext()) {
                String requestHeaderKey = requestHeaderIterator.next();
                String requestHeaderValue = conn.getRequestProperty(requestHeaderKey);
                sbRequestHeader.append(requestHeaderKey);
                sbRequestHeader.append(":");
                sbRequestHeader.append(requestHeaderValue);
                sbRequestHeader.append("\n");
            }
            return sbRequestHeader.toString();
        }

        //读取响应头
        private String getResponseHeader(HttpURLConnection conn) {
            Map<String, List<String>> responseHeaderMap = conn.getHeaderFields();
            int size = responseHeaderMap.size();
            StringBuilder sbResponseHeader = new StringBuilder();
            for(int i = 0; i < size; i++){
                String responseHeaderKey = conn.getHeaderFieldKey(i);
                String responseHeaderValue = conn.getHeaderField(i);
                sbResponseHeader.append(responseHeaderKey);
                sbResponseHeader.append(":");
                sbResponseHeader.append(responseHeaderValue);
                sbResponseHeader.append("\n");
            }
            return sbResponseHeader.toString();
        }

        //根据字节数组构建UTF-8字符串
        private String getStringByBytes(byte[] bytes) {
            String str = "";
            try {
                str = new String(bytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return str;
        }

        //从InputStream中读取数据，转换成byte数组，最后关闭InputStream
        private byte[] getBytesByInputStream(InputStream is) {
            byte[] bytes = null;
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(baos);
            byte[] buffer = new byte[1024 * 8];
            int length = 0;
            try {
                while ((length = bis.read(buffer)) > 0) {
                    bos.write(buffer, 0, length);
                }
                bos.flush();
                bytes = baos.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return bytes;
        }

        //根据文件名，从asserts目录中读取文件的字节数组
        private byte[] getBytesFromAssets(String fileName){
            byte[] bytes = null;
            AssetManager assetManager = getAssets();
            InputStream is = null;
            try{
                is = assetManager.open(fileName);
                bytes = getBytesByInputStream(is);
            }catch (IOException e){
                e.printStackTrace();
            }
            return bytes;
        }

        //将表示xml的字节数组进行解析
        private String parseXmlResultByBytes(byte[] bytes) {
            InputStream is = new ByteArrayInputStream(bytes);
            StringBuilder sb = new StringBuilder();
            List<Person.PersonsBean> persons = XmlParser.parse(is);
            for (Person.PersonsBean person : persons) {
                sb.append(person.toString()).append("\n");
            }
            return sb.toString();
        }

        //将表示json的字节数组进行解析
        private String parseJsonResultByBytes(byte[] bytes){
            String jsonString = getStringByBytes(bytes);
            List<Person.PersonsBean> persons = JsonParser.parse(jsonString);
            StringBuilder sb = new StringBuilder();
            for (Person.PersonsBean person : persons) {
                sb.append(person.toString()).append("\n");
            }
            return sb.toString();
        }

    }
}
