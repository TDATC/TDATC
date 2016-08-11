package com.skoruz.amwell.misc;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.SparseArray;

/**
 * Created by SKoruz-Keerthi on 26-11-2015.
 */
public class FontUtils {
    private static final SparseArray<Typeface> mTypefaces = new SparseArray(16);

    public static Typeface createTypeface(Context context, int n) throws IllegalArgumentException {
        switch (n) {
            default: {
                throw new IllegalArgumentException("Unknown `typeface` attribute value " + n);
            }
            case 0: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/Thin.ttf");
            }
            case 1: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/ThinItalic.ttf");
            }
            case 2: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/Light.ttf");
            }
            case 3: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/LightItalic.ttf");
            }
            case 4: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/Regular.ttf");
            }
            case 5: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/Italic.ttf");
            }
            case 6: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/Medium.ttf");
            }
            case 7: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/MediumItalic.ttf");
            }
            case 8: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/Bold.ttf");
            }
            case 9: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/BoldItalic.ttf");
            }
            case 10: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/Black.ttf");
            }
            case 11: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/BlackItalic.ttf");
            }
            case 12: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/Condensed.ttf");
            }
            case 13: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/CondensedItalic.ttf");
            }
            case 14: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/BoldCondensed.ttf");
            }
            case 15: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/BoldCondensedItalic.ttf");
            }
            case 16: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/SlabThin.ttf");
            }
            case 17: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/SlabLight.ttf");
            }
            case 18: {
                return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/SlabRegular.ttf");
            }
            case 19:
        }
        return Typeface.createFromAsset((AssetManager)context.getAssets(), (String)"fonts/SlabBold.ttf");
    }

    public static SparseArray<Typeface> getTypefaces() {
        return mTypefaces;
    }
}
