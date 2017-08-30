package com.bawei.wxn.wangchengen1506a20170807;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wxn on 2017/8/7.
 */

public class TabFragment extends Fragment {

    private View view;

    private TextView textView;
    private String title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        title = bundle.getString("title");
        bundle.clear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_fragment, container,false);
        textView = (TextView) view.findViewById(R.id.textView);

        textView.setText(title);


        return view;
    }


}
