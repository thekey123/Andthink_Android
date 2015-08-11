package com.activitys;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;

import com.base.BaseActivity;

import java.util.List;

/**
 * Created by thekey on 2015/8/10.
 * <p/>
 * 实现andorid传统的下部切换布局代码
 * <p/>
 * 这里传入的数据，四个map，分别是三个fragment——三个imageview-三张未选中图片-三张选中的图片
 * <p/>
 * 目前只支持三个tab切换
 */
public abstract class BottomTabActivity extends BaseActivity {

    private final static int ONE = 0, TWO = 1, THREE = 2;

    /**
     * 显示指定的fragment
     * * @param index
     */
    protected void showFragment(int index, int layout) {
        hideAllFragments();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (index) {
            case ONE:
                transaction.replace(layout, oneFragment());
                transaction.show(oneFragment());
                break;
            case TWO:
                transaction.replace(layout, twoFragment());
                transaction.show(twoFragment());
                break;
            case THREE:
                transaction.replace(layout, threeFragment());
                transaction.show(threeFragment());
                break;
        }
        transaction.commit();
        changeBottomButtonStyle(index);
    }

    /**
     * 根据用户点击，改变底部button样式
     */
    protected void changeBottomButtonStyle(int index) {
        for (int i = 0; i <= 2; i++) {
            if (i == index) {
                setImageview().get(i).setImageResource(setImages_on()[i]);
            } else {
                setImageview().get(i).setImageResource(setImages_off()[i]);
            }
        }
    }

    /**
     * 隐藏所有Fragment
     */
    protected void hideAllFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (oneFragment() != null)
            transaction.hide(oneFragment());
        if (twoFragment() != null)
            transaction.hide(twoFragment());
        if (threeFragment() != null)
            transaction.hide(threeFragment());
        transaction.commit();
    }

    protected abstract Fragment oneFragment();

    protected abstract Fragment twoFragment();

    protected abstract Fragment threeFragment();

    protected abstract List<ImageView> setImageview();

    protected abstract int[] setImages_on();

    protected abstract int[] setImages_off();
}
