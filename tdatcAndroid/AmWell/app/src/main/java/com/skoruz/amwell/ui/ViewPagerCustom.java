package com.skoruz.amwell.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by SKoruz-Keerthi on 26-11-2015.
 */
public class ViewPagerCustom extends ViewPager {
    private Boolean isPagingEnabled = true;
    private float lastX;
    private float lastY;
    private float xDistance;
    private float yDistance;

    public ViewPagerCustom(Context context) {
        super(context);
    }

    public ViewPagerCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case 0:
                this.yDistance=0.0f;
                this.xDistance=0.0f;
                this.lastX=ev.getX();
                this.lastY=ev.getY();
                break;
            case 1:
                super.onInterceptTouchEvent(ev);
                return false;
            case 2:
                float x=ev.getX();
                float y=ev.getY();
                this.xDistance += Math.abs(x-this.lastX);
                this.yDistance += Math.abs(y-this.lastY);
                this.lastX=x;
                this.lastY=y;
                if (this.xDistance-this.yDistance>5.0f){
                    return true;
                }
                break;
            case 3:
                super.onInterceptTouchEvent(ev);
                return false;
        }
        if (this.isPagingEnabled.booleanValue() && super.onInterceptTouchEvent(ev)){
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.isPagingEnabled && super.onTouchEvent(ev)){
            return true;
        }
        return false;
    }

    public void setIsPagingEnabled(Boolean isPagingEnabled) {
        this.isPagingEnabled = isPagingEnabled;
    }
}
