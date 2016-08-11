package com.skoruz.amwell.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;

import com.android.volley.ui.NetworkImageView;
import com.skoruz.amwell.R;

/**
 * Created by SKoruz-Keerthi on 23-11-2015.
 */
public class BezelImageView extends NetworkImageView {
    private Paint mBlackPaint;
    private Paint mMaskedPaint;

    private Rect mBounds;
    private RectF mBoundsF;

    private Drawable mBorderDrawable;
    private Drawable mMaskDrawable;

    private ColorMatrixColorFilter mDesaturateColorFilter;
    private boolean mDesaturateOnPress = false;

    private boolean mCacheValid = false;
    private Bitmap mCacheBitmap;
    private int mCachedWidth;
    private int mCachedHeight;

    public BezelImageView(Context context) {
        this(context, null);
    }

    public BezelImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezelImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mDesaturateOnPress = false;
        this.mCacheValid = false;
        // Attribute initialization
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BezelImageView,defStyle, 0);

        mMaskDrawable = a.getDrawable(R.styleable.BezelImageView_maskDrawable);
        if (mMaskDrawable != null) {
            mMaskDrawable.setCallback(this);
        }

        mBorderDrawable = a.getDrawable(R.styleable.BezelImageView_borderDrawable);
        if (mBorderDrawable != null) {
            mBorderDrawable.setCallback(this);
        }

        mDesaturateOnPress = a.getBoolean(R.styleable.BezelImageView_desaturateOnPress,mDesaturateOnPress);

        a.recycle();

        // Other initialization
        mBlackPaint = new Paint();
        mBlackPaint.setColor(0xff000000);

        mMaskedPaint = new Paint();
        mMaskedPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // Always want a cache allocated.
        mCacheBitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);

        if (mDesaturateOnPress) {
            // Create a desaturate color filter for pressed state.
            ColorMatrix cm = new ColorMatrix();
            cm.setSaturation(0.0f);
            mDesaturateColorFilter = new ColorMatrixColorFilter(cm);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mBorderDrawable != null && mBorderDrawable.isStateful()) {
            mBorderDrawable.setState(getDrawableState());
        }
        if (mMaskDrawable != null && mMaskDrawable.isStateful()) {
            mMaskDrawable.setState(getDrawableState());
        }
        if (isDuplicateParentStateEnabled()) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mBorderDrawable || who == mMaskDrawable || super.verifyDrawable(who);
    }

    @Override
    public void invalidateDrawable(Drawable who) {
        if (who == mBorderDrawable || who == mMaskDrawable) {
            invalidate();
        } else {
            super.invalidateDrawable(who);
        }
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        boolean changed = super.setFrame(l, t, r, b);
        mBounds = new Rect(0, 0, r - l, b - t);
        mBoundsF = new RectF(mBounds);

        if (mBorderDrawable != null) {
            mBorderDrawable.setBounds(mBounds);
        }
        if (mMaskDrawable != null) {
            mMaskDrawable.setBounds(mBounds);
        }

        if (changed) {
            mCacheValid = false;
        }

        return changed;
    }

    protected void onDraw(Canvas canvas) {
        if (this.mBounds != null) {
            int width = this.mBounds.width();
            int height = this.mBounds.height();
            if (width != 0 && height != 0 && !isInEditMode()) {
                if (!(this.mCacheValid && width == this.mCachedWidth && height == this.mCachedHeight)) {
                    if (width == this.mCachedWidth && height == this.mCachedHeight) {
                        this.mCacheBitmap.eraseColor(0);
                    } else {
                        this.mCacheBitmap.recycle();
                        this.mCacheBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                        this.mCachedWidth = width;
                        this.mCachedHeight = height;
                    }
                    Canvas cacheCanvas = new Canvas(this.mCacheBitmap);
                    int sc;
                    if (this.mMaskDrawable != null) {
                        ColorFilter colorFilter;
                        sc = cacheCanvas.save();
                        this.mMaskDrawable.draw(cacheCanvas);
                        Paint paint = this.mMaskedPaint;
                        if (this.mDesaturateOnPress && isPressed()) {
                            colorFilter = this.mDesaturateColorFilter;
                        } else {
                            colorFilter = null;
                        }
                        paint.setColorFilter(colorFilter);
                        cacheCanvas.saveLayer(this.mBoundsF, this.mMaskedPaint,
                                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG);
                        super.onDraw(cacheCanvas);
                        cacheCanvas.restoreToCount(sc);
                    } else if (this.mDesaturateOnPress && isPressed()) {
                        sc = cacheCanvas.save();
                        cacheCanvas.drawRect(0.0f, 0.0f, (float) this.mCachedWidth, (float) this.mCachedHeight, this.mBlackPaint);
                        this.mMaskedPaint.setColorFilter(this.mDesaturateColorFilter);
                        cacheCanvas.saveLayer(this.mBoundsF, this.mMaskedPaint,
                                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG);
                        super.onDraw(cacheCanvas);
                        cacheCanvas.restoreToCount(sc);
                    } else {
                        super.onDraw(cacheCanvas);
                    }
                    if (this.mBorderDrawable != null) {
                        this.mBorderDrawable.draw(cacheCanvas);
                    }
                }
                canvas.drawBitmap(this.mCacheBitmap, (float) this.mBounds.left, (float) this.mBounds.top, null);
            }
        }
    }
    }
