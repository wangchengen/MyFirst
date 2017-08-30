package com.bawei.wxn.wangchengen1506a20170814;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by wxn on 2017/8/14.
 */

public class MyAdapter extends BaseAdapter {
    private List<DataInfo.DataBean> list;
    private Context context;

    private DisplayImageOptions options;

    public MyAdapter(List<DataInfo.DataBean> list, Context context) {
        this.list = list;
        this.context = context;

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(R.mipmap.ic_launcher)//设置加载失败图片
                .build();
    }

    public void addData(List<DataInfo.DataBean> datas){

        for (DataInfo.DataBean data : datas) {

            list.add(data);

        }

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

        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context,R.layout.item,null);
            holder.t = (TextView) convertView.findViewById(R.id.textView);
            holder.img = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.t.setText(list.get(position).getNews_title());

        ImageLoader.getInstance().displayImage(list.get(position).getPic_url(),
                holder.img,options);


        return convertView;
    }

    class ViewHolder{

        TextView t;
        ImageView img;
    }
}
