package com.bawei.wxn.wangchengen1506a20170724.adapeter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bawei.wxn.wangchengen1506a20170724.R;
import com.bawei.wxn.wangchengen1506a20170724.bean.DataInfo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by wxn on 2017/7/24.
 *
 * 用于做xlistview适配
 */

public class MyBase extends BaseAdapter {
    private List<DataInfo.ListBean> list;
    private Context context;

    private DisplayImageOptions options;

    public void addData(List<DataInfo.ListBean> data){

        for (DataInfo.ListBean listBean : data) {

            list.add(listBean);

        }
    }

    public MyBase(List<DataInfo.ListBean> list, Context context) {
        this.list = list;
        this.context = context;

        options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        if(position % 3 == 0){

            return 0;

        }else{
            return 1;
        }


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int type = getItemViewType(position);

        viewHolder holder;

        if(type == 0){

            if(convertView == null){
                holder = new viewHolder();
                convertView = View.inflate(context, R.layout.first_item,null);
                holder.first_Img1 = (ImageView) convertView.findViewById(R.id.imageView);
                holder.first_Img2 = (ImageView) convertView.findViewById(R.id.imageView2);
                convertView.setTag(holder);
            }else{
                holder = (viewHolder) convertView.getTag();
            }



        }else{
            if(convertView == null){
                holder = new viewHolder();
                convertView = View.inflate(context, R.layout.second_item,null);
                holder.second_Img1 = (ImageView) convertView.findViewById(R.id.imageView3);
                holder.second_Img2 = (ImageView) convertView.findViewById(R.id.imageView4);
                convertView.setTag(holder);
            }else{
                holder = (viewHolder) convertView.getTag();
            }




        }

        if(type == 0){
            String[] imgUrl = list.get(position).getPic().split("\\|");

            ImageLoader.getInstance().displayImage(imgUrl[0],holder.first_Img1,options);
            ImageLoader.getInstance().displayImage(imgUrl[1],holder.first_Img2,options);
        }else{
            String[] imgUrl = list.get(position).getPic().split("\\|");

            ImageLoader.getInstance().displayImage(imgUrl[0],holder.second_Img1,options);
            ImageLoader.getInstance().displayImage(imgUrl[1],holder.second_Img2,options);
        }


        return convertView;
    }

    class viewHolder{
        ImageView first_Img1,first_Img2,second_Img1,second_Img2;
    }
}
