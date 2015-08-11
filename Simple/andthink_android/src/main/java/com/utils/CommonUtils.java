package com.utils;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Leo on 2015/5/7.
 *
 * 
 */
public class CommonUtils {

    /**
     * 时间格式化
     * @param time
     * @return
     */
    public static String getTime(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(new Date(Long.parseLong(String.valueOf(time))));
    }

    /**
     * 时间工具
     *
     * @param time
     * @return
     */
    public static String getLocalTime(long time) {

        //与当前间隔时间
        long in = System.currentTimeMillis() - time;
        //秒
        int s = (int) (in / 1000);
        if (s < 60)
            return s + "秒前";
        //分钟
        int m = s / 60;
        if (m < 60)
            return m + "分钟前";
        //小时
        int h = s / 60;
        if (h <= 8)
            return h + "小时前";

        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(new Date(Long.parseLong(String.valueOf(time))));

    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
