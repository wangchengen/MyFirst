package com.bawei.wxn.wangchengen20170710;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bawei.wxn.wangchengen20170710.bean.DataInfo;

import java.util.List;

/**
 * Created by 王承恩 on 2017/7/10.
 * <p>
 * 用于做listview适配
 */

public class MyBase extends BaseAdapter {

    private List<DataInfo.ResultBean.DataBean> list;
    private Context context;

    public MyBase(List<DataInfo.ResultBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
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
        if (convertView == null) {

            holder = new viewHolder();
            convertView = View.inflate(context, R.layout.item, null);
            holder.imfro = (TextView) convertView.findViewById(R.id.textView3);
            holder.ingredients = (TextView) convertView.findViewById(R.id.textView4);

            convertView.setTag(holder);

        } else {
            holder = (viewHolder) convertView.getTag();
        }

        holder.imfro.setText(list.get(position).getImtro());
        holder.ingredients.setText(list.get(position).getIngredients());


        return convertView;
    }

    class viewHolder {
        TextView imfro, ingredients;
    }
}
