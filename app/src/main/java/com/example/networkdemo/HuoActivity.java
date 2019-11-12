package com.example.networkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HuoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button tian,jin,xin;
    private TextView di;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huo);

        tian = findViewById(R.id.tian);
        jin = findViewById(R.id.jin);
        xin = findViewById(R.id.xin);

        tian.setOnClickListener(this);
        jin.setOnClickListener(this);
        xin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tian:
                break;
            case R.id.jin:
                break;
            case R.id.xin:
                break;
        }
    }
}
