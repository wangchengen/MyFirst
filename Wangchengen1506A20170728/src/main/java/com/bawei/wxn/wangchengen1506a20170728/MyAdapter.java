package com.bawei.wxn.wangchengen1506a20170728;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.wxn.wangchengen1506a20170728.bean.DataInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by wxn on 2017/7/28.
 * <p>
 * 用于做Xlistview适配数据
 */

public class MyAdapter extends BaseAdapter {

    private List<DataInfo.ListBean> list;
    private Context context;

    private DisplayImageOptions options;

//    加载数据
    public void RefreshData(List<DataInfo.ListBean> datas){

        for (DataInfo.ListBean data : datas) {
            list.add(0,data);
        }



    }

    public MyAdapter(List<DataInfo.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
//为imageloader 配置加载显示默认图
        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .showImageOnLoading(R.mipmap.ic_launcher)
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
    public int getItemViewType(int position) {

//    	listview 多条目展示，根据 type 等于1 和4 ，显示1张图片和4张图片
        int type = list.get(position).getType();

        if (type == 4) {

            return 0;

        } else {
            return 1;
        }


    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        viewHolder holder;

        int type = getItemViewType(position);

        if (type == 0) {

            if (convertView == null) {
                holder = new viewHolder();
                convertView = View.inflate(context, R.layout.first_item, null);
                holder.t1 = (TextView) convertView.findViewById(R.id.textView);
                holder.img1 = (ImageView) convertView.findViewById(R.id.imageView);
                holder.img2 = (ImageView) convertView.findViewById(R.id.imageView2);
                holder.img3 = (ImageView) convertView.findViewById(R.id.imageView3);
                holder.img4 = (ImageView) convertView.findViewById(R.id.imageView4);

                convertView.setTag(holder);

            } else {
                holder = (viewHolder) convertView.getTag();
            }


        }else {
            if (convertView == null) {
                holder = new viewHolder();
                convertView = View.inflate(context, R.layout.second_item, null);
                holder.t2 = (TextView) convertView.findViewById(R.id.textView);
                holder.img5 = (ImageView) convertView.findViewById(R.id.imageView);


                convertView.setTag(holder);

            } else {
                holder = (viewHolder) convertView.getTag();
            }


        }

        String[] imgUrls = list.get(position).getPic().split("\\|");

        if(type == 0){

            holder.t1.setText(list.get(position).getTitle());
            ImageLoader.getInstance().displayImage(imgUrls[0],holder.img1,options);
            ImageLoader.getInstance().displayImage(imgUrls[1],holder.img2,options);
            ImageLoader.getInstance().displayImage(imgUrls[2],holder.img3,options);
            ImageLoader.getInstance().displayImage(imgUrls[3],holder.img4,options);

        }else{
            holder.t2.setText(list.get(position).getTitle());
            ImageLoader.getInstance().displayImage(imgUrls[0],holder.img5,options);
        }


        return convertView;
    }


    class viewHolder {

        TextView t1, t2;
        ImageView img1, img2, img3, img4, img5;

    }
}
