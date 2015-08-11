package com.andthink.mvc.viewhandler;

import android.view.View;
import android.view.View.OnClickListener;

import com.andthink.mvc.IDataAdapter;
import com.andthink.mvc.ILoadViewFactory.ILoadMoreView;
import com.andthink.mvc.MVCHelper.OnScrollBottomListener;

public interface ViewHandler {

	/**
	 *
	 * @param adapter
	 * @param loadMoreView
	 * @return 是否有 init ILoadMoreView
	 */
	public boolean handleSetAdapter(View contentView, IDataAdapter<?> adapter, ILoadMoreView loadMoreView, OnClickListener onClickLoadMoreListener);

	public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener);

}
