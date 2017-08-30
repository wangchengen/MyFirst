package com.bawei.wxn.wangchengen1506a20170728;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by wxn on 2017/7/28.
 * <p>
 * 广播类用于监听网络
 */

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo moinfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiinfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!moinfo.isConnected() && !wifiinfo.isConnected()) {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);

            builder.setTitle("提示");
            builder.setMessage("无网络");

            builder.setPositiveButton("确定",null);



            builder.create().show();

        }


    }
}
