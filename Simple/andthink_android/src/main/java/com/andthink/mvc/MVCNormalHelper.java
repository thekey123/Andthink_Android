/*
 Copyright 2015 shizhefei（LuckyJayce）

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
package com.andthink.mvc;

import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

public class MVCNormalHelper<DATA> extends MVCHelper<DATA> {

    public MVCNormalHelper(View contentView, PullToRefreshBase<? extends View> pullToRefreshAdapterViewBase) {
        super(new RefreshView(contentView, pullToRefreshAdapterViewBase));
    }

    private static class RefreshView implements IRefreshView {

        private View contentView;
        private PullToRefreshBase<? extends View> pullToRefreshAdapterViewBase;

        public RefreshView(View contentView, PullToRefreshBase<? extends View> pullToRefreshAdapterViewBase) {
            this.contentView = contentView;
            this.pullToRefreshAdapterViewBase = pullToRefreshAdapterViewBase;
            pullToRefreshAdapterViewBase.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            pullToRefreshAdapterViewBase.setOnRefreshListener(new OnRefreshListener211());
        }

        private class OnRefreshListener211<T extends View> implements PullToRefreshBase.OnRefreshListener2<T> {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<T> refreshView) {
                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh();
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<T> refreshView) {

            }

        }

        private OnRefreshListener onRefreshListener;

        @Override
        public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
            this.onRefreshListener = onRefreshListener;
        }

        @Override
        public View getContentView() {
            return contentView;
        }

        @Override
        public void showRefreshComplete() {
            pullToRefreshAdapterViewBase.onRefreshComplete();
        }

        @Override
        public void showRefreshing() {
            pullToRefreshAdapterViewBase.showHeadRefreshing();
        }

        @Override
        public View getSwitchView() {
            return pullToRefreshAdapterViewBase.getRefreshableView();
        }

    }

}
