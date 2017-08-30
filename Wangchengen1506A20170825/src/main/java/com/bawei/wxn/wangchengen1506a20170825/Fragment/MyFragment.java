package com.bawei.wxn.wangchengen1506a20170825.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.wxn.wangchengen1506a20170825.Adapter.MyAdapter;
import com.bawei.wxn.wangchengen1506a20170825.R;
import com.bawei.wxn.wangchengen1506a20170825.SQLite.DataDao;
import com.bawei.wxn.wangchengen1506a20170825.SQLite.MySQLite;
import com.bawei.wxn.wangchengen1506a20170825.bean.HttpData;
import com.bawei.wxn.wangchengen1506a20170825.bean.SQLiteBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import me.maxwin.view.XListView;

/**
 * Created by wxn on 2017/8/25.
 */

public class MyFragment extends Fragment implements XListView.IXListViewListener {

    private View view;

    private String title;

    private XListView listView;
    private TextView textView;


    private int pn = 0;//起始页数

    private List<HttpData.ResultBean.DataBean> list;
    private List<SQLiteBean> sqliteList;

    private boolean isLoadMore = false;
    private MyAdapter adapter;
    private DataDao dao;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        title = bundle.getString("title");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item, container, false);

        textView = (TextView) view.findViewById(R.id.title_tv);
        listView = (XListView) view.findViewById(R.id.xListView);

        textView.setText(title);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if (title.equals("数据")) {

            textView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            dao = new DataDao(getContext());

            listView.setPullLoadEnable(true);
            listView.setXListViewListener(this);


//        无网络从数据库请求数据
            if (getContext().getSharedPreferences("NetWork", Context.MODE_PRIVATE)
                    .getBoolean("isConnect", true)) {
                setData();
            }else{

                sqliteList = dao.findAllData();

                listView.setAdapter(new MyAdapter(sqliteList,getContext(),true));

            }

        }

    }

    private void setData() {

        String uri = "http://apis.juhe.cn/cook/query.php";

        RequestParams params = new RequestParams(uri);

        params.addBodyParameter("menu", "红烧肉");
        params.addBodyParameter("key", "3d48359df155ed9c8bb74dacada494f7");
        params.addBodyParameter("rn", "8");
        params.addBodyParameter("pn", String.valueOf(pn));

        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();

                HttpData httpData = gson.fromJson(result, HttpData.class);

                list = httpData.getResult().getData();

                dao.saveData(list);

                if (!isLoadMore) {
                    adapter = new MyAdapter(list, getContext());

                    listView.setAdapter(adapter);
                } else {
                    adapter.LoadMoreData(list);
                    adapter.notifyDataSetChanged();
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

    @Override
    public void onRefresh() {
//        Toast.makeText(getContext(), "刷新", Toast.LENGTH_SHORT).show();
        pn = 0;
        setData();
        listView.stopRefresh();
    }

    @Override
    public void onLoadMore() {

        isLoadMore = true;
        pn = pn + 7;

        setData();

        listView.stopLoadMore();

        isLoadMore = false;

    }
}
