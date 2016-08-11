/*
 * Decompiled with CFR 0_108.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.View
 */
package com.skoruz.amwell.ui.materialdesign;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.skoruz.amwell.R;

public class MaterialProgressDialog extends ProgressDialog {
    private MaterialProgressDrawable indeterminateDrawable;
    private int mColor;
    private int mDefaultColor;

    public MaterialProgressDialog(Context context) {
        super(context);
    }

    public int getColor() {
        if (this.mColor == 0) {
            return this.mDefaultColor;
        }
        return this.mColor;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDefaultColor = this.getContext().getResources().getColor(R.color.colorAccent);
        this.indeterminateDrawable = new MaterialProgressDrawable(this.getContext(), this.findViewById(android.R.id.progress));
        this.indeterminateDrawable.setBackgroundColor(-328966);
        this.indeterminateDrawable.setAlpha(255);
        this.indeterminateDrawable.updateSizes(2);
        MaterialProgressDrawable materialProgressDrawable = this.indeterminateDrawable;
        int[] arrn = new int[]{this.getColor()};
        materialProgressDrawable.setColorSchemeColors(arrn);
        this.indeterminateDrawable.start();
        this.setIndeterminateDrawable((Drawable)this.indeterminateDrawable);
    }

    public void setColor(int n) {
        this.mColor = n;
    }
}

