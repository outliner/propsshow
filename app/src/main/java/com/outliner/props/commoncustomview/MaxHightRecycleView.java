package com.outliner.props.commoncustomview;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2017/3/6.
 */

public class MaxHightRecycleView extends RecyclerView {
    private final int maxHight = 700;
    public MaxHightRecycleView(Context context) {
        super(context);
    }

    public MaxHightRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int hmode = MeasureSpec.getMode(heightMeasureSpec);
        int hspec = MeasureSpec.getSize(heightMeasureSpec);
        if (hspec > maxHight) {
            hspec = maxHight;
        }
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(hspec, hmode));
    }
}
