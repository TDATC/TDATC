package com.skoruz.amwell.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by SKoruz-Keerthi on 02-12-2015.
 */
public class RecyclerViewCustom extends RecyclerView {
    public RecyclerViewCustom(Context context) {
        super(context);
    }

    public RecyclerViewCustom(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RecyclerViewCustom(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public boolean canScrollHorizontally(int n) {
        return false;
    }

    public boolean canScrollVertically(int n) {
        return false;
    }

    public void stopScroll() {
        try {
            super.stopScroll();
            return;
        } catch (NullPointerException var1_1) {
            return;
        }
    }
}
