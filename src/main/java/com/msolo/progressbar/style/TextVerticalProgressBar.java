package com.msolo.progressbar.style;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by mSolo on 2015/3/31.
 */
public class TextVerticalProgressBar extends TemplateTextProgressShape {

    private RectF mUnreachedRectF = new RectF(0,0,0,0);
    private RectF mReachedRectF = new RectF(0,0,0,0);

    private View mViewHolder;
    private Canvas mCanvas;

    public TextVerticalProgressBar(Context context, TypedArray attributes) {
        super(2.0f, context, attributes);
    }

    @Override
    public int getMeasureMiniWidth() {
        return  (int) mTextPaint.measureText(mCurrentDrawText);
    }

    @Override
    public void setCanvas(Canvas canvas) {
        if (mCanvas == null) {
            mCanvas = canvas;
        }
    }

    @Override
    public void setView(View view) {
        if (mViewHolder == null) {
            mViewHolder = view;
        }
    }

    @Override
    public void drawTextBar() {

        if(!TextUtils.isEmpty(mCurrentDrawText)){
            calculateDrawRectF();
        }else{
            calculateDrawRectFWithoutProgressText();
        }

        if(mDrawReachedBar){
            mCanvas.drawRect(mReachedRectF,mReachedBarPaint);
        }

        if(mDrawUnreachedBar) {
            mCanvas.drawRect(mUnreachedRectF, mUnreachedBarPaint);
        }

        if(!TextUtils.isEmpty(mCurrentDrawText)){
            mCanvas.drawText(mCurrentDrawText, mDrawTextStart, mDrawTextEnd, mTextPaint);
        }

    }

    private void calculateDrawRectFWithoutProgressText(){

        mUnreachedRectF.left = mViewHolder.getWidth()/2.0f - mUnreachedBarSize/2.0f;
        mUnreachedRectF.right = mViewHolder.getWidth()/2.0f + mUnreachedBarSize/2.0f;
        mUnreachedRectF.top = mViewHolder.getHeight()  -  mViewHolder.getPaddingTop();
        mUnreachedRectF.bottom = mUnreachedRectF.top  + mUnreachedBarSize;

        mReachedRectF.left = mViewHolder.getWidth()/2.0f - mReachedBarSize/2.0f;;
        mReachedRectF.right = mViewHolder.getWidth()/2.0f + mReachedBarSize/2.0f;
        mReachedRectF.top = mUnreachedRectF.bottom;
        mReachedRectF.bottom = mReachedRectF.top + mReachedBarSize;

    }

    private void calculateDrawRectF(){

        mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);

        mDrawReachedBar = true;
        if(mProgress == 0){
            mDrawReachedBar = false;
        }

        mUnreachedRectF.left = mViewHolder.getWidth()/2.0f - mUnreachedBarSize/2.0f;
        mUnreachedRectF.right = mViewHolder.getWidth()/2.0f + mUnreachedBarSize/2.0f;
        mUnreachedRectF.top = mViewHolder.getPaddingTop();
        mUnreachedRectF.bottom = (mViewHolder.getHeight() - mTextSize - 2*mOffset)/ (PROGRESS_BAR_VALUE * 1.0f) * (PROGRESS_BAR_VALUE - mProgress);

        mDrawTextStart = 0;
        mDrawTextEnd =  mUnreachedRectF.bottom + mTextSize + mOffset;

        float reachedBarStart = mDrawTextEnd + mOffset;
        if(mDrawTextEnd == 0 || mProgress == 100){
            mDrawUnreachedBar = false;
        }else{
            mDrawUnreachedBar = true;
        }

        mReachedRectF.left = mViewHolder.getWidth()/2.0f - mReachedBarSize/2.0f;
        mReachedRectF.right = mViewHolder.getWidth()/2.0f + mReachedBarSize/2.0f;
        mReachedRectF.top = reachedBarStart;
        mReachedRectF.bottom = mViewHolder.getHeight() - mViewHolder.getPaddingBottom();

    }

}
