package com.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.utils.SysbarUtils;
import butterknife.ButterKnife;
/**
 * Created by Mr.zheng on 2015/7/20.
 *
 * 所有Fragment的父类
 */
public abstract class BaseFragment extends android.support.v4.app.Fragment {

    // 缓存布局对象
    private View rootView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVariable();
        setAttribute();
        addListener();
    }

    /**
     * 释放相关用到的api资源
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //缓存fragment，先判断是否加载过
        if (null != rootView) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (null != parent) {
                parent.removeView(rootView);
            }
        } else {
            rootView =initView(inflater, null);
        }
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    /**
     * 改变手机通知栏颜色，使之与app主题色一致
     */
    protected void initSystemBar(RelativeLayout rl , int color) {
        SysbarUtils.init(getActivity(), rl,color);
    }

    /**
     * 设置相关属性方法
     */
    protected abstract void setAttribute();

    /**
     * 初始化用到的相关常量api
     */
    protected abstract void initVariable();


    /**
     * 添加事件监听器
     */
    protected abstract void addListener();

    /**
     *给界面添加布局
     */
    protected abstract View initView(LayoutInflater inflater, ViewGroup container);

}
