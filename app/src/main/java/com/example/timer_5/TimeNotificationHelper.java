package com.example.timer_5;



import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

/*
* 채널(Channel)이란?
안드로이드 오레오(API 26) 이상부터는 모든 알림에 채널을 할당해야한다.
 채널을 할당하지 않으면 알림이 오지 않는다.
채널은 알림마다 설정해서 채널을 통해 알림을 분류하고,
채널별로 설정을 다르게 지정할 수 있다.
 */



public class TimeNotificationHelper extends ContextWrapper {

    public static final String channeID = "channelID"; //채널의 고유한 id
    public static final String channeNm = "channelNm"; //채널의 이름

    private NotificationManager notiManager;

    public TimeNotificationHelper(Context base) {
        super(base);

        //안드로이드 버전이 오레오거나 이상이면 채널생성성
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels(){

        /*채널 객체 생성
         * NotificationManager의 createNotificationChannel()를 이용해서 등록
         * 알림이 올때마다 만들 필요가 없고 딱 한번만 만들면 된다.
         */
        NotificationChannel channel1 = new NotificationChannel(channeID, channeNm, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.black);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager(){
        if(notiManager == null){
            notiManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notiManager;
    }


    /*
    채널 지정하기
    알림에 지정할때는 Builder 객체에 채널을 같이 지정해주면 된다.
     */
    public NotificationCompat.Builder getChannelNotification(){
        return new NotificationCompat.Builder(getApplicationContext(), channeID)
                .setContentTitle("오늘의 술자리")
                .setContentText(" 집에 갈 사람이 있습니다!!")
                .setSmallIcon(R.drawable.ic_launcher_background);
    }

}