package com.bawei.wxn.wangchengen20170821;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wxn on 2017/8/21.
 */

public class OtherFragment extends Fragment {

    private View view;
    private String title;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        title = getArguments().getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_other_item, container, false);


        TextView t = (TextView) view.findViewById(R.id.title);

        t.setText(title);

        return view;
    }
}
