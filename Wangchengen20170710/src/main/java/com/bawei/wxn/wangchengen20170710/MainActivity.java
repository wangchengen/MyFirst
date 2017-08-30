package com.bawei.wxn.wangchengen20170710;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.bawei.wxn.wangchengen20170710.bean.DataInfo;
import com.bawei.wxn.wangchengen20170710.util.NetWork;
import com.bawei.wxn.wangchengen20170710.util.SteamTools;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王承恩 on 2017/7/10.
 * <p>
 * 用于做z主要显示数据界面
 */

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<DataInfo.ResultBean.DataBean> list;

    private MyBase base;

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == 0){
                if(list != null){
                    base = new MyBase(list,MainActivity.this);
                    listView.setAdapter(base);

                }else{
                    Toast.makeText(MainActivity.this, "没有查询到数据", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);

        NetWork net = new NetWork();

        boolean isconnect = net.isNetworkConnected(MainActivity.this);




        if(!isconnect){

            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);

            dialog.setTitle("提示");
            dialog.setMessage("当前网络不可用，点击确定，开启网络");

            dialog.setNegativeButton("取消",null);
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //跳转到整体网络设置
//                    startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));

                    //跳转到WIFI网络设置
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    //跳转到3G流量网络设置
//                    startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                }
            });

            dialog.create().show();

        } else{
            new Thread(){
                @Override
                public void run() {
                    super.run();
                    getData();


                    Message message = handler.obtainMessage();


                    handler.sendEmptyMessage(0);


                }
            }.start();
        }












    }


    private void getData() {

        try {
            URL url = new URL("http://apis.juhe.cn/cook/query");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);

            OutputStream os = connection.getOutputStream();
            os.write("key=9a3b48705b2c121641e6ed769ac1e677&menu=红烧肉&rn=10&pn=3".getBytes());
            os.flush();

            int code = connection.getResponseCode();

            if(code == HttpURLConnection.HTTP_OK){

                InputStream is = connection.getInputStream();

                String json = new SteamTools().dataToJson(is);

                System.out.println(json);

                Gson gson = new Gson();

                DataInfo dataInfo = gson.fromJson(json,DataInfo.class);

                list = dataInfo.getResult().getData();




            }else{
                list = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
