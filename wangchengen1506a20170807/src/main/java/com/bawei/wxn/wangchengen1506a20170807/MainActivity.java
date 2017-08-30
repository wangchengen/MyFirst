package com.bawei.wxn.wangchengen1506a20170807;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 王承恩
 *
 * 20170807
 *
 *
 *
 *
 */


public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private TabLayout tabLayout;
    private ImageView icon;
    private View left;

    private SharedPreferences preferences;

    private String[] titles = {"推荐","热点","北京","军事","科技",
            "推荐","热点","北京","军事","科技"};

    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .displayer(new CircleBitmapDisplayer())
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        left = View.inflate(MainActivity.this,R.layout.left_fragment,null);
        icon = (ImageView) left.findViewById(R.id.imageView);

        preferences = getSharedPreferences("UrlData",MODE_PRIVATE);
        String url = preferences.getString("iconurl","");
        if(url != ""){
            ImageLoader.getInstance().displayImage(url,icon,options);
        }

        pager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment = new TabFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title",titles[position]);
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
        
        setSlidingMenu();
    }

    private void setSlidingMenu() {

        SlidingMenu leftMenu = new SlidingMenu(this);
        SlidingMenu rightMenu = new SlidingMenu(this);

        leftMenu.setMode(SlidingMenu.LEFT);
        rightMenu.setMode(SlidingMenu.RIGHT);



        leftMenu.setBehindOffset(200);
        rightMenu.setBehindOffset(200);
        leftMenu.setFadeDegree(0.5f);
        rightMenu.setFadeDegree(0.5f);

        leftMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        rightMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);

        leftMenu.setMenu(left);
        rightMenu.setMenu(R.layout.right_fragment);
    }


    public void LoginQQ(View view){


        UMShareAPI.get(this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ,
                new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();

                        String iconurl = map.get("iconurl");
                        SharedPreferences.Editor editor =  preferences.edit();
                        editor.putString("iconurl",iconurl);
                        editor.commit();



                        ImageLoader.getInstance().displayImage(iconurl,icon,options);
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        Toast.makeText(MainActivity.this,"失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        Toast.makeText(MainActivity.this,"取消了",Toast.LENGTH_SHORT).show();
                    }
                });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
