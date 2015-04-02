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
public class TextHorizontalProgressBar extends TemplateTextProgressShape {

    private RectF mUnreachedRectF = new RectF(0,0,0,0);
    private RectF mReachedRectF = new RectF(0,0,0,0);

    private View mViewHolder;
    private Canvas mCanvas;

    public TextHorizontalProgressBar(Context context, TypedArray attributes) {
        super(3.0f, context, attributes);
    }

    @Override
    public int getMeasureMiniWidth() {
        return (int) mTextSize;
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
        mReachedRectF.left = mViewHolder.getPaddingLeft();
        mReachedRectF.top = mViewHolder.getHeight()/2.0f - mReachedBarSize / 2.0f;
        mReachedRectF.right = (mViewHolder.getWidth() - mViewHolder.getPaddingLeft() - mViewHolder.getPaddingRight() ) /
                (PROGRESS_BAR_VALUE * 1.0f) * mProgress + mViewHolder.getPaddingLeft();
        mReachedRectF.bottom = mViewHolder.getHeight()/2.0f + mReachedBarSize / 2.0f;

        mUnreachedRectF.left = mReachedRectF.right;
        mUnreachedRectF.right = mViewHolder.getWidth() - mViewHolder.getPaddingRight();
        mUnreachedRectF.top = mViewHolder.getHeight()/2.0f +  -mUnreachedBarSize / 2.0f;
        mUnreachedRectF.bottom = mViewHolder.getHeight()/2.0f  + mUnreachedBarSize / 2.0f;
    }

    private void calculateDrawRectF(){

        mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);

        if(mProgress == 0){
            mDrawReachedBar = false;
            mDrawTextStart = mViewHolder.getPaddingLeft();
        }else{
            mDrawReachedBar = true;
            mReachedRectF.left = mViewHolder.getPaddingLeft();
            mReachedRectF.top = mViewHolder.getHeight() / 2.0f - mReachedBarSize / 2.0f;
            mReachedRectF.right = (mViewHolder.getWidth() - mViewHolder.getPaddingLeft() - mViewHolder.getPaddingRight() ) /
                    (PROGRESS_BAR_VALUE * 1.0f) * mProgress - mOffset + mViewHolder.getPaddingLeft();
            mReachedRectF.bottom = mViewHolder.getHeight()/2.0f + mReachedBarSize / 2.0f;
            mDrawTextStart = (mReachedRectF.right + mOffset);
        }

        mDrawTextEnd =  (int) ((mViewHolder.getHeight() / 2.0f) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2.0f)) ;

        if((mDrawTextStart + mDrawTextWidth )>= mViewHolder.getWidth() - mViewHolder.getPaddingRight()){
            mDrawTextStart = mViewHolder.getWidth() - mViewHolder.getPaddingRight() - mDrawTextWidth;
            mReachedRectF.right = mDrawTextStart - mOffset;
        }

        float unreachedBarStart = mDrawTextStart + mDrawTextWidth + mOffset;
        if(unreachedBarStart >= mViewHolder.getWidth() - mViewHolder.getPaddingRight()){
            mDrawUnreachedBar = false;
        }else{
            mDrawUnreachedBar = true;
            mUnreachedRectF.left = unreachedBarStart;
            mUnreachedRectF.right = mViewHolder.getWidth() - mViewHolder.getPaddingRight();
            mUnreachedRectF.top = mViewHolder.getHeight()/2.0f +  -mUnreachedBarSize / 2.0f;
            mUnreachedRectF.bottom = mViewHolder.getHeight()/2.0f  + mUnreachedBarSize / 2.0f;
        }
    }

}
