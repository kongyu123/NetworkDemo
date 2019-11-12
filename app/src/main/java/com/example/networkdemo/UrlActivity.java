package com.example.networkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UrlActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String IP_BASE_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=112.2.253.218";
    private static final String IP_URL = IP_BASE_URL + "?ip=192.168.43.164";

    private Button get,post,shang,xia;
    private TextView jie;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);

        get = findViewById(R.id.get);
        post = findViewById(R.id.post);
        shang = findViewById(R.id.shang);
        xia = findViewById(R.id.xia);
        jie = findViewById(R.id.jie);
        image = findViewById(R.id.image);

        get.setOnClickListener(this);
        post.setOnClickListener(this);
        shang.setOnClickListener(this);
        xia.setOnClickListener(this);

        Glide.with(this)
                .load("http://cdn.duitang.com/uploads/item/201601/15/20160115155749_BQ3Vk.jpeg")
                .into(image);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String result = NetworkUrl.get(IP_URL);
                        Log.d("MainActivity",result);
                        if (result != null){
                            jie.post(new Runnable() {
                                @Override
                                public void run() {
                                    jie.setText(result);
                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   jie.setText("数据为空");
                                }
                            });
                        }
                    }
                }).start();
                break;
            case R.id.post:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<NameValuePair> params = new ArrayList<>();
                        params.add(new BasicNameValuePair("ip","112.2.253.218"));
                        final String result = NetworkUrl.post(IP_BASE_URL,params);
                        if (result != null) {
                            Log.d("MainActivity", result);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    jie.setText(result);
                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    jie.setText("请求失败，没有获得数据");
                                }
                            });
                        }
                    }
                }).start();
                break;
            case R.id.shang:
                break;
            case R.id.xia:
                break;
        }
    }
}
