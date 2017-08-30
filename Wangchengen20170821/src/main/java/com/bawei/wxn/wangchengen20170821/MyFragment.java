package com.bawei.wxn.wangchengen20170821;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bawei.wxn.wangchengen20170821.Adapter.MyAdapter;
import com.bawei.wxn.wangchengen20170821.SQLite.BookDao;
import com.bawei.wxn.wangchengen20170821.bean.BooksInfo;
import com.bawei.wxn.wangchengen20170821.bean.DBbookBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import me.maxwin.view.XListView;

/**
 * Created by wxn on 2017/8/21.
 */

public class MyFragment extends Fragment implements XListView.IXListViewListener {

    private View view;

    private XListView xListView;


    private BookDao dao;

    private MyAdapter myAdapter;
    private int skip = 1;
    private List<BooksInfo.ResultBean.BookListBean> list;
    private List<DBbookBean> Dblist;

    private boolean isLoadMore = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_item, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        xListView = (XListView) view.findViewById(R.id.xlv);

        xListView.setPullLoadEnable(true);
        xListView.setXListViewListener(this);


        dao = new BookDao(getContext());

        Dblist = dao.findData();
//        3)	数据存储到本地数据库，第二次启动从本地数据库读取并显示
        if(Dblist != null || Dblist.size() != 0){

            myAdapter = new MyAdapter(getContext(),Dblist,true);

            xListView.setAdapter(myAdapter);

        }else {
            setData("http://japi.juhe.cn/comic/book");
        }


    }

    private void setData(String uri) {

        RequestParams params = new RequestParams(uri);
        params.addBodyParameter("key","9c4397c42d1542e18439484ffd6a78da");
        params.addBodyParameter("type","少年漫画");
        params.addBodyParameter("skip",String.valueOf(skip));

//2)	请求数据，并显示
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();
                BooksInfo info = gson.fromJson(result,BooksInfo.class);

                list = info.getResult().getBookList();

                dao.saveData(list);

                if(!isLoadMore){
                    myAdapter = new MyAdapter(getContext(),list);

                    xListView.setAdapter(myAdapter);
                }else {

                    myAdapter.addData(list);
                    myAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


//    Listview 实现上下拉刷新

    @Override
    public void onRefresh() {
        skip = 1;

        isLoadMore = false;

        setData("http://japi.juhe.cn/comic/book");
        xListView.stopRefresh();

    }

    @Override
    public void onLoadMore() {

        skip = skip + 20;

        isLoadMore = true;

        setData("http://japi.juhe.cn/comic/book");
        xListView.stopLoadMore();
    }
}
