package com.bawei.wxn.wangchengen1506a20170728;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by wxn on 2017/7/28.
 *
 * 用于初始化ImageLoader；
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        imageloader 缓存路径到sdcard/baweikaoshi
        File file = new File(Environment.getExternalStorageDirectory() + "/baweikaoshi");

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(2*1024*1024)
                .memoryCacheExtraOptions(100,100)
                .diskCache(new UnlimitedDiskCache(file))
                .diskCacheSize(50*1024*1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .build();


        ImageLoader.getInstance().init(configuration);


    }
}
