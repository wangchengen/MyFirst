package com.bawei.wxn.wangchengen1506a20170728;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bawei.wxn.wangchengen1506a20170728.bean.DataInfo;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.w3c.dom.ls.LSInput;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.maxwin.view.XListView;

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener {

    private XListView xListView;
    private boolean isRefresh = false;


    private View bannerView;

    private List<DataInfo.ListBean> list;
    private List<String> imgUrlList;

    private MyAdapter adapter;


    private ViewPager viewPager;
    private ImageView dot1, dot2, dot3;

    private int newbanner = 0;
    private int oldbanner = 0;

    private Timer timer;
    private int bannerCount = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);


            viewPager.setCurrentItem(bannerCount);


        }
    };

    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .showImageOnLoading(R.mipmap.ic_launcher)
            .build();
    private Receiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();

//        注册广播
        setRecriver();


        xListView.addHeaderView(bannerView);

        xListView.setXListViewListener(this);


        setData("http://qhb.2dyt.com/Bwei/news", "1", "5", "ff1d1AK");

        startBanner();

    }

    private void setRecriver() {

        receiver = new Receiver();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        registerReceiver(receiver, filter);

    }

    private void startBanner() {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                bannerCount++;

                handler.sendEmptyMessage(0);


            }
        }, 2000, 2000);

    }

    private void initView() {

        xListView = (XListView) findViewById(R.id.xListView);

        bannerView = View.inflate(this, R.layout.banner_item, null);

        viewPager = (ViewPager) bannerView.findViewById(R.id.viewPager);
        dot1 = (ImageView) bannerView.findViewById(R.id.dot1);
        dot2 = (ImageView) bannerView.findViewById(R.id.dot2);
        dot3 = (ImageView) bannerView.findViewById(R.id.dot3);


        final List<ImageView> viewList = new ArrayList<>();
        viewList.add(dot1);
        viewList.add(dot2);
        viewList.add(dot3);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                viewList.get(newbanner).setImageResource(R.drawable.dot_y);
                viewList.get(oldbanner).setImageResource(R.drawable.dot_n);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                List<String> str = null;
                try {
                    str.get(0);
                } catch (Exception e) {
                    e.printStackTrace();
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setTitle("提示");
                    builder.setMessage("应用程序无响应");

                    builder.setPositiveButton("确定", null);
                    builder.setNegativeButton("取消", null);

                    builder.create().show();
                }


            }
        });
    }


    private void setData(String path, final String page, String type, String postkey) {


        new AsyncTask<String, Void, String>() {


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                if (s != null) {

                    Gson gson = new Gson();


                    DataInfo dataInfo = gson.fromJson(s, DataInfo.class);

                    list = dataInfo.getList();
                    imgUrlList = dataInfo.getListViewPager();
                    final int imgUrlListCount = imgUrlList.size();




                    if (!isRefresh) {
                        viewPager.setAdapter(new PagerAdapter() {
                            @Override
                            public int getCount() {
                                return Integer.MAX_VALUE;
                            }

                            @Override
                            public boolean isViewFromObject(View view, Object object) {
                                return view == object;
                            }

                            @Override
                            public void destroyItem(ViewGroup container, int position, Object object) {

                                container.removeView((View) object);
                            }

                            @Override
                            public Object instantiateItem(ViewGroup container, int position) {

                                ImageView imageView = new ImageView(MainActivity.this);

                                ImageLoader.getInstance().displayImage(
                                        imgUrlList.get(position % imgUrlListCount),
                                        imageView, options);

                                newbanner = position % imgUrlListCount;

                                if (newbanner == 0) {
                                    oldbanner = imgUrlListCount - 1;
                                } else {
                                    oldbanner = newbanner - 1;
                                }

                                container.addView(imageView);

                                return imageView;
                            }
                        });
                        adapter = new MyAdapter(list, MainActivity.this);
                        xListView.setAdapter(adapter);
                    } else {
                        adapter.RefreshData(list);
                        adapter.notifyDataSetChanged();
                    }


                }
            }

            @Override
            protected String doInBackground(String... params) {

                String u = params[0];
                String p = params[1];
                String t = params[2];
                String key = params[3];

                try {
                    URL url = new URL(u);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setConnectTimeout(5000);
                    connection.setReadTimeout(5000);
                    connection.setRequestMethod("POST");

                    OutputStream os = connection.getOutputStream();
                    os.write(("page=" + p + "&type=" + t + "&postkey=" + key).getBytes());
                    os.flush();

                    int code = connection.getResponseCode();
                    if (code == HttpURLConnection.HTTP_OK) {

                        InputStream is = connection.getInputStream();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        int len = 0;
                        byte[] b = new byte[1024];

                        while ((len = is.read(b)) != -1) {

                            bos.write(b, 0, len);

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
        }.execute(path, page, type, postkey);


    }


    //    7)	listview 上拉刷新数据，加载到当前集合中显示
    @Override
    public void onRefresh() {


        isRefresh = true;
        setData("http://qhb.2dyt.com/Bwei/news", "1", "5", "ff1d1AK");



        xListView.stopRefresh();

    }

    @Override
    public void onLoadMore() {


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//注销广播
        unregisterReceiver(receiver);
    }
}
