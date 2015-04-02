package com.msolo.progressbar.style;

import android.graphics.Canvas;
import android.view.View;

/**
 * Created by mSolo on 2015/3/31.
 */
public interface ITextProgressShape {

    void setCanvas(Canvas canvas);
    void setView(View view);
    void drawTextBar();

}
