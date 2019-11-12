package com.example.networkdemo;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.View;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkActivity extends AppCompatActivity implements View.OnClickListener {
    //请求的URL
    private static final String IP_BASE_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=222.95.239.158";
    private static final String IP_URL = IP_BASE_URL + "?ip=192.168.43.164";
    public static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
    private static final Object UPLOAD_FILE_URL = null;

    private Button get1,post1,shang1,xia1;
    private TextView tvResult;
    private ImageView Imview;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok);

        get1 = findViewById(R.id.get1);
        post1 = findViewById(R.id.post1);
        shang1 = findViewById(R.id.shang1);
        xia1 = findViewById(R.id.xia1);
        tvResult = findViewById(R.id.gu);
        Imview= findViewById(R.id.view);
        scrollView=findViewById(R.id.scroll);

//        view = findViewById(R.id.view);

        get1.setOnClickListener(this);
        post1.setOnClickListener(this);
        shang1.setOnClickListener(this);
        xia1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String path = getFilesDir().getAbsolutePath();
        switch (v.getId()){
            case R.id.get1:
                scrollView.setVisibility(View.VISIBLE);
                Imview.setVisibility(View.GONE);
                get(IP_URL);
                break;
            case R.id.post1:
                scrollView.setVisibility(View.VISIBLE);
                Imview.setVisibility(View.GONE);
                Map<String,String>params = new HashMap<>();
                params.put("ip","192.168.43.164");
                post(IP_URL,params);
                break;
            case R.id.shang1:
                scrollView.setVisibility(View.VISIBLE);
                Imview.setVisibility(View.GONE);
                final String fileName = path + File.separator+"readme.md";
                uploadFile(UPLOAD_FILE_URL,fileName);
                break;
            case R.id.xia1:
                break;
        }
    }

    private void uploadFile(Object uploadFileUrl, String fileName) {
    }

    private void post(String ipUrl, Map<String, String> params) {
        RequestBody body = setRequestBody(params);
        Request request = new Request.Builder().url(ipUrl).post(body)
                .header("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36"
                        + "(KHTML, like Gecko) Chrome/76.0.3809.132 Mobile Safari/537.36")
                .addHeader("Accept", "application/json")
                .build();
    }

    private RequestBody setRequestBody(Map<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()){
            builder.add(key,params.get(key));
        }
        return builder.build();
    }

    private void get(String ipUrl) {
        //1.构造Request
        Request request = new Request.Builder().url(ipUrl)
                .header("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36"
                        + "(KHTML, like Gecko) Chrome/76.0.3809.132 Mobile Safari/537.36")
                .addHeader("Accept", "application/json")
                .get()
                .method("GET", null)
                .build();
        //2.发送请求，并处理回调
        OkHttpClient client = HttpsUtil.handleSSLHandshakeByOkHttp();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();

                    final Ip ip = JSON.parseObject(json, Ip.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (ip.getCode() != 0) {
                                tvResult.setText("未获得数据");
                            } else {
                                IpData data = ip.getData();
                                tvResult.setText(data.getIp() + "," + data.getArea());
                            }

                        }
                    });
                }
            }
        });
    }
}