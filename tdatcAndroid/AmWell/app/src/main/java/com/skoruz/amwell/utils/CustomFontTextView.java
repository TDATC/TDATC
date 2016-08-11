package com.skoruz.amwell.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.skoruz.amwell.R;

/**
 * Created by SKoruz-Keerthi on 07-10-2015.
 */
public class CustomFontTextView extends TextView
    {
        private Context mContext;

        public CustomFontTextView(Context paramContext)
        {
            super(paramContext);
            this.mContext = paramContext;
        }

        public CustomFontTextView(Context paramContext, AttributeSet paramAttributeSet)
        {
            super(paramContext, paramAttributeSet);
            this.mContext = paramContext;
            setCustomFont(this, paramContext, paramAttributeSet);
        }

        public CustomFontTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
        {
            super(paramContext, paramAttributeSet, paramInt);
            setCustomFont(this, paramContext, paramAttributeSet);
        }

        public static void setCustomFont(TextView paramTextView, Context paramContext, AttributeSet paramAttributeSet)
        {
            TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, R.styleable.CustomFont);
            /*setCustomFont(paramTextView, localTypedArray.getString(0), paramContext);
            localTypedArray.recycle();*/

            String fontName=localTypedArray.getString(R.styleable.CustomFonts_fontName);
            if (fontName!=null){
                Typeface typeface=Typeface.createFromAsset(paramContext.getAssets(),fontName);
                paramTextView.setTypeface(typeface);
            }
            localTypedArray.recycle();
        }

        /*public static void setCustomFont(TextView paramTextView, String paramString, Context paramContext)
        {
            if (paramString == null) {}
            Typeface localTypeface;
            do
            {
                return;
                localTypeface = FontCache.get(paramString, paramContext);
            } while (localTypeface == null);
            paramTextView.setTypeface(localTypeface);
        }*/
}
