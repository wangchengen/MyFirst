package com.bawei.wxn.wangchengen1506a20170825.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.wxn.wangchengen1506a20170825.R;
import com.bawei.wxn.wangchengen1506a20170825.bean.HttpData;
import com.bawei.wxn.wangchengen1506a20170825.bean.SQLiteBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by wxn on 2017/8/25.
 */

public class MyAdapter extends BaseAdapter {

    private List<HttpData.ResultBean.DataBean> list;
    private List<SQLiteBean> sqliteList;

    private boolean isSQLiteData = false;
    private Context context;

    private DisplayImageOptions options;
    public MyAdapter(List<HttpData.ResultBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
//8)	设置模式失败图（
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .showImageOnFail(R.mipmap.ic_launcher)//失败图
                .build();
    }

    public MyAdapter(List<SQLiteBean> sqliteList, Context context,boolean isSQLiteData) {
        this.sqliteList = sqliteList;
        this.isSQLiteData = isSQLiteData;
        this.context = context;
        //8)	设置模式失败图（
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .showImageOnFail(R.mipmap.ic_launcher)//失败图
                .build();
    }

    public void LoadMoreData(List<HttpData.ResultBean.DataBean> data){

        for (HttpData.ResultBean.DataBean dataBean : data) {

            list.add(dataBean);
        }

    }

    @Override
    public int getCount() {
        if(isSQLiteData){
            return sqliteList.size();
        }else {
            return list.size();
        }

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        viewHolder holder;

        if(convertView == null){

            holder = new viewHolder();
            convertView = View.inflate(context, R.layout.listview_item,null);
            holder.img = (ImageView) convertView.findViewById(R.id.ImageView);
            holder.tv = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);

        }else {
            holder = (viewHolder) convertView.getTag();
        }

        if(isSQLiteData){
            holder.tv.setText(sqliteList.get(position).getTitle());
            ImageLoader.getInstance().displayImage(sqliteList.get(position).getImgUrl()
                    ,holder.img,options);
        }else {
            holder.tv.setText(list.get(position).getImtro());

            ImageLoader.getInstance().displayImage(list.get(position).getAlbums().get(0)
                    ,holder.img,options);
        }



        return convertView;
    }

    class viewHolder{
        ImageView img;
        TextView tv;

    }
}
