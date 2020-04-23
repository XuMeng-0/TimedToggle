package com.xumeng.timedtoggle;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

import android.os.Handler;

public class NormalService extends IntentService {

    public NormalService() {
        super("NormalService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        normal();
        /*如果用户选择了重复频率，设置下一次定时任务*/
        if (!TimedUtil.toggleButtonsIsCheckedNotContainsTrue()) {
            resetTimedNormal();
        }
    }

    private void normal() {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioManager != null) {
            Log.i("currentTime", String.valueOf(System.currentTimeMillis()));
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            Handler handler = new Handler(getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(NormalService.this, "切换为正常模式", Toast.LENGTH_SHORT)
                            .show();
                }
            });
            Log.i("NormalService", "切换为正常模式");
        }
    }

    //再次设置定时置为正常模式任务
    private void resetTimedNormal() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, EndReceiver.class);
        intent.setAction("timedEnd");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 2, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        long timestamp = TimedUtil.computeTimeAgain(false);
        if (timestamp == -1L) {
            Handler handler = new Handler(getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(NormalService.this,
                            "定时置为正常模式任务设置失败，选择的时间段无效", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
        Log.i("NormalService", "定时置为正常模式任务已经设置完成");
    }
}
