package com.leo.remotesphelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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
                spResolver = new SpResolver(MainActivity.this, getMetaValue("authority"));
                spResolver.setString("test1", "这是测试数据1");
                spResolver.setBoolean("test2", true);
                spResolver.setInt("test3", 1000);
                spResolver.setFloat("test4", 100.456f);
                spResolver.setLong("test5", System.currentTimeMillis());
                mResultTv.append("保存数据完成");
            }
        });
    }

    private String getMetaValue(String metaKey) {
        String metaValue = null;
        if (metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo appInfo = this.getPackageManager().getApplicationInfo(
                    this.getPackageName(), PackageManager.GET_META_DATA);
            metaValue = appInfo.metaData.getString(metaKey);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return metaValue;
    }
}
