package com.example.mace.countdowntimer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnClickListener  {

    private Button startButton;
    private TextView timeLeft;
    private TextView timeElapsed;
    private boolean timerStarted = false;
    private BasicCountDownTimer timer;
    private long millisecondsElapsed;

    private final long startTime = 50*1000;
    private final long interval = 1*1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = (Button) this.findViewById(R.id.button);
        startButton.setOnClickListener(this);
        timeLeft = (TextView)this.findViewById(R.id.timeLeft);
        timeElapsed = (TextView)this.findViewById(R.id.timeElapsed);
        timer = new BasicCountDownTimer(startTime, interval);
        timeLeft.setText(timeLeft.getText() + String.valueOf(startTime));


    }

    @Override
    public void onClick(View v){
        if(!timerStarted) {
            timer.start();
            timerStarted = true;
            startButton.setText("STOP");

        }

        else {
            timer.cancel();
            timerStarted = false;
            startButton.setText("RESET");
        }
    }

    public class BasicCountDownTimer extends CountDownTimer {

        public BasicCountDownTimer(long startTime, long interval){
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            timeLeft.setText("Isteklo vreme");
            timeElapsed.setText("Time elapsed: " + String.valueOf(startTime));

        }

        @Override
        public void onTick(long millisecondsUntilDone){
            timeLeft.setText("Time remaining: " + millisecondsUntilDone);
            millisecondsElapsed = startTime - millisecondsUntilDone;
            timeElapsed.setText("Time elapsed: " + String.valueOf(millisecondsElapsed));


        }


    }
}
