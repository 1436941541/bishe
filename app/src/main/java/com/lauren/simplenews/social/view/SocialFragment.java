package com.lauren.simplenews.social.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lauren.simplenews.R;
import com.lauren.simplenews.base.BaseFragment;

/**
 * 创建人： 杨云杰
 * 创建时间 ： 2019/1/3
 * 描述：
 */
public class SocialFragment extends BaseFragment {
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        init();
        return getContentView();
    }

    @Override
    protected void init() {
        floatingActionButton = findViewById(R.id.floatActionButton);
        recyclerView = findViewById(R.id.social_list);
//        recyclerView.setAdapter();
    }

    @Override
    protected int setContentView() {
        return R.layout.social_fragment;
    }
}
