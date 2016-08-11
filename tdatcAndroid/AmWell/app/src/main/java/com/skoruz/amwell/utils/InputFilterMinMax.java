package com.skoruz.amwell.utils;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by SKoruz-Keerthi on 19-10-2015.
 */
public class InputFilterMinMax implements InputFilter
{
    private int max;
    private int min;

    public InputFilterMinMax(int paramInt1, int paramInt2)
    {
        this.min = paramInt1;
        this.max = paramInt2;
    }

    public InputFilterMinMax(String paramString1, String paramString2)
    {
        this.min = Integer.parseInt(paramString1);
        this.max = Integer.parseInt(paramString2);
    }

    private boolean isInRange(int a, int b, int c)
    {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }

    public CharSequence filter(CharSequence paramCharSequence, int paramInt1, int paramInt2, Spanned paramSpanned, int paramInt3, int paramInt4)
    {
        String str1 = paramCharSequence.subSequence(paramInt1, paramInt2).toString();
        String str2 = paramSpanned.subSequence(0, paramInt3).toString() + str1 + paramSpanned.subSequence(paramInt4, paramSpanned.length()).toString();
        try
        {
            int i = Integer.parseInt(str2);
            boolean bool = isInRange(this.min, this.max, i);
            if (bool) {
                return null;
            }
        }
        catch (NumberFormatException localNumberFormatException)
        {
            return null;
        }
        return "";
    }
}