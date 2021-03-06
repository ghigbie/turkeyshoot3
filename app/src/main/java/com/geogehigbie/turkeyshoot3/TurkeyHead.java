package com.geogehigbie.turkeyshoot3;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * Created by georgehigbie on 10/22/16.
 */

public class TurkeyHead {

    private ImageView Image;
    private int TurkeyHeight;
    private int TurkeyWidth;
    private int XBegin;
    private int XEnd;
    private int YBegin;
    private int YEnd;
    private int Duration;
    private int RepeatCount;
    private int RepeatMode;
    private boolean FillAfter;

    private int AlphaBegin;
    private int AlphaEnd;
    private int AlphaDuration;


    //this constructor encapsulates all of the translate animation properties into a TurkeyBig
    public TurkeyHead(ImageView imageView, int turkeyHeight, int turkeyWidth, int xBegin, int xEnd, int yBegin, int yEnd, int duration, int repeatCount, int repeatMode, boolean fillAfter) {

        Image = imageView;
        TurkeyHeight = turkeyHeight;
        TurkeyWidth = turkeyWidth;
        XBegin = xBegin;
        XEnd = xEnd;
        YBegin = yBegin;
        YEnd = yEnd;
        Duration = duration;
        RepeatCount = repeatCount;
        RepeatMode = repeatMode;
        FillAfter = fillAfter;

    }


    public TurkeyHead() {

    }


    public void translateAninimation(final ImageView imageKind) {

        TranslateAnimation animation1 = new TranslateAnimation(XBegin, XEnd, YBegin, YEnd);
        animation1.setDuration(Duration);
        animation1.setRepeatCount(RepeatCount);
        animation1.setRepeatMode(RepeatMode);
        animation1.setFillAfter(FillAfter);
        animation1.setRepeatMode(Animation.REVERSE);
        imageKind.startAnimation(animation1);


        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //TranslateAnimation animation2 = new TranslateAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void alphaAnimation(){

    }

    public void valueTranslateAnimation(){

        ValueAnimator valueAnimator = ValueAnimator.ofInt(YBegin, YEnd);
        valueAnimator.setDuration(Duration);
        valueAnimator.setRepeatCount(RepeatCount);
        valueAnimator.setRepeatMode(RepeatMode);

    }

    public void objectAnimationMotionY(ImageView imageView){

        ObjectAnimator animX = ObjectAnimator.ofFloat(imageView, "x", 0f);
        ObjectAnimator animY = ObjectAnimator.ofFloat(imageView, "y", YEnd);
        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(animX, animY);
        animSetXY.setDuration(Duration);
        animSetXY.start();
    }

}
