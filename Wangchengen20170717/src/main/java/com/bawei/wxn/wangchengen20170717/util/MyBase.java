package com.bawei.wxn.wangchengen20170717.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bawei.wxn.wangchengen20170717.R;
import com.bawei.wxn.wangchengen20170717.bean.DataInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by 王承恩 on 2017/7/17.
 *
 * 用于做listview适配
 */

public class MyBase extends BaseAdapter {

    private List<DataInfo.ListBean> list;
    private Context context;

    private DisplayImageOptions options;

    public MyBase(List<DataInfo.ListBean> list, Context context) {
        this.list = list;
        this.context = context;

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.drawable.ic_error)//加载失败显示图片
                .build();

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            convertView = View.inflate(context, R.layout.item,null);

            holder.date = (TextView) convertView.findViewById(R.id.textView3);
            holder.title = (TextView) convertView.findViewById(R.id.textView2);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(holder);

        }else {
            holder = (viewHolder) convertView.getTag();
        }

        holder.title.setText(list.get(position).getTitle());
        holder.date.setText(list.get(position).getDate());

        ImageLoader.getInstance().displayImage(list.get(position).getPic(),holder.imageView,options);




        return convertView;
    }

    class viewHolder{
        ImageView imageView;
        TextView title,date;
    }
}
