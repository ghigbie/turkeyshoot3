package com.geogehigbie.turkeyshoot3;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity  {

    //level increase variables
    int level = 2;
    int durationLevelUp = 200 * level;


    public int points = 0;

    private int touchXLocation;
    private int touchYLocation;

    private int recursionCount = 0;
    private int numberOfHits = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // initialAnimation();

       // animateTurkeyHeads();


    }

    public void clickToPlay(View view){
        initialAnimation();
        animateTurkeyHeads();
        playInitialGobble();
        cloudMotion();
       // cowMotion();

    }

    // plays when the screen is open
    public void initialAnimation(){
        ImageView turkeybody = (ImageView) findViewById(R.id.big_turkey_body);
        TurkeyBig turkeyBig1 = new TurkeyBig(turkeybody, 2000, 2000, 0, -2000, 0, 1700, 3000, 0, 0, true);
        turkeyBig1.translateAninimation(turkeybody);

        playInitialGobble();

        buttonAnimation();


    }

    //button fades away when pressed
    public void buttonAnimation(){
        final Button button = (Button) findViewById(R.id.start_button);
        Animation alphaAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_animation_long);
        button.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                button.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void levelUp(){
        level = level++;
        durationLevelUp = 200 * level;
    }






    public void animateTurkeyHeads(){

        //these values control turkey head behavior
        int numberOfTurkeys = 4;
        int turkeyHeight = 100;
        int turkeyWidth = 100;
        int baseStartValueX = 0;
        int baseEndValueX = 0;
        final int baseStartValueY = 800;
        int baseEndValueY = 90;
        int baseDuration = 1500;
        int repeatCount = 10;
        int repeatMode = 10;
        boolean fillAfter = true;

        int startValue1 = baseStartValueY;
        int startValue2 = baseStartValueY;
        int startValue3 = baseStartValueY;
        int startValue4 = baseStartValueY;

        int endValue1 = baseEndValueY;
        int endValue2 = baseEndValueY;
        int endValue3 = baseEndValueY;
        int endValue4 = baseEndValueY;

        int duration1 = baseDuration;
        int duration2 = baseDuration;
        int duration3 = baseDuration;
        int duration4 = baseDuration;

        final ImageView turkeyHead1 = (ImageView) findViewById(R.id.turkey_head1);
        final ImageView turkeyHead2 = (ImageView) findViewById(R.id.turkey_head2);
        final ImageView turkeyHead3 = (ImageView) findViewById(R.id.turkey_head3);
        final ImageView turkeyHead4 = (ImageView) findViewById(R.id.turkey_head4);

        int[] startValueArray = {startValue1, startValue2, startValue3, startValue4};
        int[] endValueArray = {endValue1, endValue2, endValue3, endValue4};
        int[] durationArray = {duration1, duration2, duration3, duration4};

        final ImageView [] turkeyHeadImageArray = {turkeyHead1, turkeyHead2, turkeyHead3, turkeyHead4};


        for(int a = 0; a < numberOfTurkeys; a++){
            Random random1 = new Random();
            int randomDecreaseValue = random1.nextInt(200);
            startValueArray[a] = startValueArray[a] - randomDecreaseValue;

            Random random2 = new Random();
            int randomIncreaseValue = random2.nextInt(150);
            endValueArray[a] = endValueArray[a] + randomIncreaseValue;

            Random random3 = new Random();
            int randomDecreaseDurationValue = random3.nextInt(500);
            durationArray[a] = durationArray[a] - randomDecreaseDurationValue - durationLevelUp;

//            property animation - does not do as much
//            turkeyHeadImageArray[a].animate()
//                    .translationX(0)
//                    .translationY(endValueArray[a])
//                    .setDuration(durationArray[a]);

            final ObjectAnimator yAnimTurkeyHead = ObjectAnimator.ofFloat(turkeyHeadImageArray[a], "y", startValueArray[a],
                    endValueArray[a]);
            yAnimTurkeyHead.setDuration(durationArray[a]);
            yAnimTurkeyHead.setRepeatCount(20);
            yAnimTurkeyHead.setRepeatMode(ValueAnimator.REVERSE);
            yAnimTurkeyHead.start();

            yAnimTurkeyHead.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
//                    turkeyHead1.setVisibility(View.VISIBLE);
//                    turkeyHead2.setVisibility(View.VISIBLE);
//                    turkeyHead3.setVisibility(View.VISIBLE);
//                    turkeyHead4.setVisibility(View.VISIBLE);

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    turkeyHead1.animate().translationY(baseStartValueY);
                    turkeyHead2.animate().translationY(baseStartValueY);
                    turkeyHead3.animate().translationY(baseStartValueY);
                    turkeyHead4.animate().translationY(baseStartValueY);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    turkeyHead1.setVisibility(View.VISIBLE);
                    turkeyHead2.setVisibility(View.VISIBLE);
                    turkeyHead3.setVisibility(View.VISIBLE);
                    turkeyHead4.setVisibility(View.VISIBLE);
                }
            });

            turkeyHeadImageArray[a].setVisibility(View.VISIBLE);
            turkeyHeadImageArray[a].setClickable(true);
            turkeyHeadImageArray[a].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                        numberOfHits++;
                        turkeyShot();
                        v.getX();
                        v.getY();
                        v.animate().rotationX(80).setDuration(500).start();
                        v.animate().setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                                v.animate().rotationX(0).setStartDelay(200).start();
                                v.animate().setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        v.clearAnimation();
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        v.animate().start();


                    final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);


                    //this creates the blood splatter
                    final ImageView turkeyAngel = new ImageView(getApplicationContext());
                    turkeyAngel.setImageResource(R.drawable.blood_splatter);
                    turkeyAngel.clearAnimation();
                    turkeyAngel.setVisibility(View.VISIBLE);
                    turkeyAngel.setAlpha(1f);
                    turkeyAngel.setMaxWidth(500);
                    turkeyAngel.setMaxHeight(500);
                    turkeyAngel.setX(v.getX()-300);
                    turkeyAngel.setY(v.getY()+300);
                    relativeLayout.addView(turkeyAngel);

                    turkeyAngel.animate()
                            .alpha(0f)
                            .setDuration(500)
                            .start();





