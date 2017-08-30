package com.bawei.wxn.wangchengen20170717;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bawei.wxn.wangchengen20170717.bean.DataInfo;
import com.bawei.wxn.wangchengen20170717.util.MyBase;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by 王承恩 on 2017/7/17.
 *
 * 作为主界面
 */
public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<DataInfo.ListBean> list;


    private MyBase base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       listView = (ListView) findViewById(R.id.listview);


        setData("http://qhb.2dyt.com/Bwei/news?page=1&type=7&postkey=ad1AK");


//        listView点击跳转
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this,NextActivity.class);

                String title = list.get(position).getTitle();
                String date = list.get(position).getDate();
                String imgUrl = list.get(position).getPic();

                intent.putExtra("title",title);
                intent.putExtra("date",date);
                intent.putExtra("imgUrl",imgUrl);

                startActivity(intent);

            }
        });


    }

    private void setData(String path) {

        new AsyncTask<String,Void,String>(){

            @Override
            protected String doInBackground(String... params) {

                String s = params[0];

                try {
                    URL url = new URL(s);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);



                    int code = connection.getResponseCode();

                    if(code == HttpURLConnection.HTTP_OK){

                        InputStream is = connection.getInputStream();

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        int len = 0;
                        byte[] buffer = new byte[1024];

                        while ((len = is.read(buffer)) != -1){
                            bos.write(buffer,0,len);
                        }

                        String json = bos.toString();

                        is.close();
                        bos.close();

                        return json;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                Gson gson = new Gson();
                DataInfo dataInfo = gson.fromJson(s,DataInfo.class);

                list = dataInfo.getList();

                base = new MyBase(list,MainActivity.this);
                listView.setAdapter(base);


            }
        }.execute(path);


    }
}
