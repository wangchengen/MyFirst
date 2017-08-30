package com.bawei.wxn.wangchengen1506a20170825;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bawei.wxn.wangchengen1506a20170825.Fragment.MyFragment;

import org.xutils.x;

public class MainActivity extends AppCompatActivity {

    private String[] titles = {"数据", "其他1", "其他2", "其他3", "其他4"
            , "其他5", "其他6", "其他7", "其他8", "其他9"};

    private TabLayout tabLayout;
    private ViewPager pager;
    private MyConnectivityReciver reciver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.TabLayout);
        pager = (ViewPager) findViewById(R.id.ViewPager);

        //注册广播
        IntentFilter filter = new IntentFilter();

        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        reciver = new MyConnectivityReciver();

        registerReceiver(reciver, filter);


        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                Fragment fragment = new MyFragment();

                Bundle bundle = new Bundle();
                bundle.putString("title", titles[position]);
                fragment.setArguments(bundle);

                return fragment;
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });


        tabLayout.setupWithViewPager(pager);

    }

    //    跳转到清除缓存界面
    public void startCache(View view) {

        Intent intent = new Intent(MainActivity.this,CacheActivity.class);

        startActivity(intent);


    }


    //     网络监听广播
    class MyConnectivityReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

                ConnectivityManager manager =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo info = manager.getActiveNetworkInfo();

                if (info != null && info.isAvailable()) {
                    getSharedPreferences("NetWork", MODE_PRIVATE)
                            .edit()
                            .putBoolean("isConnect", true)
                            .commit();

                } else {

                    getSharedPreferences("NetWork", MODE_PRIVATE)
                            .edit()
                            .putBoolean("isConnect", false)
                            .commit();
                    Toast.makeText(MainActivity.this, "无网络", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(reciver);
    }
}