//                    if(v == turkeyHead1){
//                            //turkeyHead1.setVisibility(View.);
//                            turkeyHead1.setImageResource(R.drawable.blood_splatter_dark);
//                            //yAnimTurkeyHead.end();
//                        }
//                        else if(v == turkeyHead2){
//                           // turkeyHead2.setVisibility(View.GONE);
//                            turkeyHead2.setImageResource(R.drawable.blood_splatter_dark);
//                        }
//                        else if(v == turkeyHead3){
//                            //turkeyHead3.setVisibility(View.GONE);
//                            turkeyHead3.setImageResource(R.drawable.blood_splatter_dark);
//                        }
//                        else if(v == turkeyHead4){
//                            //turkeyHead4.setVisibility(View.GONE);
//                            turkeyHead4.setImageResource(R.drawable.blood_splatter_dark);
//                        }
                }
            });

//            final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
//
//            final ImageView turkeyAngel = new ImageView(getApplicationContext());
//            turkeyAngel.setImageResource(R.drawable.blood_splatter);
//            turkeyAngel.setVisibility(View.VISIBLE);
//            turkeyAngel.setMaxWidth(50);
//            turkeyAngel.setMaxHeight(50);
            //relativeLayout.addView(turkeyAngel);

           // turkeyAngel.animate().translationY(-2000).setDuration(1000);


