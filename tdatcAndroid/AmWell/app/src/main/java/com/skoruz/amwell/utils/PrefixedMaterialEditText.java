package com.skoruz.amwell.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.skoruz.amwell.BuildConfig;
import com.skoruz.amwell.R;

/**
 * Created by SKoruz-Keerthi on 02-11-2015.
 */
public class PrefixedMaterialEditText extends MaterialEditText {
    private String mPrefix;
    private ColorStateList mPrefixTextColor;

    private class TextDrawable extends Drawable {
        private String mText = BuildConfig.FLAVOR;

        public TextDrawable(String text) {
            this.mText = text;
            setBounds(0, 0, ((int) PrefixedMaterialEditText.this.getPaint().measureText(this.mText)) + 2, (int) PrefixedMaterialEditText.this.getTextSize());
        }

        public void draw(Canvas canvas) {
            Paint paint = PrefixedMaterialEditText.this.getPaint();
            paint.setColor(PrefixedMaterialEditText.this.mPrefixTextColor.getColorForState(PrefixedMaterialEditText.this.getDrawableState(), 0));
            canvas.drawText(this.mText, 0.0f, (float) (canvas.getClipBounds().top + PrefixedMaterialEditText.this.getLineBounds(0, null)), paint);
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }

        public int getOpacity() {
            return 1;
        }
    }

    public PrefixedMaterialEditText(Context context) {
        this(context, null);
    }

    public PrefixedMaterialEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PrefixedMaterialEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mPrefixTextColor = getTextColors();
        String prefix = context.obtainStyledAttributes(attrs, R.styleable.PrefixedMaterialEditText, defStyle, 0).getString(0);
        if (prefix != null) {
            setPrefix(prefix);
        }
    }

    public void setPrefix(String prefix) {
        this.mPrefix = prefix;
        setCompoundDrawables(new TextDrawable(prefix), null, null, null);
    }

    public String getPrefix() {
        return this.mPrefix;
    }

    public void setPrefixTextColor(int color) {
        this.mPrefixTextColor = ColorStateList.valueOf(color);
    }

    public void setPrefixTextColor(ColorStateList color) {
        this.mPrefixTextColor = color;
    }
}

