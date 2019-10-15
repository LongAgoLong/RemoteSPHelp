package com.leo.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.leo.sp.resolver.SpResolver;

public class MainActivity extends AppCompatActivity {
    private TextView mResultTv;
    private Button mEnsureBtn;

    private SpResolver spResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultTv = findViewById(R.id.resultTv);
        mEnsureBtn = findViewById(R.id.ensureBtn);
        mEnsureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = "获取数据结果：\n" +
                        "test1 : " + spResolver.getString("test1") + "\n" +
                        "test2 : " + spResolver.getBoolean("test2") + "\n" +
                        "test3 : " + spResolver.getInt("test3") + "\n" +
                        "test4 : " + spResolver.getFloat("test4") + "\n" +
                        "test5 : " + spResolver.getLong("test5") + "\n";
                mResultTv.append(s);
            }
        });

        spResolver = new SpResolver(this, "com.android.sp.provider");

    }
}
