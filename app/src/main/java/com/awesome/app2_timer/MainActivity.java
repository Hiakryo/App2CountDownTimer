package com.awesome.app2_timer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private  long Starttime;
    private TextView CountDownText;
    private EditText InputTimeText;
    private Button SetTimeButton;
    private Button StartPauseButton;
    private Button ResetButton;
    private boolean Timerrunning;
    private CountDownTimer XCountDownTimer;//https://developer.android.com/reference/android/os/CountDownTimer
    private long Timeleft= Starttime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CountDownText= findViewById(R.id.Countdown);
        StartPauseButton = findViewById(R.id.Start_Button);
        ResetButton = findViewById(R.id.Reset_Button);
        SetTimeButton = findViewById(R.id.Set_Button);
        InputTimeText = findViewById(R.id.InputTime);

        SetTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Input1= InputTimeText.getText().toString();
                if(Input1.length()==0)//gak ada isinya edittextnya
                {
                    Toast.makeText(MainActivity.this, "Please Input The Time!", Toast.LENGTH_SHORT).show();
                    return;
                }
                long milinput=  Long.parseLong(Input1) * 60000;
                if(milinput==0)
                {
                    Toast.makeText(MainActivity.this, "Positive Number only!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Settime(milinput);
                InputTimeText.setText("");//inputtimetext=null

            }
        });


        StartPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Timerrunning)
                {
                    TimerPaused();
                }
                else
                {
                    TimerStart();
                }
            }
        });

        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimerReset();
            }
        });

updateCountDownText();
    }

    private void Settime(long milisecond)
    {
        Starttime= milisecond;
        TimerReset();
    }
    private void TimerStart()
    {
        XCountDownTimer= new CountDownTimer(Timeleft,1000)
        {
            @Override
            public void onTick(long Sectillfinished)
            {
             Timeleft=Sectillfinished;//Dia ngulang waktu yang sama terus
             updateCountDownText();//ngatur waktu
            }

            @Override
            public void onFinish() {
             Timerrunning=false;
             StartPauseButton.setText("Start");
             StartPauseButton.setVisibility(View.INVISIBLE);
             ResetButton.setVisibility(View.VISIBLE);//pas selse
            }
        }.start();
        Timerrunning=true;
        StartPauseButton.setText("Paused");
        ResetButton.setVisibility(View.INVISIBLE);
    }

    private void TimerPaused()
    {
         XCountDownTimer.cancel();
         Timerrunning=false;
         StartPauseButton.setText("Start");
         ResetButton.setVisibility(View.VISIBLE);
    }
    private void TimerReset()
    {
        Timeleft= Starttime;
        updateCountDownText();
        ResetButton.setVisibility(View.INVISIBLE);
        StartPauseButton.setVisibility(View.VISIBLE);
    }

    private void updateCountDownText()
    {
        int hour= (int) (Timeleft/1000) /3600;
        int minute= (int) ((Timeleft/1000)%3600)/60;
        int second= (int) (Timeleft/1000)%60;
        String Timeformat;//https://developer.android.com/reference/java/util/Locale
        if(hour>0 ) {
            Timeformat=String.format(Locale.getDefault(), "%d:%02d:%02d",hour, minute, second);
        }
        else
        {
            Timeformat= String.format(Locale.getDefault(), "%02d:%02d", minute, second);
        }

        CountDownText.setText(Timeformat);
    }

}
