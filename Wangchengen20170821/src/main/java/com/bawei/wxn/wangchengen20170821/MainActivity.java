package com.bawei.wxn.wangchengen20170821;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    private String[] title = {"漫画","其他1","其他2","其他3","其他4"
            ,"其他5","其他6","其他7","其他8","其他9","其他10"};

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
//1)	实现TabLayout＋fragment 滑动
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                Fragment fragment;

                if(position == 0){
                    fragment = new MyFragment();
                }else {
                    fragment = new OtherFragment();

                    Bundle bundle = new Bundle();
                    bundle.putString("title",title[position]);
                    fragment.setArguments(bundle);

                }

                return fragment;
            }

            @Override
            public int getCount() {
                return title.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return title[position];
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }
}
