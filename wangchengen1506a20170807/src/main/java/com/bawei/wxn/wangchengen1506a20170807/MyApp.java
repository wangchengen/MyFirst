package com.bawei.wxn.wangchengen1506a20170807;

import android.app.Application;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.io.File;

/**
 * Created by wxn on 2017/8/7.
 */

public class MyApp extends Application {

    {


        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");

    }


    @Override
    public void onCreate() {
        super.onCreate();

        File file = new File(Environment.getExternalStorageDirectory()+"/mycard");

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheSize(2*1024*1024)
                .memoryCacheExtraOptions(100,100)
                .diskCache(new UnlimitedDiskCache(file))
                .diskCacheSize(50*1024*1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .build();

        ImageLoader.getInstance().init(configuration);

        UMShareAPI.get(this);

    }
}
