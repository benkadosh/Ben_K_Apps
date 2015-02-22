package com.example.user.ballgame;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MainActivity extends ActionBarActivity {

    public static final int BALL_SIZE = 100;
    public static final int MARGIN_LEFT = 30;
    public static final int MARGIN_TOP = 75;
    public static final int MARGIN_RIGHT = 850;
    public static final int MARGIN_BOTTOM = 1400;
    private RelativeLayout mainLayout;
    ImageView ball;
    float x, y;
    float vX, vY;
    private GestureDetector gestureDetector;
    final Handler handler = new Handler();
    final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(BALL_SIZE, BALL_SIZE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        ball = new ImageView(this);
        ball.setImageResource(R.drawable.soccerball);
        x = 400;
        y = 400;
        layoutParams.setMargins(Math.round(x), Math.round(y), 0, 0);
        ball.setLayoutParams(layoutParams);
        mainLayout.addView(ball);

        gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                //todo: make sure fling is touching the ball.
                vX = velocityX * 0.002f;
                vY = velocityY * 0.002f;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (vX != 0f || vY != 0f) {
                            x += vX;
                            y += vY;
                            vX *= 0.995f;
                            vY *= 0.995f;
                            if (Math.abs(vX) < 0.1)
                                vX = 0f;
                            if (Math.abs(vY) < 0.1)
                                vY = 0f;
                            if (x <= MARGIN_LEFT || x >= MARGIN_RIGHT) {
                                if (x < MARGIN_LEFT)
                                    x = MARGIN_LEFT;
                                if (x > MARGIN_RIGHT)
                                    x = MARGIN_RIGHT;
                                vX *= -1;
                            }

                            if (y <= MARGIN_TOP || y >= MARGIN_BOTTOM) {
                                if (y < MARGIN_TOP)
                                    y = MARGIN_TOP;
                                if (y > MARGIN_BOTTOM)
                                    y = MARGIN_BOTTOM;
                                vY *= -1;
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    layoutParams.setMargins(Math.round(x), Math.round(y), 0, 0);
                                    ball.setLayoutParams(layoutParams);
                                }
                            });
                            try {
                                Thread.sleep(8);
                            } catch (InterruptedException e)

                            {
                                e.printStackTrace();
                            }
                        }
                    }

                });
                thread.start();
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}