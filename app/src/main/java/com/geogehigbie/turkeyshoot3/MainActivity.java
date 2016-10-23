package com.geogehigbie.turkeyshoot3;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity  {

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






    public void animateTurkeyHeads(){

        int baseStartValue = 800;
        int baseEndValue = 110;
        int baseDuration = 2000;

        int startValue1 = baseStartValue;
        int startValue2 = baseStartValue;
        int startValue3 = baseStartValue;
        int startValue4 = baseStartValue;

        Random random1 = new Random();
        int randomDecreaseValue1 = random1.nextInt(200);
        Random random2 = new Random();
        int randomDecreaseValue2 = random2.nextInt(200);
        Random random3 = new Random();
        int randomDecreaseValue3 = random3.nextInt(200);
        Random random4 = new Random();
        int randomDecreaseValue4 = random4.nextInt(200);

        startValue1 = startValue1 - randomDecreaseValue1;
        startValue2 = startValue2 - randomDecreaseValue2;
        startValue3 = startValue3 - randomDecreaseValue3;
        startValue4 = startValue4 - randomDecreaseValue4;

        int endValue1 = baseEndValue;
        int endValue2 = baseEndValue;
        int endValue3 = baseEndValue;
        int endValue4 = baseEndValue;

        Random random5 = new Random();
        int randomIncreaseValue1 = random5.nextInt(150);
        Random random6 = new Random();
        int randomIncreaseValue2 = random6.nextInt(150);
        Random random7 = new Random();
        int randomIncreaseValue3 = random7.nextInt(150);
        Random random8 = new Random();
        int randomIncreaseValue4 = random8.nextInt(150);

        int duration1 = baseDuration;
        int duration2 = baseDuration;
        int duration3 = baseDuration;
        int duration4 = baseDuration;

        Random random9 = new Random();
        int randomDecreaseDurationValue1 = random9.nextInt(300);
        Random random10 = new Random();
        int randomDecreaseDurationValue2 = random10.nextInt(300);
        Random random11 = new Random();
        int randomDecreaseDurationValue3 = random11.nextInt(300);
        Random random12 = new Random();
        int randomDecreaseDurationValue4 = random12.nextInt(300);

        duration1 = baseDuration - randomDecreaseDurationValue1;
        duration2 = baseDuration - randomDecreaseDurationValue2;
        duration3 = baseDuration - randomDecreaseDurationValue3;
        duration4 = baseDuration - randomDecreaseDurationValue4;

        //I could put this in a loop, but the code might get too complicated

//        int numberOfTurkHeads = 4;
//        for(int a=0; a< numberOfTurkHeads; a++){
//            Random
//        }


        endValue1 = endValue1 + randomIncreaseValue1;
        endValue2 = endValue2 + randomIncreaseValue2;
        endValue3 = endValue3 + randomIncreaseValue3;
        endValue4 = endValue4 + randomIncreaseValue4;

        ImageView turkeyHead1 = (ImageView) findViewById(R.id.turkey_head1);
        ImageView turkeyHead2 = (ImageView) findViewById(R.id.turkey_head2);
        ImageView turkeyHead3 = (ImageView) findViewById(R.id.turkey_head3);
        ImageView turkeyHead4 = (ImageView) findViewById(R.id.turkey_head4);

        //should start at 800 or lower
        TurkeyHead turkeyHeadObject1 = new TurkeyHead(turkeyHead1, 100, 300, 0, 0, startValue1, endValue1, duration1, 10, 10, true);
        TurkeyHead turkeyHeadObject2 = new TurkeyHead(turkeyHead1, 200, 400, 0, 0, startValue2, endValue2, duration2, 10, 10, true);
        TurkeyHead turkeyHeadObject3 = new TurkeyHead(turkeyHead1, 200, 200, 0, 0, startValue3, endValue3, duration3, 10, 10, false);
        TurkeyHead turkeyHeadObject4 = new TurkeyHead(turkeyHead1, 200, 200, 0, 0, startValue4, endValue4, duration4, 10, 10, false);


        turkeyHeadObject1.translateAninimation(turkeyHead1);
        turkeyHeadObject2.translateAninimation(turkeyHead2);
        turkeyHeadObject3.translateAninimation(turkeyHead3);
        turkeyHeadObject4.translateAninimation(turkeyHead4);
    }



    public void playInitialGobble(){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.turkey_gobble);
        mediaPlayer.start();
        mediaPlayer.setLooping(false);

    }

    public void playSoundEffects(){

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.click_on_sound);
        mediaPlayer.start();

    }

}
