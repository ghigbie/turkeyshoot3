package com.geogehigbie.turkeyshoot3;

import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Created by georgehigbie on 10/28/16.
 */

public class Cow {

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
    public Cow(ImageView imageView, int cowHeight, int cowWidth, int xBegin, int xEnd, int yBegin, int yEnd, int duration, int repeatCount, int repeatMode, boolean fillAfter) {

        fillAfter = true; //no matter what, I want the cow to stay in place and not move back to it's point of origin

        Image = imageView;
        Height = cowHeight;
        Width = cowWidth;
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
        imageKind.startAnimation(translateAnimation1);

    }


}

