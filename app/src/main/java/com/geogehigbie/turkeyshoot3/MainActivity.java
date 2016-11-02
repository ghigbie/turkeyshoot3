package com.geogehigbie.turkeyshoot3;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity  {

    //level increase variables
    int level = 1;
    int durationLevelUp = 200 * level;


    public int points = 0;
    private int highScoreInt;

    private int numberOfBullets = 4;

    private int touchCount = 0; //counts the number of times the screen is touched, i.e. number of bullets

    private int numberKilled = 0;

    private MediaPlayer mediaPlayerTurkeyCry;
    private MediaPlayer mediaPlayerReload;
    private MediaPlayer mediaPlayerGobble;
    private MediaPlayer mediaPlayerGunShot;
    private MediaPlayer mediaPlayerClick;

    private int insultCount = 0;
    private int numberOfMisses = 0;

    private Handler handler;
    private Runnable runnable;

    private int timer = 0; //keeps track of time roughly so that if the game sits idle for too long, the game ends




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RelativeLayout relativeLayoutMain = (RelativeLayout) findViewById(R.id.activity_main);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setFillAfter(true);
        relativeLayoutMain.startAnimation(alphaAnimation);

        defineMediaPlayers(); //defines the sounds to be played during the game

    }

    //makes the game playable by setting the touchCount to zero and calling the animateTurkeyHeads method
    public void clickToPlay(View view){

        checkStatus();

        touchCount = 0;
        initialAnimation();
        //animateTurkeyHeads();

        mediaPlayerClick.start();

        cloudMotion();
        // cowMotion();  //this has been removed for now

        setClickableArea(); //this is set first so that the number of bullets is displayed properly

        loadBullets(); //this makes the bullets visible and sets the touch count to zero

    }


    // plays when the screen is open
    public void initialAnimation(){
        ImageView turkeybody = (ImageView) findViewById(R.id.big_turkey_body);
        TurkeyBig turkeyBig1 = new TurkeyBig(turkeybody, 2000, 2000, 0, -2000, 0, 1700, 3000, 0, 0, true);
        turkeyBig1.translateAninimation(turkeybody);


        mediaPlayerGobble.start();

        buttonAnimation(); //the button is set the fade upon game start

    }

    //the button is set the fade upon game start
    //button fades away when pressed
    public void buttonAnimation(){
        final Button button = (Button) findViewById(R.id.start_button);
        button.setClickable(false);
        Animation alphaAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha_animation_long);
        button.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                button.setVisibility(View.GONE);
                animateTurkeyHeads();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //defines and sets up the media players to be used to play the sound effects
    public void defineMediaPlayers(){
        mediaPlayerTurkeyCry = MediaPlayer.create(this, R.raw.turkey_cry);
        mediaPlayerReload = MediaPlayer.create(this, R.raw.reload_again);
        mediaPlayerGobble = MediaPlayer.create(this, R.raw.turkey_gobble);
        mediaPlayerGunShot = MediaPlayer.create(this, R.raw.shotgun_sound);
        mediaPlayerClick = MediaPlayer.create(this, R.raw.click_on_sound);
    }



    //sets the entire area of a screen to be clickable and which counts the number of shots!
    public void setClickableArea(){

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numberOfBullets > 0) {
                    mediaPlayerGunShot.start();
                }

                reduceBullets();

                showMissText();


            }
        });

        LinearLayout linearBulletLayout = (LinearLayout) findViewById(R.id.reload);
        linearBulletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadBullets();
            }
        });


    }

    //makes the bullets visible and loads them for player use this is also called be reloadBullets method
    //ALSO IMPORTANT: MAKES TURKEYS SHOOTABLE via the maketurkeyShootable() method
    public void loadBullets(){
        mediaPlayerReload.start(); //makes the reload sound
        touchCount = 0;
        numberOfBullets = 4;

        makeTurkeyShootable();

        //this is the area where the bullets sit visually on the screen - they are initially hidden from view
        LinearLayout topLinearLayout = (LinearLayout) findViewById(R.id.reload);
        topLinearLayout.bringToFront();

        ImageView bullet1 = (ImageView) findViewById(R.id.bullet1);
        ImageView bullet2 = (ImageView) findViewById(R.id.bullet2);
        ImageView bullet3 = (ImageView) findViewById(R.id.bullet3);
        ImageView bullet4 = (ImageView) findViewById(R.id.bullet4);

        bullet1.setVisibility(View.VISIBLE);
        bullet2.setVisibility(View.VISIBLE);
        bullet3.setVisibility(View.VISIBLE);
        bullet4.setVisibility(View.VISIBLE);

    }

    //makes the button invisible and resets the number of bullets via the load bullets method
    public void reloadBullets(){

      //  mediaPlayerReload.start(); //makes the reload sound

        Button reloadNow = (Button) findViewById(R.id.reload_button);
        reloadNow.setVisibility(View.INVISIBLE);

        loadBullets();

    }


    //this method reduces the number of bullets
    public void reduceBullets(){
        touchCount++;
        numberOfBullets--;

        ImageView bullet1 = (ImageView) findViewById(R.id.bullet1);
        ImageView bullet2 = (ImageView) findViewById(R.id.bullet2);
        ImageView bullet3 = (ImageView) findViewById(R.id.bullet3);
        ImageView bullet4 = (ImageView) findViewById(R.id.bullet4);

        switch(touchCount){
            case 1:
                bullet1.setVisibility(View.INVISIBLE);
                numberOfBullets = 3;
                break;
            case 2:
                bullet2.setVisibility(View.INVISIBLE);
                numberOfBullets = 2;
                break;
            case 3:
                bullet3.setVisibility(View.INVISIBLE);
                numberOfBullets = 1;
                break;
            case 4:
                bullet4.setVisibility(View.INVISIBLE);
                reloadNow();
                numberOfBullets = 0;
                break;
        }

    }

    //tells the player to reload the bullets by making a button visible also sets the button invisible and calls the
    //reloadBullets method, which calls the loadBullets method
    //ALSO IMPORTANT: MAKES TURKEYS NOT SHOOTABLE via the maketurkeyNOTShootable() method
    public void reloadNow(){

        final Button reloadNow = (Button) findViewById(R.id.reload_button);
        reloadNow.setVisibility(View.VISIBLE);
        reloadNow.bringToFront();

        makeTurkeyNOTShootable();

        reloadNow.setClickable(true);
        reloadNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadNow.setVisibility(View.INVISIBLE);

                reloadBullets();

            }
        });

    }

    public void makeTurkeyNOTShootable(){
        ImageView turkeyHead1 = (ImageView) findViewById(R.id.turkey_head1);
        ImageView turkeyHead2 = (ImageView) findViewById(R.id.turkey_head2);
        ImageView turkeyHead3 = (ImageView) findViewById(R.id.turkey_head3);
        ImageView turkeyHead4 = (ImageView) findViewById(R.id.turkey_head4);

        turkeyHead1.setClickable(false);
        turkeyHead2.setClickable(false);
        turkeyHead3.setClickable(false);
        turkeyHead4.setClickable(false);
    }


    public void makeTurkeyShootable(){
        ImageView turkeyHead1 = (ImageView) findViewById(R.id.turkey_head1);
        ImageView turkeyHead2 = (ImageView) findViewById(R.id.turkey_head2);
        ImageView turkeyHead3 = (ImageView) findViewById(R.id.turkey_head3);
        ImageView turkeyHead4 = (ImageView) findViewById(R.id.turkey_head4);

        turkeyHead1.setClickable(true);
        turkeyHead2.setClickable(true);
        turkeyHead3.setClickable(true);
        turkeyHead4.setClickable(true);
    }