//            turkeyHeadImageArray[a].setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_DOWN){
//                        relativeLayout.addView(turkeyAngel);
//                                turkeyAngel.setX(event.getX());
//                                turkeyAngel.setY(event.getY());
//                               // turkeyAngel.animate().translationY(-2000).setDuration(1000);
//                    }
//                    return true;
//                }
//            });



            //these values below should be put into a control panel above
            TurkeyHead turkeyHeadObject = new TurkeyHead(turkeyHeadImageArray[a], turkeyHeight, turkeyWidth, baseStartValueX,
                    baseEndValueX, startValueArray[a], endValueArray[a], durationArray[a], repeatCount, repeatMode, fillAfter);

          //  turkeyHeadObject.translateAninimation(turkeyHeadImageArray[a]);
          //  turkeyHeadObject.objectAnimationMotionY(turkeyHeadImageArray[a]);


            }

//        ObjectAnimator yAnim = ObjectAnimator.ofFloat(turkeyHead1, "y",
//                turkeyHead1.getY(), turkeyHead1.getHeight() - 50f);
//        yAnim.setDuration(duration1);
//        yAnim.setRepeatCount(20);
//        yAnim.setRepeatMode(ValueAnimator.REVERSE);
//       // yAnim.setInterpolator(new AccelerateInterpolator(2f));
//        //yAnim.addUpdateListener(this);
//        //yAnim.addListener(this);
//        yAnim.start();
    }

//
//
//
//        Random random1 = new Random();
//        int randomDecreaseValue1 = random1.nextInt(200);
//        Random random2 = new Random();
//        int randomDecreaseValue2 = random2.nextInt(200);
//        Random random3 = new Random();
//        int randomDecreaseValue3 = random3.nextInt(200);
//        Random random4 = new Random();
//        int randomDecreaseValue4 = random4.nextInt(200);

//        startValue1 = startValue1 - randomDecreaseValue1;
//        startValue2 = startValue2 - randomDecreaseValue2;
//        startValue3 = startValue3 - randomDecreaseValue3;
//        startValue4 = startValue4 - randomDecreaseValue4;
//
//        int endValue1 = baseEndValue;
//        int endValue2 = baseEndValue;
//        int endValue3 = baseEndValue;
//        int endValue4 = baseEndValue;
//
//        Random random5 = new Random();
//        int randomIncreaseValue1 = random5.nextInt(150);
//        Random random6 = new Random();
//        int randomIncreaseValue2 = random6.nextInt(150);
//        Random random7 = new Random();
//        int randomIncreaseValue3 = random7.nextInt(150);
//        Random random8 = new Random();
//        int randomIncreaseValue4 = random8.nextInt(150);
//
//        int duration1 = baseDuration;
//        int duration2 = baseDuration;
//        int duration3 = baseDuration;
//        int duration4 = baseDuration;
//
//        Random random9 = new Random();
//        int randomDecreaseDurationValue1 = random9.nextInt(300);
//        Random random10 = new Random();
//        int randomDecreaseDurationValue2 = random10.nextInt(300);
//        Random random11 = new Random();
//        int randomDecreaseDurationValue3 = random11.nextInt(300);
//        Random random12 = new Random();
//        int randomDecreaseDurationValue4 = random12.nextInt(300);
//
//        duration1 = baseDuration - randomDecreaseDurationValue1;
//        duration2 = baseDuration - randomDecreaseDurationValue2;
//        duration3 = baseDuration - randomDecreaseDurationValue3;
//        duration4 = baseDuration - randomDecreaseDurationValue4;
//
//        endValue1 = endValue1 + randomIncreaseValue1;
//        endValue2 = endValue2 + randomIncreaseValue2;
//        endValue3 = endValue3 + randomIncreaseValue3;
//        endValue4 = endValue4 + randomIncreaseValue4;
//
//        ImageView turkeyHead1 = (ImageView) findViewById(R.id.turkey_head1);
//        ImageView turkeyHead2 = (ImageView) findViewById(R.id.turkey_head2);
//        ImageView turkeyHead3 = (ImageView) findViewById(R.id.turkey_head3);
//        ImageView turkeyHead4 = (ImageView) findViewById(R.id.turkey_head4);

