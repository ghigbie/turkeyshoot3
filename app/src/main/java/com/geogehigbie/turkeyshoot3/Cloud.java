package com.geogehigbie.turkeyshoot3;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Created by georgehigbie on 10/27/16.
 */

public class Cloud {

    private ImageView Image;
    private int Height;
    private int Width;

    private int XBegin;
    private int XEnd;
    private int YBegin;
    private int YEnd;
    private int Duration;
    private int RepeatCount;
    private int RepeatMode;
    private boolean FillAfter;


    //this constructor encapsulates all of the translate animation properties into a Cloud
    public Cloud(ImageView imageView, int cloudHeight, int cloudWidth, int xBegin, int xEnd, int yBegin, int yEnd, int duration, int repeatCount, int repeatMode, boolean fillAfter) {

        Image = imageView;
        Height = cloudHeight;
        Width = cloudWidth;
        XBegin = xBegin;
        XEnd = xEnd;
        YBegin = yBegin;
        YEnd = yEnd;
        Duration = duration;
        RepeatCount = repeatCount;
        RepeatMode = repeatMode;
        FillAfter = fillAfter;
    }

    public void translateAnimation(final ImageView imageKind){

        TranslateAnimation translateAnimation1 = new TranslateAnimation(XBegin, XEnd, YBegin, YEnd);
        translateAnimation1.setDuration(Duration);
        translateAnimation1.setRepeatCount(RepeatCount);
        translateAnimation1.setRepeatMode(RepeatMode);
        translateAnimation1.setFillAfter(FillAfter);
        translateAnimation1.setRepeatMode(Animation.RESTART);
        imageKind.startAnimation(translateAnimation1);


        }
}


