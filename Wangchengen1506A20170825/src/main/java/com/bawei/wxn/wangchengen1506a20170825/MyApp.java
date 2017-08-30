package com.bawei.wxn.wangchengen1506a20170825;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

import java.io.File;

/**
 * Created by wxn on 2017/8/25.
 */

public class MyApp extends Application{


    @Override
    public void onCreate() {
        super.onCreate();

//        图片缓存目录
        File file = new File(Environment.getExternalStorageDirectory() + "/yuekao");

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .diskCache(new UnlimitedDiskCache(file))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(50*1024*1024)
                .build();

        ImageLoader.getInstance().init(configuration);

        x.Ext.init(this);
    }
}
