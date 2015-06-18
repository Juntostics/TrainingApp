package com.juntostics.trainingapp.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Simple RecyclerView subclass that supports providing an empty view (which
 * is displayed when the adapter has no data and hidden otherwise).
 */
public class SimpleRecyclerView extends RecyclerView{
    private View mEmptyView;

    private AdapterDataObserver mDataObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateEmptyView();
        }
    };

    public SimpleRecyclerView(Context context) {
        super(context);
    }

    public SimpleRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setEmptyView(View emptyview) {
        this.mEmptyView = emptyview;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if(getAdapter() != null){
            getAdapter().unregisterAdapterDataObserver(mDataObserver);
        }
        if (adapter != null){
            adapter.registerAdapterDataObserver(mDataObserver);
        }
        super.setAdapter(adapter);
        updateEmptyView();
    }

    private void updateEmptyView(){
        if(mEmptyView!=null && getAdapter()!= null){
            boolean showEmptyView = getAdapter().getItemCount()==0;
            mEmptyView.setVisibility(showEmptyView ? VISIBLE : GONE);
            setVisibility(showEmptyView ? GONE: VISIBLE);
        }
    }
}
