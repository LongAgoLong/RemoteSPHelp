package com.leo.client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.leo.sp.resolver.SpResolver;

public class MainActivity extends AppCompatActivity {
    private TextView mResultTv;
    private Button mInsertBtn;
    private Button mQueryBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultTv = findViewById(R.id.resultTv);
        mInsertBtn = findViewById(R.id.insertBtn);
        mQueryBtn = findViewById(R.id.queryBtn);
        mQueryBtn.setOnClickListener(view -> {
            String s = "获取数据结果：\n" +
                    "test1 : " + SpResolver.getInstance().getString("test1", "0") + "\n" +
                    "test2 : " + SpResolver.getInstance().getBoolean("test2", false) + "\n" +
                    "test3 : " + SpResolver.getInstance().getInt("test3", 0) + "\n" +
                    "test4 : " + SpResolver.getInstance().getFloat("test4", 0) + "\n" +
                    "test5 : " + SpResolver.getInstance().getLong("test5", 0) + "\n" +
                    "test6 : " + SpResolver.getInstance().getString("test6", "没有数据") + "\n";
            mResultTv.append(s);
        });
        mInsertBtn.setOnClickListener(v -> SpResolver.getInstance().save("test6", "随手生成的数据" + System.currentTimeMillis()));

    }
}
