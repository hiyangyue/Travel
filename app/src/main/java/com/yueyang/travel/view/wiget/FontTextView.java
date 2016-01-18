package com.yueyang.travel.view.wiget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Yang on 2016/1/18.
 */
public class FontTextView extends TextView {
    public FontTextView (Context context) {
        super(context);
        init(context);
    }

    public FontTextView (Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FontTextView (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Typeface t = Typeface.createFromAsset(context.getAssets(), "Lobster-Regular.ttf");
        this.setTypeface(t);
    }
}
