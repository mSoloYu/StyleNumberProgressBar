package com.msolo.progressbar.style;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;

import java.io.Serializable;

/**
 * Created by mSolo on 2015/3/31.
 */
public abstract class TemplateTextProgressShape implements Serializable, ITextProgressShape {

    protected static final int PROGRESS_BAR_VALUE = 100;

    public static final int PROGRESS_SHAPE_HORIZONTAL_DEFAULT = 0;
    public static final int PROGRESS_SHAPE_VERTICAL_DEFAULT = 1;
    public static final int PROGRESS_SHAPE_HORIZONTAL_TIMELINE = 2;
    public static final int PROGRESS_SHAPE_PIE_DEFAULT = 4;

    protected final int default_progress_shape = 0;
    private final int default_text_color = Color.rgb(66, 145, 241);
    private final int default_reached_color = Color.rgb(66,145,241);
    private final int default_unreached_color = Color.rgb(204, 204, 204);
    private float default_text_size;
    private float default_reached_bar_size;
    private float default_unreached_bar_size;

    protected float default_progress_text_offset;
    protected int mProgressShape = 0;
    protected int mProgress = 0;
    protected int mReachedBarColor;
    protected int mUnreachedBarColor;
    protected int mTextColor;

    protected float mTextSize;
    protected float mReachedBarSize; // height or width
    protected float mUnreachedBarSize;

    protected String mCurrentDrawText;

    protected float mDrawTextWidth;
    protected float mDrawTextStart;
    protected float mDrawTextEnd;

    protected String[] mTimelineTextArray;

    protected float mOffset;

    protected boolean mDrawUnreachedBar = true;
    protected boolean mDrawReachedBar = true;

    protected Paint mReachedBarPaint;
    protected Paint mUnreachedBarPaint;
    protected Paint mLinePaint;
    protected TextPaint mTextPaint;
    protected TextPaint mTimelineTextPaint;

    private Context mContext;

    protected TemplateTextProgressShape() {}

    protected TemplateTextProgressShape(float offsetVal, Context context, TypedArray attributes) {

        mContext = context;

        default_reached_bar_size = dp2px(1.5f);
        default_unreached_bar_size = dp2px(1.0f);
        default_text_size = sp2px(10);
        default_progress_text_offset = dp2px(offsetVal);

        mReachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_progress_reached_color, default_reached_color);
        mUnreachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_progress_unreached_color,default_unreached_color);
        mTextColor = attributes.getColor(R.styleable.NumberProgressBar_progress_text_color,default_text_color);
        mTextSize = attributes.getDimension(R.styleable.NumberProgressBar_progress_text_size, default_text_size);

        mReachedBarSize = attributes.getDimension(R.styleable.NumberProgressBar_progress_reached_bar_size, default_reached_bar_size);
        mUnreachedBarSize = attributes.getDimension(R.styleable.NumberProgressBar_progress_unreached_bar_size, default_unreached_bar_size);
        mOffset = attributes.getDimension(R.styleable.NumberProgressBar_progress_text_offset,default_progress_text_offset);

        setProgress(attributes.getInt(R.styleable.NumberProgressBar_progress, 0));
        mCurrentDrawText = attributes.getString(R.styleable.NumberProgressBar_progress_text);

        mProgressShape = attributes.getInt(R.styleable.NumberProgressBar_progress_shape, TemplateTextProgressShape.PROGRESS_SHAPE_HORIZONTAL_DEFAULT);
        if (mProgressShape == PROGRESS_SHAPE_HORIZONTAL_TIMELINE) {
            mTimelineTextArray = attributes.getString(R.styleable.NumberProgressBar_progress_timeline).split(",");
        }

        initializePainters();

    }

    public abstract int getMeasureMiniWidth();

    public void setProgress(int progress) {
        if(progress <= 100  && progress >= 0){
            mProgress = progress;
        }
    }

    private void initializePainters(){
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(mReachedBarColor);

        mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedBarPaint.setColor(mUnreachedBarColor);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mTextColor);
        mLinePaint.setStrokeWidth(mOffset/2.0f);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        mTimelineTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTimelineTextPaint.setColor(mTextColor);
        mTimelineTextPaint.setTextSize(mTextSize * 3 / 5);
    }

    public int getmProgressShape() {
        return mProgressShape;
    }

    public void setmProgressShape(int mProgressShape) {
        this.mProgressShape = mProgressShape;
    }

    public int getmProgress() {
        return mProgress;
    }

    public void setmProgress(int mProgress) {
        this.mProgress = mProgress;
    }

    public int getmReachedBarColor() {
        return mReachedBarColor;
    }

    public void setmReachedBarColor(int mReachedBarColor) {
        this.mReachedBarColor = mReachedBarColor;
        mReachedBarPaint.setColor(mReachedBarColor);
    }

    public int getmUnreachedBarColor() {
        return mUnreachedBarColor;
    }

    public void setmUnreachedBarColor(int mUnreachedBarColor) {
        this.mUnreachedBarColor = mUnreachedBarColor;
        mUnreachedBarPaint.setColor(mReachedBarColor);
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        mTextPaint.setColor(mTextColor);
    }

    public float getmTextSize() {
        return mTextSize;
    }

    public void setmTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
        mTextPaint.setTextSize(mTextSize);
    }

    public float getmReachedBarSize() {
        return mReachedBarSize;
    }

    public void setmReachedBarSize(float mReachedBarSize) {
        this.mReachedBarSize = mReachedBarSize;
    }

    public float getmUnreachedBarSize() {
        return mUnreachedBarSize;
    }

    public void setmUnreachedBarSize(float mUnreachedBarSize) {
        this.mUnreachedBarSize = mUnreachedBarSize;
    }

    public String getmCurrentDrawText() {
        return mCurrentDrawText;
    }

    public void setmCurrentDrawText(String mCurrentDrawText) {
        this.mCurrentDrawText = mCurrentDrawText;
    }

    public float getmDrawTextWidth() {
        return mDrawTextWidth;
    }

    public void setmDrawTextWidth(float mDrawTextWidth) {
        this.mDrawTextWidth = mDrawTextWidth;
    }

    public float getmDrawTextStart() {
        return mDrawTextStart;
    }

    public void setmDrawTextStart(float mDrawTextStart) {
        this.mDrawTextStart = mDrawTextStart;
    }

    public float getmDrawTextEnd() {
        return mDrawTextEnd;
    }

    public void setmDrawTextEnd(float mDrawTextEnd) {
        this.mDrawTextEnd = mDrawTextEnd;
    }

    public float getmOffset() {
        return mOffset;
    }

    public void setmOffset(float mOffset) {
        this.mOffset = mOffset;
    }

    public boolean ismDrawUnreachedBar() {
        return mDrawUnreachedBar;
    }

    public void setmDrawUnreachedBar(boolean mDrawUnreachedBar) {
        this.mDrawUnreachedBar = mDrawUnreachedBar;
    }

    public boolean ismDrawReachedBar() {
        return mDrawReachedBar;
    }

    public void setmDrawReachedBar(boolean mDrawReachedBar) {
        this.mDrawReachedBar = mDrawReachedBar;
    }

    public float dp2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return  dp * scale + 0.5f;
    }

    public float sp2px(float sp){
        final float scale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

}
