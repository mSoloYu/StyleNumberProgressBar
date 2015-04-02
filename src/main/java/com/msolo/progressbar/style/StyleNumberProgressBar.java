package com.msolo.progressbar.style;

/**
 * Created by mSolo on 2015/3/31.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

public class StyleNumberProgressBar extends View {

    /**
     * for save and restore instance of progressbar.
     */
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height";
    private static final String INSTANCE_REACHED_BAR_COLOR = "reached_bar_color";
    private static final String INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height";
    private static final String INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_DRAWER = "drawer";

    private int mProgressShape = TemplateTextProgressShape.PROGRESS_SHAPE_HORIZONTAL_DEFAULT;
    private TemplateTextProgressShape mTemplateTextProgressShape;

    public enum ProgressTextVisibility{
        Visible,Invisible
    };

    public StyleNumberProgressBar(Context context) {
        this(context, null);
    }

    public StyleNumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StyleNumberProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NumberProgressBar,
                defStyleAttr, 0);

        mProgressShape = attributes.getInt(R.styleable.NumberProgressBar_progress_shape, TemplateTextProgressShape.PROGRESS_SHAPE_HORIZONTAL_DEFAULT);
        if (mProgressShape == TemplateTextProgressShape.PROGRESS_SHAPE_VERTICAL_DEFAULT) {
            mTemplateTextProgressShape = new TextVerticalProgressBar(context, attributes);
        } else if (mProgressShape == TemplateTextProgressShape.PROGRESS_SHAPE_HORIZONTAL_DEFAULT) {
            mTemplateTextProgressShape = new TextHorizontalProgressBar(context, attributes);
        } else if (mProgressShape == TemplateTextProgressShape.PROGRESS_SHAPE_HORIZONTAL_TIMELINE) {
            mTemplateTextProgressShape = new TextHoriTimelineProgressBar(context, attributes);
        } else {
            mTemplateTextProgressShape = new TextPieProgressBar(context, attributes);
        }

        attributes.recycle();

    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) mTemplateTextProgressShape.getMeasureMiniWidth();
    }

    @Override
    protected int getSuggestedMinimumHeight() {

        if (mProgressShape == TemplateTextProgressShape.PROGRESS_SHAPE_HORIZONTAL_TIMELINE) {

            int result = (int) mTemplateTextProgressShape.getmTextSize() * 4 +
                    Math.max((int) mTemplateTextProgressShape.getmReachedBarSize(),(int) mTemplateTextProgressShape.getmUnreachedBarSize());
            return result;
        }

        return Math.max(getSuggestedMinimumWidth(),
                Math.max((int) mTemplateTextProgressShape.getmReachedBarSize(),(int) mTemplateTextProgressShape.getmUnreachedBarSize()));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec,true), measure(heightMeasureSpec,false));
    }

    private int measure(int measureSpec,boolean isWidth){

        if (mProgressShape == TemplateTextProgressShape.PROGRESS_SHAPE_PIE_DEFAULT) {
            return getSuggestedMinimumWidth();
        }

        if (mProgressShape == TemplateTextProgressShape.PROGRESS_SHAPE_HORIZONTAL_TIMELINE && !isWidth) {
            return getSuggestedMinimumHeight();
        }

        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth?getPaddingLeft()+getPaddingRight():getPaddingTop()+getPaddingBottom();
        if(mode == MeasureSpec.EXACTLY){
            if (mProgressShape == TemplateTextProgressShape.PROGRESS_SHAPE_VERTICAL_DEFAULT ||
                mProgressShape == TemplateTextProgressShape.PROGRESS_SHAPE_PIE_DEFAULT) {
                result = getSuggestedMinimumWidth() > size ? getSuggestedMinimumWidth() : size;
            } else {
                result = size;
            }
        }else{
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if(mode == MeasureSpec.AT_MOST){
                if(isWidth) {
                    result = Math.max(result, size);
                } else{
                    if (mProgressShape == TemplateTextProgressShape.PROGRESS_SHAPE_VERTICAL_DEFAULT ||
                        mProgressShape == TemplateTextProgressShape.PROGRESS_SHAPE_PIE_DEFAULT) {
                        result = Math.max(result, size);
                    } else {
                        result = Math.min(result, size);
                    }
                }
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mTemplateTextProgressShape.setView(this);
        mTemplateTextProgressShape.setCanvas(canvas);
        mTemplateTextProgressShape.drawTextBar();
    }

    public void setText(String text) {
        mTemplateTextProgressShape.setmCurrentDrawText(text);
        invalidate();
    }

    public int getTextColor() {
        return mTemplateTextProgressShape.getmTextColor();
    }

    public float getProgressTextSize() {
        return mTemplateTextProgressShape.getmTextSize();
    }

    public int getUnreachedBarColor() {
        return mTemplateTextProgressShape.getmUnreachedBarColor();
    }

    public int getReachedBarColor() {
        return mTemplateTextProgressShape.getmReachedBarColor();
    }

    public int getProgress() {
        return mTemplateTextProgressShape.getmProgress();
    }

    public float getReachedBarHeight(){
        return mTemplateTextProgressShape.getmReachedBarSize();
    }

    public float getUnreachedBarHeight(){
        return mTemplateTextProgressShape.getmUnreachedBarSize();
    }

    public void setProgressTextSize(float TextSize) {
        mTemplateTextProgressShape.setmTextSize(TextSize);
        invalidate();
    }

    public void setProgressTextColor(int TextColor) {
        mTemplateTextProgressShape.setmTextColor(TextColor);
        invalidate();
    }

    public void setUnreachedBarColor(int BarColor) {
        mTemplateTextProgressShape.setmUnreachedBarColor(BarColor);
        invalidate();
    }

    public void setReachedBarColor(int ProgressColor) {
        mTemplateTextProgressShape.setmReachedBarColor(ProgressColor);
        invalidate();
    }

    public void incrementProgressBy(int by){
        if(by > 0){
            setProgress(getProgress() + by);
        }
    }

    public void setProgress(int progress) {
        mTemplateTextProgressShape.setProgress(progress);
        invalidate();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getProgressTextSize());
        bundle.putFloat(INSTANCE_REACHED_BAR_HEIGHT,getReachedBarHeight());
        bundle.putFloat(INSTANCE_UNREACHED_BAR_HEIGHT, getUnreachedBarHeight());
        bundle.putInt(INSTANCE_REACHED_BAR_COLOR,getReachedBarColor());
        bundle.putInt(INSTANCE_UNREACHED_BAR_COLOR,getUnreachedBarColor());
        bundle.putInt(INSTANCE_PROGRESS, getProgress());
        bundle.putSerializable(INSTANCE_DRAWER, mTemplateTextProgressShape);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof Bundle){

            final Bundle bundle = (Bundle)state;
            mTemplateTextProgressShape = (TextHorizontalProgressBar) bundle.getSerializable(INSTANCE_DRAWER);
            mTemplateTextProgressShape.setmTextColor( bundle.getInt(INSTANCE_TEXT_COLOR) );
            mTemplateTextProgressShape.setmTextSize(bundle.getFloat(INSTANCE_TEXT_SIZE));
            mTemplateTextProgressShape.setmReachedBarSize(bundle.getFloat(INSTANCE_REACHED_BAR_HEIGHT));
            mTemplateTextProgressShape.setmUnreachedBarSize(bundle.getFloat(INSTANCE_UNREACHED_BAR_HEIGHT));
            mTemplateTextProgressShape.setmReachedBarColor(bundle.getInt(INSTANCE_REACHED_BAR_COLOR));
            mTemplateTextProgressShape.setmUnreachedBarColor(bundle.getInt(INSTANCE_UNREACHED_BAR_COLOR));
            mTemplateTextProgressShape.setmProgress(bundle.getInt(INSTANCE_PROGRESS));
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

}
