package com.bawei.wxn.wangchengen20170710.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 王承恩 on 2017/7/10.
 *
 * 用于做字节流转换的工具类
 */

public class SteamTools {

    public String dataToJson(InputStream is) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        int len = 0;
        byte[] buffer = new byte[1024];

        while((len = is.read(buffer)) != -1){

            os.write(buffer,0,len);

        }

        os.close();
        is.close();

        return os.toString();

    }
}
