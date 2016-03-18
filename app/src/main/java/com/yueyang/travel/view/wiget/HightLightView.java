package com.yueyang.travel.view.wiget;

/**
 * Created by Yang on 2016/3/18.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

public class HightLightView extends FrameLayout {

    private static final PorterDuffXfermode MODE_DST_OUT = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    private Bitmap mMaskBitmap;
    private Paint mPaint;
    private List<HighLight.ViewPosInfo> mViewRects;
    private HighLight mHighLight;
    private LayoutInflater mInflater;
    private boolean isCircle;
    private int maskColor = 0xCC000000;

    public HightLightView(Context context, HighLight highLight,boolean isCircle, List<HighLight.ViewPosInfo> viewRects) {
        super(context);
        mHighLight = highLight;
        mInflater = LayoutInflater.from(context);
        mViewRects = viewRects;
        this.isCircle = isCircle;
        setWillNotDraw(false);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        addViewForTip();
    }

    private void addViewForTip() {
        for (HighLight.ViewPosInfo viewPosInfo : mViewRects) {
            View view = mInflater.inflate(viewPosInfo.layoutId, this, false);
            FrameLayout.LayoutParams lp = buildTipLayoutParams(view, viewPosInfo);

            if (lp == null) continue;

            lp.leftMargin = (int) viewPosInfo.marginInfo.leftMargin;
            lp.topMargin = (int) viewPosInfo.marginInfo.topMargin;
            lp.rightMargin = (int) viewPosInfo.marginInfo.rightMargin;
            lp.bottomMargin = (int) viewPosInfo.marginInfo.bottomMargin;

            if(lp.rightMargin != 0){
                lp.gravity = Gravity.RIGHT;
            }else {
                lp.gravity = Gravity.LEFT;
            }

            if(lp.bottomMargin != 0){
                lp.gravity |= Gravity.BOTTOM;
            }else {
                lp.gravity |= Gravity.TOP;
            }
            addView(view, lp);
        }
    }

    private void buildMask() {
        mMaskBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mMaskBitmap);
        canvas.drawColor(maskColor);
        mPaint.setXfermode(MODE_DST_OUT);
        mHighLight.updateInfo();

        int radius = 70;

        for (int i = 0 ; i < mViewRects.size() ; i ++){

            HighLight.ViewPosInfo viewPosInfo = mViewRects.get(i);
            if (i >= 2){

            }else {
                if (isCircle){
                    canvas.drawCircle(viewPosInfo.rectF.centerX(),viewPosInfo.rectF.centerY()  - viewPosInfo.rectF.centerY() / 30,radius,mPaint);
                    radius = radius + 50;
                }else {
                    viewPosInfo.rectF.bottom = viewPosInfo.rectF.bottom - 30;
                    viewPosInfo.rectF.right = viewPosInfo.rectF.right - 30;
                    viewPosInfo.rectF.left = viewPosInfo.rectF.left + 30;
                    viewPosInfo.rectF.top = viewPosInfo.rectF.top + 30;
                    canvas.drawRoundRect(viewPosInfo.rectF,10, 10, mPaint);
                }
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),//
                MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY));
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            buildMask();
            updateTipPos();
        }

    }

    private void updateTipPos() {
        for (int i = 0, n = getChildCount(); i < n; i++) {
            View view = getChildAt(i);
            HighLight.ViewPosInfo viewPosInfo = mViewRects.get(i);

            LayoutParams lp = buildTipLayoutParams(view, viewPosInfo);
            if (lp == null) continue;
            view.setLayoutParams(lp);
        }
    }

    private LayoutParams buildTipLayoutParams(View view, HighLight.ViewPosInfo viewPosInfo) {
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        if (lp.leftMargin == (int) viewPosInfo.marginInfo.leftMargin &&
                lp.topMargin == (int) viewPosInfo.marginInfo.topMargin &&
                lp.rightMargin == (int) viewPosInfo.marginInfo.rightMargin &&
                lp.bottomMargin == (int) viewPosInfo.marginInfo.bottomMargin) return null;

        lp.leftMargin = (int) viewPosInfo.marginInfo.leftMargin;
        lp.topMargin = (int) viewPosInfo.marginInfo.topMargin;
        lp.rightMargin = (int) viewPosInfo.marginInfo.rightMargin;
        lp.bottomMargin = (int) viewPosInfo.marginInfo.bottomMargin;

        if(lp.rightMargin != 0){
            lp.gravity = Gravity.RIGHT;
        }else {
            lp.gravity = Gravity.LEFT;
        }

        if(lp.bottomMargin != 0){
            lp.gravity |= Gravity.BOTTOM;
        }else {
            lp.gravity |= Gravity.TOP;
        }
        return lp;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawBitmap(mMaskBitmap, 0, 0, null);
        super.onDraw(canvas);

    }
}
