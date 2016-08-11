package com.skoruz.amwell.ui.zdepth.shadow;

import android.graphics.Canvas;

import com.skoruz.amwell.ui.zdepth.ZDepthParam;


public interface Shadow {
    public void setParameter(ZDepthParam parameter, int left, int top, int right, int bottom);
    public void onDraw(Canvas canvas);
}
