package com.bawei.wxn.wangchengen20170717;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by 王承恩 on 2017/7/17.
 *
 * 初始化imageloader
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        File file = new File(Environment.getExternalStorageDirectory()+"/mycard");

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(400,400)
                .memoryCacheSize(2*1024*1024)
                .diskCacheSize(50*1024*1024)
                .diskCacheFileCount(20)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCache(new UnlimitedDiskCache(file))
                .build();


        ImageLoader.getInstance().init(configuration);


    }
}
