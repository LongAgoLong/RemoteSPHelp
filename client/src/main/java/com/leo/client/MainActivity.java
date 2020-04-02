package com.leo.client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.leo.sp.resolver.SpResolver;

public class MainActivity extends AppCompatActivity {
    private TextView mResultTv;
    private Button mEnsureBtn;

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
                        "test1 : " + SpResolver.getInstance().getString("test1", "0") + "\n" +
                        "test2 : " + SpResolver.getInstance().getBoolean("test2", false) + "\n" +
                        "test3 : " + SpResolver.getInstance().getInt("test3", 0) + "\n" +
                        "test4 : " + SpResolver.getInstance().getFloat("test4", 0) + "\n" +
                        "test5 : " + SpResolver.getInstance().getLong("test5", 0) + "\n";
                mResultTv.append(s);
            }
        });


    }
}
