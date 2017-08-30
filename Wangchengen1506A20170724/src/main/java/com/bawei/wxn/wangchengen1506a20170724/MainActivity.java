package com.bawei.wxn.wangchengen1506a20170724;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bawei.wxn.wangchengen1506a20170724.adapeter.MyBase;
import com.bawei.wxn.wangchengen1506a20170724.bean.DataInfo;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.XListView;
/**
 * Created by wxn on 2017/7/24.
 *
 * 主界面
 */
public class MainActivity extends AppCompatActivity {

    private XListView xlv;
    private List<DataInfo.ListBean> list = new ArrayList<>();


    private MyBase base;

    private int mtype = 5;

    private boolean isRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xlv = (XListView) findViewById(R.id.xlistview);

        xlv.setPullLoadEnable(true);


//      XListview 上拉 下拉 刷新数据
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {

                isRefresh = false;
                mtype = mtype-1;

                if(mtype == 1){
                    mtype = 5;
                }

                getData("http://qhb.2dyt.com/Bwei/news",String.valueOf(mtype),"ad1AK");


                xlv.stopRefresh();
            }

            @Override
            public void onLoadMore() {

                isRefresh = true;

                mtype = mtype-1;

                if(mtype == 1){
                    mtype = 5;
                }
                getData("http://qhb.2dyt.com/Bwei/news",String.valueOf(mtype),"ad1AK");

                xlv.stopLoadMore();
            }
        });



        getData("http://qhb.2dyt.com/Bwei/news",String.valueOf(mtype),"ad1AK");
    }

    private void getData(final String path, String type, String postkey) {


        new AsyncTask<String, Void, String>() {


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if(s != null){

                    Gson gson = new Gson();

                    DataInfo dataInfo = gson.fromJson(s,DataInfo.class);

                    list = dataInfo.getList();



                    if(isRefresh){

                        base.addData(list);
                        base.notifyDataSetChanged();

                    }else{
                        base = new MyBase(list,MainActivity.this);

                        xlv.setAdapter(base);
                    }


                }


            }

            @Override
            protected String doInBackground(String... params) {

                String url = params[0];
                String t = params[1];
                String p = params[2];

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(url)
                            .openConnection();

                    connection.setRequestMethod("POST");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);

                    OutputStream os = connection.getOutputStream();
                    os.write(("type="+t+"&postkey="+p).getBytes());
                    os.flush();


                    int code = connection.getResponseCode();
                    if(code == HttpURLConnection.HTTP_OK){

                        InputStream is = connection.getInputStream();

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        int len = 0;
                        byte[] b = new byte[1024];
                        while ((len = is.read(b)) != -1){

                            bos.write(b,0,len);
                        }

                        String json = bos.toString();

                        bos.close();
                        is.close();
                        os.close();

                        return json;

                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }


                return null;
            }
        }.execute(path,type,postkey);
    }
}