//controls the main animation of the turkey heads
    public void animateTurkeyHeads(){

        int animationEndCount = 0;

        ImageView MeasurementTurkey = (ImageView) findViewById(R.id.turkey_head1);
        int TurkeyHeight = MeasurementTurkey.getHeight();

        //these values control turkey head behavior
        int numberOfTurkeys = 4;
        final int baseStartValueY = TurkeyHeight + 150; //sets the turkey's lower height, from where he animates or his hiding spot
        final int baseEndValueY = 125; //sets the turkey's max top value, the lower the higher
        int baseDuration = 1500; //this is the total time it will take for one animation

        //TODO: get height of each turkey head and set this to the be max for movement & needs to minus some value to keep below wood

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
            turkeyHeadImageArray[a].setVisibility(View.VISIBLE);
            turkeyHeadImageArray[a].setY(250);

            Random random1 = new Random();
            int randomDecreaseValue = random1.nextInt(200); //randomly sets the turkey's lower position
            startValueArray[a] = startValueArray[a] - randomDecreaseValue;

            Random random2 = new Random();
            int randomIncreaseValue = random2.nextInt(50); //randomly changes the turkey's upper position
            endValueArray[a] = endValueArray[a] + randomIncreaseValue;

            Random random3 = new Random();
            int randomDecreaseDurationValue = random3.nextInt(400);//randomly changes the duration of each animation
            durationArray[a] = durationArray[a] - randomDecreaseDurationValue - durationLevelUp;


            final ObjectAnimator yAnimTurkeyHead = ObjectAnimator.ofFloat(turkeyHeadImageArray[a], "y", startValueArray[a],
                    endValueArray[a]);
            yAnimTurkeyHead.setDuration(durationArray[a]);
            yAnimTurkeyHead.setRepeatCount(21);
            yAnimTurkeyHead.setRepeatMode(ValueAnimator.REVERSE);
            yAnimTurkeyHead.start();
            yAnimTurkeyHead.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation){


                    yAnimTurkeyHead.cancel();

//                    ImageView turkeyHead1 = (ImageView) findViewById(R.id.turkey_head1);
//                    ImageView turkeyHead2 = (ImageView) findViewById(R.id.turkey_head2);
//                    ImageView turkeyHead3 = (ImageView) findViewById(R.id.turkey_head3);
//                    ImageView turkeyHead4 = (ImageView) findViewById(R.id.turkey_head4);
//
//                    LinearLayout turkeyContainer = (LinearLayout) findViewById(R.id.turkey_container);
//
//
//                    animationEndCount++;
//                    v.clearAnimation();
//
//                    if(animationEndCount >= 4){
//
//
//                        turkeyContainer.removeView();
//                        turkeyContainer.removeView(turkeyHead2);
//                        turkeyContainer.removeView(turkeyHead3);
//                        turkeyContainer.removeView(turkeyHead4);
//                        animationEndCount = 0;
//
//                        turkeyHead1.clearAnimation();
//                        turkeyHead2.clearAnimation();
//                        turkeyHead3.clearAnimation();
//                        turkeyHead4.clearAnimation();
//                    }
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

                        turkeyShot();
                        v.getX();
                        v.getY();
                        v.setClickable(false);
                        v.animate().rotationX(80).setDuration(600).start();
                        v.animate().alpha(0).setDuration(200).start();
                        v.animate().setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {

                                v.setClickable(true);
                                v.animate().alpha(1).setStartDelay(100).start();
                                v.animate().rotationX(0).setStartDelay(200).start();
                                v.animate().setListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {


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
                      //  v.animate().start();


                    final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);


                    //this creates the blood splatter
                    final ImageView turkeySplatter = new ImageView(getApplicationContext());
                    turkeySplatter.setImageResource(R.drawable.splatter);
                    turkeySplatter.clearAnimation();
                    turkeySplatter.setVisibility(View.VISIBLE);
                    turkeySplatter.setAlpha(1f);
                    turkeySplatter.setMaxWidth(500);
                    turkeySplatter.setMaxHeight(500);
                    turkeySplatter.setX(v.getX()-300);
                    turkeySplatter.setY(v.getY()+300);
                    relativeLayout.addView(turkeySplatter);

                    turkeySplatter.animate()
                            .alpha(0f)
                            .setDuration(500)
                            .start();
                }
            });

        }
    }


