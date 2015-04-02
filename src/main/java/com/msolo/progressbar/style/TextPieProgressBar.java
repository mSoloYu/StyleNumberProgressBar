package com.msolo.progressbar.style;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by mSolo on 2015/4/1.
 */
public class TextPieProgressBar extends TemplateTextProgressShape {

    private static final int PROGRESS_CIRCLE_VALUE = 360;

    private RectF mUnreachedRectF = new RectF(0,0,0,0);
    private RectF mReachedRectF = new RectF(0,0,0,0);

    private View mViewHolder;
    private Canvas mCanvas;

    public TextPieProgressBar(Context context, TypedArray attributes) {
        super(2.0f, context, attributes);
    }

    @Override
    public int getMeasureMiniWidth() {
        return (int)(mReachedBarSize + mUnreachedBarSize + mOffset);
    }

    @Override
    public void setCanvas(Canvas canvas) {
        mCanvas = canvas;
    }

    @Override
    public void setView(View view) {
        mViewHolder = view;
    }

    @Override
    public void drawTextBar() {

        float reachedSweepAngle = mProgress*1.0f / PROGRESS_BAR_VALUE * PROGRESS_CIRCLE_VALUE;
        float unReachedSweepAngle = PROGRESS_CIRCLE_VALUE - reachedSweepAngle;

        mReachedRectF.top = mViewHolder.getPaddingTop();
        mReachedRectF.bottom = mViewHolder.getHeight() - mViewHolder.getPaddingBottom();
        mReachedRectF.left = mViewHolder.getPaddingLeft();
        mReachedRectF.right = mViewHolder.getWidth() - mViewHolder.getPaddingRight();

        mUnreachedRectF.top = mReachedRectF.top;
        mUnreachedRectF.bottom = mReachedRectF.bottom;
        mUnreachedRectF.left = mReachedRectF.left;
        mUnreachedRectF.right = mReachedRectF.right;

        mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);
        mDrawTextStart = mViewHolder.getWidth() / 2.0f - mViewHolder.getPaddingLeft() - mDrawTextWidth / 2.0f;
        mDrawTextEnd = mViewHolder.getHeight() / 2.0f + mTextSize/2.0f + mOffset;

        mCanvas.drawArc(mReachedRectF, 0, reachedSweepAngle, true, mReachedBarPaint);
        mCanvas.drawArc(mUnreachedRectF, reachedSweepAngle,unReachedSweepAngle, true, mUnreachedBarPaint);

        mCanvas.drawText(mCurrentDrawText, mDrawTextStart, mDrawTextEnd, mTextPaint);

    }



}
