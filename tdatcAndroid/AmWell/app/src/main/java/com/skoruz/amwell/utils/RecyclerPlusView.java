package com.skoruz.amwell.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by SKoruz-Keerthi on 03-11-2015.
 */
public class RecyclerPlusView extends RecyclerView {
    private View emptyView;
    private final AdapterDataObserver observer = new AdapterDataObserver() {
        public void onChanged() {
            RecyclerPlusView.this.checkIfEmpty();
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            RecyclerPlusView.this.checkIfEmpty();
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            RecyclerPlusView.this.checkIfEmpty();
        }
    };

    public RecyclerPlusView(Context context) {
        super(context);
    }

    public RecyclerPlusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerPlusView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void checkIfEmpty() {
        if (this.emptyView != null && getAdapter() != null) {
            this.emptyView.setVisibility(getAdapter().getItemCount() > 0 ? GONE : VISIBLE);
        }
    }

    public void setAdapter(Adapter adapter) {
        Adapter oldAdapter = getAdapter();
        if (oldAdapter != null) {
            oldAdapter.unregisterAdapterDataObserver(this.observer);
        }
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.observer);
        }
        checkIfEmpty();
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (this.emptyView == null || !(visibility == GONE || visibility == INVISIBLE)) {
            checkIfEmpty();
        } else {
            this.emptyView.setVisibility(GONE);
        }
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        checkIfEmpty();
    }
}
