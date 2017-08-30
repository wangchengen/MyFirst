package com.bawei.wxn.wangchengen1506a20170814;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.xutils.x;

import java.io.File;

/**
 * Created by wxn on 2017/8/14.
 */

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        File file = new File(Environment.getExternalStorageDirectory() +"/m");

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .diskCache(new UnlimitedDiskCache(file))
                .diskCacheSize(50*1024*1024)
                .memoryCacheExtraOptions(100,100)
                .memoryCacheSize(2*1024*1024)
                .build();

        ImageLoader.getInstance().init(configuration);



        x.Ext.init(this);
    }
}
