package com.leo.remotesphelp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.leo.sp.provider.SpResolver;

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
                SpResolver.getInstance().save("test1", "这是测试数据1");
                SpResolver.getInstance().save("test2", true);
                SpResolver.getInstance().save("test3", 1000);
                SpResolver.getInstance().save("test4", 100.456f);
                SpResolver.getInstance().save("test5", System.currentTimeMillis());
                mResultTv.append("保存数据完成");
            }
        });
    }
}
