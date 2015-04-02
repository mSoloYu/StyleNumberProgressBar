package com.msolo.progressbar.style;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by mSolo on 2015/3/31.
 */
public class TextHoriTimelineProgressBar extends TemplateTextProgressShape {

    private RectF mUnreachedRectF = new RectF(0,0,0,0);
    private RectF mReachedRectF = new RectF(0,0,0,0);

    private View mViewHolder;
    private Canvas mCanvas;

    public TextHoriTimelineProgressBar(Context context, TypedArray attributes) {
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

        calculateDrawRectF();

        mCanvas.drawRect(mReachedRectF,mReachedBarPaint);
        mCanvas.drawRect(mUnreachedRectF, mUnreachedBarPaint);

        mDrawTextStart = mReachedRectF.right - mDrawTextWidth / 2.0f;
        mDrawTextEnd = mViewHolder.getPaddingTop() + mTextSize * 1.5f;
        mCanvas.drawText(mCurrentDrawText, mDrawTextStart, mDrawTextEnd, mTimelineTextPaint);
        float drawTextTimelineStartX = mDrawTextStart + mDrawTextWidth / 2.0f;
        float drawTextTimelineStartY = mReachedRectF.top - mOffset;
        float drawTextTimelineEndY = mReachedRectF.top;
        mCanvas.drawLine(drawTextTimelineStartX, drawTextTimelineStartY, drawTextTimelineStartX, drawTextTimelineEndY, mLinePaint);

        float timelineText1Start = mReachedRectF.left;
        float timelineText1End = mReachedRectF.bottom +  mTextSize * 1.2f;
        mCanvas.drawText(mTimelineTextArray[0], timelineText1Start, timelineText1End, mTimelineTextPaint);
        float timeline1StartX = timelineText1Start;
        float timeline1StartY = mReachedRectF.bottom;
        float timelien1EndY = timeline1StartY + mOffset;
        mCanvas.drawLine(timeline1StartX, timeline1StartY, timeline1StartX+mLinePaint.getStrokeWidth()/2.0f, timelien1EndY, mLinePaint);

        float timelineText2Start = mReachedRectF.left + mViewHolder.getWidth()/2 - mDrawTextWidth/2;
        mCanvas.drawText(mTimelineTextArray[1], timelineText2Start, timelineText1End, mTimelineTextPaint);
        float timeline2Start = timelineText2Start + mDrawTextWidth/2;
        mCanvas.drawLine(timeline2Start, timeline1StartY, timeline2Start, timelien1EndY, mLinePaint);

        float timelineText3Start = mViewHolder.getWidth() - mViewHolder.getPaddingRight() - mDrawTextWidth;
        mCanvas.drawText(mTimelineTextArray[2], timelineText3Start, timelineText1End, mTimelineTextPaint);
        float timeline3Start = mUnreachedRectF.right - mLinePaint.getStrokeWidth()/2.0f;
        mCanvas.drawLine(timeline3Start, timeline1StartY, timeline3Start, timelien1EndY, mLinePaint);

    }

    private void calculateDrawRectF(){

        mDrawTextWidth = mTimelineTextPaint.measureText(mCurrentDrawText);

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

}
