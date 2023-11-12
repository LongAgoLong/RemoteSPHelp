package com.leo.client

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.leo.sp.resolver.SpContants
import com.leo.sp.resolver.SpResolver

class MainActivity : AppCompatActivity() {
    private lateinit var mResultTv: TextView
    private lateinit var mInsertBtn: Button
    private lateinit var mQueryBtn: Button
    private lateinit var mRemoveBtn: Button
    private lateinit var mClearBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mResultTv = findViewById(R.id.resultTv)
        mInsertBtn = findViewById(R.id.insertBtn)
        mQueryBtn = findViewById(R.id.queryBtn)
        mRemoveBtn = findViewById(R.id.removeBtn)
        mClearBtn = findViewById(R.id.clearBtn)

        mQueryBtn.setOnClickListener {
            val s = """
                获取数据结果：
                test1 : ${SpResolver.getInstance().getString("test1", "0")}
                test2 : ${SpResolver.getInstance().getBoolean("test2", false)}
                test3 : ${SpResolver.getInstance().getInt("test3", 0)}
                test4 : ${SpResolver.getInstance().getFloat("test4", 0f)}
                test5 : ${SpResolver.getInstance().getLong("test5", 0)}
                test6 : ${SpResolver.getInstance().getString("test6", "没有数据")}
            """
            mResultTv.append(s)
        }
        mInsertBtn.setOnClickListener {
            SpResolver.getInstance().putString("test6", "随手生成的数据" + System.currentTimeMillis())
        }
        mRemoveBtn.setOnClickListener {
            SpResolver.getInstance().remove("test6", SpContants.TYPE_STRING)
        }
        mClearBtn.setOnClickListener {
            SpResolver.getInstance().clear()
        }
    }
}
