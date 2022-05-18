package com.example.fcmtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessageService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        //token을 서버로 전송
        Log.d("FCM_TEST", "fcm_test" + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //수신한 메시지를 처리
        String title = remoteMessage.getData().get("title");//firebase에서 보낸 메세지의 title
        String message = remoteMessage.getData().get("message");//firebase에서 보낸 메세지의 내용
        String test = remoteMessage.getData().get("test");

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("test", test);


       PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
               PendingIntent.FLAG_UPDATE_CURRENT);

        String channel = "채널";
        String channel_nm = "채널명";

        NotificationManager notichannel = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channelMessage = new NotificationChannel(channel, channel_nm, NotificationManager.IMPORTANCE_DEFAULT);
        channelMessage.setDescription("채널 설명");
        channelMessage.enableLights(true);
        channelMessage.enableVibration(true);
        channelMessage.setShowBadge(true);
        channelMessage.setVibrationPattern(new long[]{1000, 1000});
        notichannel.createNotificationChannel(channelMessage);

        //푸시 알람을 Builder를 이용하여 제작
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, channel);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background);
        notificationBuilder.setContentTitle(title); // 푸시 알람의 제목
        notificationBuilder.setContentText(message); // 푸시 알람의 내용
        notificationBuilder.setChannelId(channel);
        notificationBuilder.setAutoCancel(true); // 선택 시 자동삭제
        notificationBuilder.setContentIntent(pendingIntent); // 알림을 눌렀을 때 실행할 인텐트 설정
        notificationBuilder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(9999, notificationBuilder.build());





    }
}
