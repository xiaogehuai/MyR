package com.bwie.myr;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.bwie.myr.adapter.MAdapter;
import com.bwie.myr.bean.CommunityBean;
import com.bwie.myr.tools.Url;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {

    RecyclerView mRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //从网上初始化数据
        initView();
        requestimg();

    }



        private void requestimg() {
        String url ="http://open.qyer.com/qyer/bbs/entry?client_id=qyer_android&client_secret=9fcaae8aefc4f9ac4915";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Gson gson = new Gson();
                final CommunityBean communityBean = gson.fromJson(json, CommunityBean.class);
               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //设置适配器
                        mRV.setAdapter(new MAdapter(MainActivity.this,communityBean));
                    }


                });

            }
        });
    }
    /**
     * 初始化视图
     */
    private void initView() {
        mRV=(RecyclerView) findViewById(R.id.rv_basefragment);
        mRV.setLayoutManager(new LinearLayoutManager(this));

    }

    }