//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int x = (int)event.getX();
//        int y = (int)event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
//            case MotionEvent.ACTION_UP:
//        }
//        return false;
//    }


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


   // a method that does everything that it should when a turkey is shot
    public void turkeyShot(){
        numberKilled++;
        reduceBullets();
        points += 10;

        Log.i("points", "turkeyShot: called ");
        TextView pointsText = (TextView) findViewById(R.id.score);
        pointsText.setText("Score " + Integer.toString(points));
        pointsText.bringToFront();


        TextView highScore = (TextView) findViewById(R.id.high_score);
        highScore.setText("High Score " + Integer.toString(highScoreInt));
        highScore.bringToFront();

        mediaPlayerGunShot.start(); //plays sound of shotgun

        //plays the cry of the turkeys when they are dispatched
        mediaPlayerTurkeyCry.start();

//        if(numberKilled == 15){
//            levelUpStart();  //this keeps crashing
//        }





//this code is for an alternative angel to fly after a turkey is dispatched
//        RelativeLayout relativeLayout = new RelativeLayout(this);
//
//        ImageView turkeyAngel = new ImageView(getApplicationContext());
//        turkeyAngel.setImageResource(R.drawable.turkey_body);
//        turkeyAngel.setVisibility(View.VISIBLE);
//        turkeyAngel.setMaxWidth(50);
//        turkeyAngel.setMaxHeight(50);
//        relativeLayout.addView(turkeyAngel);
//
//        turkeyAngel.animate().translationY(-2000).setDuration(1000);

    }

    public void showMissText(){
        insultCount++;

        //numberOfMisses++;

        final TextView missText = (TextView) findViewById(R.id.miss_text);
        missText.setVisibility(View.VISIBLE);
        missText.bringToFront();

        if(numberOfBullets > 0) {
            if (insultCount % 3 == 0) {
                missText.setText("YOU SUCK!");
                missText.setTextSize(75);
                numberOfMisses++;
            }
            else{
                missText.setText("Miss!");
                missText.setTextSize(100);
                numberOfMisses++;
            }
        }
        else{
                missText.setText("Reload!");
                missText.setTextSize(75);
        }

        //missText.getAnimation().cancel();

        missText.animate().setDuration(500).alpha(1f).start();
        missText.animate().setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                missText.animate().alpha(0).setDuration(200).start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }



    public void levelUpStart(){
        timer =0;
        numberOfMisses = 0;
        numberKilled = 0;
        level++;
        durationLevelUp = 200 * level;


        final TextView levelText = (TextView) findViewById(R.id.level_text);
        levelText.setVisibility(View.VISIBLE);
        if(levelText.getAnimation()!=null)levelText.getAnimation().cancel();
        levelText.bringToFront();
        levelText.setText("Level " + Integer.toString(level));

        levelText.animate().setDuration(3000).alpha(1f).start();
        levelText.animate().setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                levelText.animate().alpha(0).setDuration(1000).start();
                levelText.animate().setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animateTurkeyHeads();
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

    }

    public void gameOver() {

        final TextView overText = (TextView) findViewById(R.id.level_text);
        overText.setText("Game Over");
        overText.setTextSize(75);
        overText.setVisibility(View.VISIBLE);
        if (overText.getAnimation() != null) overText.getAnimation().cancel();
        overText.bringToFront();

        overText.animate().setDuration(3000).alpha(1f).start();
        overText.animate().setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
               // overText.animate().alpha(0).setDuration(1000).start();

                RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
                relativeLayout.clearAnimation();

               // relativeLayout.getAnimation().cancel();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        handler.removeCallbacks(runnable);

        makeElementsFade();
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);

        relativeLayout.setClickable(false);
        makeElementsFade();

        final Button restart = (Button) findViewById(R.id.start_button);
        restart.setText("Replay");
        restart.setVisibility(View.VISIBLE);
        restart.setClickable(true);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clickToPlay(restart);
                restart.animate().alpha(0).setDuration(300).start();
                restart.setClickable(false);

                //this should make the entire layout fade out
                RelativeLayout relativeLayoutMain = (RelativeLayout) findViewById(R.id.activity_main);
                AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
                alphaAnimation.setDuration(4000);
                alphaAnimation.setFillAfter(true);
                relativeLayoutMain.startAnimation(alphaAnimation);
                alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rebirth();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }


    public void makeElementsFade() {
        numberKilled = 0;
        numberOfMisses = 0;

        Button reloadButton = (Button) findViewById(R.id.reload_button);
        ImageView turkeyHead1 = (ImageView) findViewById(R.id.turkey_head1);
        ImageView turkeyHead2 = (ImageView) findViewById(R.id.turkey_head2);
        ImageView turkeyHead3 = (ImageView) findViewById(R.id.turkey_head3);
        ImageView turkeyHead4 = (ImageView) findViewById(R.id.turkey_head4);

        View[] stuffToBeInvisible = {reloadButton, turkeyHead1, turkeyHead2, turkeyHead3, turkeyHead4};

        for (int a = 0; a < stuffToBeInvisible.length; a++) {
            stuffToBeInvisible[a].animate().alpha(0).setDuration(700).start();
            stuffToBeInvisible[a].setClickable(false);
        }
    }



    //this method checks to see if a the level should be increased or if the game will be over
    public void checkStatus() {

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                timer++;

                if (numberKilled > 20) {
                    levelUpStart();
                }

                if (numberOfMisses > 25 || timer > 250){
                    gameOver();
                }
                checkStatus();
            }
        };

        handler.postDelayed(runnable, 100);
    }




    //this relaunches the app
    public void rebirth(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

