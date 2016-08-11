package com.skoruz.amwell.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.misc.FontUtils;

/**
 * Created by SKoruz-Keerthi on 26-11-2015.
 */
public class MyTextView extends TextView {
    private float letterSpacing = 0.0f;
    private CharSequence originalText = "";

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context, attrs);
    }

    private void applyLetterSpacing() {
        if (this.getLetterSpacing() == 0.0f) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.originalText.length(); ++i) {
            stringBuilder.append(this.originalText.charAt(i));
            if (i + 1 >= this.originalText.length()) continue;
            stringBuilder.append("\u00a0");
        }
        SpannableString spannableString = new SpannableString(stringBuilder.toString());
        if (stringBuilder.toString().length() > 1) {
            for (int j = 1; j < stringBuilder.toString().length(); j += 2) {
                spannableString.setSpan(new ScaleXSpan((1.0f + this.getLetterSpacing()) / 10.0f), j, j + 1, 33);
            }
        }
        super.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

    private Typeface obtainTypeface(Context context, int n) throws IllegalArgumentException {
        Typeface typeface = (Typeface)FontUtils.getTypefaces().get(n);
        if (typeface == null) {
            typeface = FontUtils.createTypeface(context, n);
            FontUtils.getTypefaces().put(n,typeface);
        }
        return typeface;
    }

    private void parseAttributes(Context context, AttributeSet attributeSet) {
        if (this.isInEditMode()) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CustomFont);
        int n = typedArray.getInt(0, 4);
        this.letterSpacing = typedArray.getFloat(1, 0.0f);
        typedArray.recycle();
        this.originalText = this.getText();
        applyLetterSpacing();
        this.setTypeface(obtainTypeface(context, n));
    }

    public float getLetterSpacing() {
        return this.letterSpacing;
    }

    public void setLetterSpacing(float f) {
        this.letterSpacing = f;
        applyLetterSpacing();
    }
}
