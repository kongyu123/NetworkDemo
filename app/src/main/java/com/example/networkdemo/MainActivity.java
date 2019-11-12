package com.example.networkdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView url;
    private TextView ok;
    private TextView huo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = findViewById(R.id.url);
        ok = findViewById(R.id.ok);
        huo = findViewById(R.id.huo);

        url.setOnClickListener(this);
        ok.setOnClickListener(this);
        huo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.url:
                Intent intent = new Intent(MainActivity.this,UrlActivity.class);
                startActivity(intent);
                break;
            case R.id.ok:
                Intent intent1 = new Intent(MainActivity.this,OkActivity.class);
                startActivity(intent1);
                break;
            case R.id.huo:
                Intent intent2 = new Intent(MainActivity.this,HuoActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
