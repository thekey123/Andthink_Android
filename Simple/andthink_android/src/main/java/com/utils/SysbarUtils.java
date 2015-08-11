package com.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Leo on 2015/5/7.
 *
 * 改变状态栏颜色
 */
public class SysbarUtils {

    public static void init(Context context, RelativeLayout rl,int color) {
        Window win = ((Activity) context).getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        SystemBarTintManager tintManager = new SystemBarTintManager((Activity) context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(context.getResources().getColor(color));
        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();

        Class clz = rl.getParent().getClass();
        if (clz == LinearLayout.class) {
            LinearLayout.LayoutParams l = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(context, 50));
            l.setMargins(0, config.getPixelInsetTop(false), 0, 0);
            rl.setLayoutParams(l);
        } else if (clz == RelativeLayout.class) {
            RelativeLayout.LayoutParams r = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, CommonUtils.dip2px(context, 50));
            r.setMargins(0, config.getPixelInsetTop(false), 0, 0);
            rl.setLayoutParams(r);
        }

    }

}
