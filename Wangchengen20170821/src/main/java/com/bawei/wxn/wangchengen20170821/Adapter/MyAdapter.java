package com.bawei.wxn.wangchengen20170821.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bawei.wxn.wangchengen20170821.R;
import com.bawei.wxn.wangchengen20170821.SQLite.BookDao;
import com.bawei.wxn.wangchengen20170821.bean.BooksInfo;
import com.bawei.wxn.wangchengen20170821.bean.DBbookBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxn on 2017/8/21.
 */

public class MyAdapter extends BaseAdapter {

    private Context context;

    private List<BooksInfo.ResultBean.BookListBean> list;
    private List<DBbookBean> Dblist;
    private boolean isDataBase = false;

//    5)	使用图片加载框架，加载图片并显示，设置模式失败图
    private DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheOnDisk(true)
            .showImageOnFail(R.mipmap.ic_launcher)
            .build();
    private PopupWindow popupWindow;
    private View popview;
    private ImageView closeButton;
    private TextView deleteButton;
    private BookDao dao;

    public MyAdapter(Context context, List<DBbookBean> dblist, boolean isDataBase) {
        this.context = context;
        Dblist = dblist;
        this.isDataBase = isDataBase;

        initPop();
    }



    public MyAdapter(Context context, List<BooksInfo.ResultBean.BookListBean> list) {
        this.context = context;
        this.list = list;

        initPop();
    }

    public void addData(List<BooksInfo.ResultBean.BookListBean> otherList){

        if(isDataBase){

            List<DBbookBean> Dblist2 = new ArrayList<>();

            for (BooksInfo.ResultBean.BookListBean bookListBean : otherList) {

                Dblist2.add(new DBbookBean(bookListBean.getName(),
                        bookListBean.getCoverImg()));

            }

            Dblist.addAll(Dblist2);

        }else{
            for (BooksInfo.ResultBean.BookListBean bookListBean : otherList) {
                list.add(bookListBean);

            }
        }

    }

    @Override
    public int getCount() {

        if(isDataBase){
            return Dblist.size();
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.listview_item,null);
            holder.t = (TextView) convertView.findViewById(R.id.textView);
            holder.img = (ImageView) convertView.findViewById(R.id.imageView);


            convertView.setTag(holder);

        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(isDataBase){

            holder.t.setText(Dblist.get(position).getName());

            ImageLoader.getInstance().displayImage(Dblist.get(position).getImgUrl(),
                    holder.img,options);

        }else {
            holder.t.setText(list.get(position).getName());

            ImageLoader.getInstance().displayImage(list.get(position).getCoverImg(),
                    holder.img,options);
        }

        holder.popButton = (ImageView) convertView.findViewById(R.id.popButton);
        holder.popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPop(v,position);

            }
        });



        return convertView;
    }




    class ViewHolder{
        TextView t;
        ImageView img,popButton;

    }

//    初始化popupwindow
    private void initPop() {

        popview = View.inflate(context, R.layout.pop_item,null);

        popupWindow = new PopupWindow(popview, ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));


    }

//    6)	点击在正确的位置弹出popupwindow
    private void showPop(View view, final int position) {

        popupWindow.showAsDropDown(view);

        closeButton = (ImageView) popview.findViewById(R.id.closeButton);
        deleteButton = (TextView) popview.findViewById(R.id.t1);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                popupWindow.dismiss();
            }
        });


//        7)	点击popupwindow 中的不感兴趣，当前item从集合和数据库中移除（
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dao = new BookDao(context);

                if(isDataBase){

                    Dblist.remove(position);

                    dao.deleteData(position);
                    notifyDataSetChanged();
                    popupWindow.dismiss();

                }else{

                    list.remove(position);
                    dao.deleteData(position);
                    notifyDataSetChanged();
                    popupWindow.dismiss();

                }

            }
        });

    }

}
