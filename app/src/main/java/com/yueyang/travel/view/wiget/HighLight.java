package com.yueyang.travel.view.wiget;

/**
 * Created by Yang on 2016/3/18.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhy on 15/10/8.
 */
public class HighLight
{
    public static class ViewPosInfo
    {
        public int layoutId = -1;
        public RectF rectF;
        public MarginInfo marginInfo;
        public View view;
        public int x;
        public int y;
        public int radius;
        public OnPosCallback onPosCallback;
    }

    public static class MarginInfo
    {
        public float topMargin;
        public float leftMargin;
        public float rightMargin;
        public float bottomMargin;

    }

    public static interface OnPosCallback
    {
        void getPos(float rightMargin, float bottomMargin, RectF rectF, MarginInfo marginInfo);
    }

    public static interface OnClickCallback
    {
        void onClick();
    }


    private View mAnchor;
    private List<ViewPosInfo> mViewRects;
    private Context mContext;
    private HightLightView mHightLightView;
    private OnClickCallback clickCallback;

    private boolean intercept = true;
    private boolean isCircle;
    private int maskColor = 0xCC000000;
    private int circleX;
    private int circleY;
    private int radius;

    private int[][] location;

    public HighLight(Context context,boolean isCircle)
    {
        mContext = context;
        mViewRects = new ArrayList<>();
        this.isCircle = isCircle;
        mAnchor = ((Activity) mContext).findViewById(android.R.id.content);
    }

    public HighLight(Context context,boolean isCircle,int circleX,int circleY,int radius)
    {
        mContext = context;
        mViewRects = new ArrayList<>();
        this.isCircle = isCircle;
        this.circleX = circleX;
        this.circleY = circleY;
        this.radius = radius;
        mAnchor = ((Activity) mContext).findViewById(android.R.id.content);
    }

    public HighLight setLocation(int x,int y,int radius){
        circleX = x;
        circleY = y;
        this.radius = radius;
        return this;
    }

    public HighLight setLocation(int[][] location){
        this.location = location;
        return this;
    }


    public HighLight anchor(View anchor)
    {
        mAnchor = anchor;
        return this;
    }

    public HighLight intercept(boolean intercept)
    {
        this.intercept = intercept;
        return this;
    }

    public HighLight maskColor(int maskColor)
    {
        this.maskColor = maskColor;
        return this;
    }


    public HighLight addHighLight(int viewId, int decorLayoutId, OnPosCallback onPosCallback)
    {
        ViewGroup parent = (ViewGroup) mAnchor;
        View view = parent.findViewById(viewId);
        addHighLight(view, decorLayoutId, onPosCallback);
        return this;
    }

    public void updateInfo()
    {
        ViewGroup parent = (ViewGroup) mAnchor;
        for (HighLight.ViewPosInfo viewPosInfo : mViewRects)
        {

            RectF rect = new RectF(ViewUtils.getLocationInView(parent, viewPosInfo.view));
            {
                viewPosInfo.rectF = rect;
                viewPosInfo.onPosCallback.getPos(parent.getWidth() - rect.right, parent.getHeight() - rect.bottom, rect, viewPosInfo.marginInfo);
            }
        }

    }


    public HighLight addHighLight(View view, int decorLayoutId, OnPosCallback onPosCallback)
    {
        ViewGroup parent = (ViewGroup) mAnchor;
        RectF rect = new RectF(ViewUtils.getLocationInView(parent, view));
        ViewPosInfo viewPosInfo = new ViewPosInfo();
        viewPosInfo.layoutId = decorLayoutId;
        viewPosInfo.rectF = rect;
        viewPosInfo.view = view;
        if (onPosCallback == null && decorLayoutId != -1)
        {
            throw new IllegalArgumentException("onPosCallback can not be null.");
        }
        MarginInfo marginInfo = new MarginInfo();
        onPosCallback.getPos(parent.getWidth() - rect.right, parent.getHeight() - rect.bottom, rect, marginInfo);
        viewPosInfo.marginInfo = marginInfo;
        viewPosInfo.onPosCallback = onPosCallback;
        mViewRects.add(viewPosInfo);

        return this;
    }

    // 一个场景可能有多个步骤的高亮。一个步骤完成之后再进行下一个步骤的高亮
    // 添加点击事件，将每次点击传给应用逻辑
    public HighLight setClickCallback(OnClickCallback clickCallback){
        this.clickCallback = clickCallback;
        return this;
    }


    public void show()
    {

        if (mHightLightView != null) return;

//        HightLightView hightLightView = new HightLightView(mContext, this,isCircle,mViewRects,circleX,circleY,radius);
        HightLightView hightLightView = new HightLightView(mContext, this,isCircle,mViewRects);
        if (mAnchor.getClass().getSimpleName().equals("FrameLayout"))
        {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) mAnchor).addView(hightLightView, ((ViewGroup) mAnchor).getChildCount(), lp);

        } else
        {
            FrameLayout frameLayout = new FrameLayout(mContext);
            ViewGroup parent = (ViewGroup) mAnchor.getParent();
            parent.removeView(mAnchor);
            parent.addView(frameLayout, mAnchor.getLayoutParams());
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            frameLayout.addView(mAnchor, lp);

            frameLayout.addView(hightLightView);
        }

        if (intercept)
        {
            hightLightView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    remove();
                    if(clickCallback != null){
                        clickCallback.onClick();
                    }
                }
            });
        }

        mHightLightView = hightLightView;
    }

    public void remove()
    {
        if (mHightLightView == null) return;
        ViewGroup parent = (ViewGroup) mHightLightView.getParent();
        if (parent instanceof RelativeLayout || parent instanceof FrameLayout)
        {
            parent.removeView(mHightLightView);
        } else
        {
            parent.removeView(mHightLightView);
            View origin = parent.getChildAt(0);
            ViewGroup graParent = (ViewGroup) parent.getParent();
            graParent.removeView(parent);
            graParent.addView(origin, parent.getLayoutParams());
        }
        mHightLightView = null;
    }


}
