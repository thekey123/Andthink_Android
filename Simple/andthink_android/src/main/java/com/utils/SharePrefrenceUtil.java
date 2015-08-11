/**
 * 
 */
package com.utils;


import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Thekey on 2015/5/9.
 *
 * sharePrefrence工具类
 *
 */
public class SharePrefrenceUtil {

	private static final String PREFERENCE_NAME = "TheKey";
	private static final String SHARED_KEY_USER_SITUATION = "isFirstUse";
	private static SharedPreferences sp;
	private static SharedPreferences.Editor editor;
	private static SharePrefrenceUtil mInstance;

	/**
	 *初始化SharePrefrenceUtil
	 * @param context
	 */
	private SharePrefrenceUtil(Context context) {
		sp = context
				.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public synchronized static void init(Context ctx) {
        mInstance = new SharePrefrenceUtil(ctx);
	}

	public synchronized static SharePrefrenceUtil getInstance() {
		if (null == mInstance){
			throw new RuntimeException("使用前请初始化SharePrefrence");
		}
		return mInstance;
	}

	/**
	 * 用户是不是第一次进入APP
	 * @param isFirstUser 
	 */
	public void setIsFirstUser(boolean isFirstUser){
		editor.putBoolean(SHARED_KEY_USER_SITUATION, isFirstUser);
		editor.commit();
	}

    /**
     * 获取用户使用情况
     * @return
     */
	public boolean getUseValue(){
		return sp.getBoolean(SHARED_KEY_USER_SITUATION, true);
	}

	/**
	 * 注册成功之后写入用户数据
	 */
	public void setUerInfo(String user_id) {
		editor.putString("user_id", user_id);
		editor.commit();
	}

	/**
	 * 获取当前是否有用户登录
	 * @return
	 */
	public String getUserInfo() {
		return sp.getString("user_id","");
	}

	/**
	 * 保存用户在意见反馈界面所保存的反馈内容
	 */

	public void setFeedBackContent(String content){
		editor.putString("content",content);
		editor.commit();
	}

	/**
	 * 获取用户在意见反馈界面所保存的反馈内容
	 */
	public String getFeedBackContent() {
		return sp.getString("content",null);
	}
}