//
//        TurkeyHead turkeyHeadObject1 = new TurkeyHead(turkeyHead1, 100, 300, 0, 0, startValue1, endValue1, duration1, 10, 10, true);
//        TurkeyHead turkeyHeadObject2 = new TurkeyHead(turkeyHead1, 200, 400, 0, 0, startValue2, endValue2, duration2, 10, 10, true);
//        TurkeyHead turkeyHeadObject3 = new TurkeyHead(turkeyHead1, 200, 200, 0, 0, startValue3, endValue3, duration3, 10, 10, false);
//        TurkeyHead turkeyHeadObject4 = new TurkeyHead(turkeyHead1, 200, 200, 0, 0, startValue4, endValue4, duration4, 10, 10, false);
//
//
//        turkeyHeadObject1.translateAninimation(turkeyHead1);
//        turkeyHeadObject2.translateAninimation(turkeyHead2);
//        turkeyHeadObject3.translateAninimation(turkeyHead3);
//        turkeyHeadObject4.translateAninimation(turkeyHead4);


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }
        return false;
    }


    public void cloudMotion(){
        ImageView cloud1 = (ImageView) findViewById(R.id.cloud1);
        cloud1.setVisibility(View.VISIBLE);
        ImageView cloud2 = (ImageView) findViewById(R.id.cloud2);
        cloud2.setVisibility(View.VISIBLE);

        Cloud cloudRightToLeft = new Cloud(cloud1, 200, 200, -300, 1200, 0, 0, 10000, 15, Animation.RESTART, false);
        Cloud cloudLeftToRight = new Cloud(cloud2, 200, 200, 300, -1200, 0, 0, 13000, 15, Animation.RESTART, false);

        cloudLeftToRight.translateAnimation(cloud1);
        cloudRightToLeft.translateAnimation(cloud2);


    }

//    public void cowMotion(){
//        ImageView cow1 = (ImageView) findViewById(R.id.cow1);
//        cow1.setVisibility(View.VISIBLE);
//
//        Cow cowRighttoLeft = new Cow(cow1, 50, 50, 1200, -200, 0, 0, 25000, 15, Animation.RESTART, false);
//
//        cowRighttoLeft.translateAnimation(cow1);
//    }



    public void playInitialGobble(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.turkey_gobble);
        mediaPlayer.start();
        mediaPlayer.setLooping(false);

    }

    public void playSoundEffects(){

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click_on_sound);
        mediaPlayer.start();

    }

    public void playTurkeyCry(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.turkey_cry);
        mediaPlayer.start();

    }

   // public static TextView pointsText = (TextView) findViewById(R.id.points);


    public void turkeyShot(){
        points += 10;

        Log.i("points", "turkeyShot: called ");
        TextView pointsText = (TextView) findViewById(R.id.points);
        pointsText.setText("Points " + Integer.toString(points));
        pointsText.bringToFront();

        playTurkeyCry();


        RelativeLayout relativeLayout = new RelativeLayout(this);

        ImageView turkeyAngel = new ImageView(getApplicationContext());
        turkeyAngel.setImageResource(R.drawable.turkey_body);
        turkeyAngel.setVisibility(View.VISIBLE);
        turkeyAngel.setMaxWidth(50);
        turkeyAngel.setMaxHeight(50);
        relativeLayout.addView(turkeyAngel);

        turkeyAngel.animate().translationY(-2000).setDuration(1000);




    }

//
//    public void onClickTurkey1(View view){
//        turkeyShot();
//    }
//
//
//    public void onClickTurkey2(View view){
//        turkeyShot();
//    }
//
//
//    public void onClickTurkey3(View view){
//        turkeyShot();
//    }
//
//
//    public void onClickTurkey4(View view){
//        turkeyShot();
//    }
}
