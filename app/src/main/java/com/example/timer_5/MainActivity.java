package com.example.timer_5;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import android.os.CountDownTimer;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView time_text;

    AlertDialog customDialog;
    Calendar calendar = Calendar.getInstance();
    private int setHour=0;
    private int setMinute=0;
    private int setSec=0;
    private long time=0;
    private long tempTime = 0;

    private CountDownTimer Timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //view 객체 획득
        time_text = findViewById(R.id.time_text);

        //버튼 이벤트 등록
        time_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int min) {
                setHour = hourOfDay;
                setMinute = min;
                time=(Long.valueOf(setHour-hour)*3600000)+(Long.valueOf(setMinute-minute)*60000)-Long.valueOf(second)*1000;
                //time_text.setText( Integer.toString(setHour) +" : "+Integer.toString(setMinute));
                TimerRest timer = new TimerRest(time, 1000);
                timer.start();
            }
        }, hour, minute,true);
        timePickerDialog.show();

    }


    class TimerRest extends CountDownTimer {
        public TimerRest(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tempTime=millisUntilFinished;
            updateTimer();
        }

        @Override
        public void onFinish() {
                time_text.setText("집에 갈 시간입니다.");

            }

        }


    private void updateTimer(){

        time_text.setText(String.valueOf(tempTime));


        int text_hour = (int)tempTime/3600000;
        int text_min = (int) tempTime % 3600000/60000;
        int text_sec = (int) tempTime%3600000%60000/1000;

        String textTime="";

        textTime=text_hour+" :  "+text_min +" : "+text_sec;

        /*분이 10보다 작으면 앞에 0 붙임;
        if(text_min<10) textTime+="0";
        textTime += text_min +":";

         */

        time_text.setText(textTime);

    }

    private void startAlarm(Calendar c){
        Log.d(TAG, "## startAlarm ## ");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, TimeAlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE, 1);
        }

        //RTC_WAKE : 지정된 시간에 기기의 절전 모드를 해제하여 대기 중인 인텐트를 실행
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }

}