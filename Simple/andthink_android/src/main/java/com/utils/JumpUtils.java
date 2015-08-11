package com.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Mr.zheng on 2015/7/28.
 *
 * 跳转的封装类
 *
 */
public class JumpUtils {

    public static void jumpTo(Context context,Class<?> tClass) {
        Intent intent = new Intent(context, tClass);
        context.startActivity(intent);
    }
}
