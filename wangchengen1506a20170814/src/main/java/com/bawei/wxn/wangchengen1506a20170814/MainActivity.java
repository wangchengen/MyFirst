package com.bawei.wxn.wangchengen1506a20170814;

import android.content.res.Configuration;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.List;

import me.maxwin.view.XListView;

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener {


    @ViewInject(R.id.xlistview)
    private XListView xListView;
    @ViewInject(R.id.img)
    private ImageView imageView;


    private SlidingMenu menu;

    private List<DataInfo.DataBean> list;
    private int urlCount = 1;

    private boolean isLoadMore = false;
    private MyAdapter myAdapter;
    private File file;
    private float dirSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        x.view().inject(this);
//      得到缓存大小
        file = new File(Environment.getExternalStorageDirectory() +"/m");
        dirSize = getDirSize(file);
        Toast.makeText(MainActivity.this,"缓存图片大小为： " + dirSize,Toast.LENGTH_SHORT).show();

//        设置SlidingMenu
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setBehindOffset(200);
        menu.setMenu(R.layout.slidingmenu_item);
        menu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
//        menu.showMenu();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                menu.toggle();
            }
        });


        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);
        
        
//        设置数据
        setData();



    }

    private float getDirSize(File file) {

        if (file.exists()) {

            if (file.isDirectory()) {
                File[] children = file.listFiles();
                float size = 0;
                for (File f : children)
                    size += getDirSize(f);
                return size;
            } else {//如果是文件则直接返回其大小,以“兆”为单位
                float  size = (float) ((double) file.length() / 1024 / 1024);
                return size;
            }
        } else {
            System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
            return 0;
        }

    }

    private void setData() {

        RequestParams params = new RequestParams("http://api.expoon.com/AppNews/getNewsList/" +
                "type/1/p/"+urlCount);


        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                if(result != null){
                    Gson gson = new Gson();
                    DataInfo info = gson.fromJson(result,DataInfo.class);


                    list = info.getData();

                    if(!isLoadMore){
                        myAdapter = new MyAdapter(list,MainActivity.this);
                        xListView.setAdapter(myAdapter);
                    }else {
                        myAdapter.addData(list);
                        myAdapter.notifyDataSetChanged();
                    }
                    dirSize = getDirSize(file);
                    Toast.makeText(MainActivity.this,"缓存图片大小为： " + dirSize,Toast.LENGTH_SHORT).show();



                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onRefresh() {

        isLoadMore = false;
        setData();

        xListView.stopRefresh();

    }

    @Override
    public void onLoadMore() {

        urlCount++;
        isLoadMore = true;

        setData();

        xListView.stopLoadMore();

    }

//    切换日夜间模式
    public void SwitchTheme(View view){

        int type;

        type = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if(type == Configuration.UI_MODE_NIGHT_NO){

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }


        recreate();




    }
}
