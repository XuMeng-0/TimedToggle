package com.xumeng.timedtoggle;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class SilentService extends IntentService {

    public SilentService() {
        super("SilentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        silent();
        /*如果用户选择了重复频率，设置下一次定时任务*/
        if (!TimedUtil.toggleButtonsIsCheckedNotContainsTrue()) {
            resetTimedSilent();
        }
    }

    private void silent() {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioManager != null) {
            Log.i("currentTime", String.valueOf(System.currentTimeMillis()));
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);

            Handler handler = new Handler(getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SilentService.this, "切换为静音模式", Toast.LENGTH_SHORT)
                            .show();
                }
            });
            Log.i("SilentService", "切换为静音模式");
        }
    }

    //再次设置定时静音任务
    private void resetTimedSilent() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, StartReceiver.class);
        intent.setAction("timedStart");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        long timestamp = TimedUtil.computeTimeAgain(true);
        if (timestamp == -1L) {
            Handler handler = new Handler(getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SilentService.this,
                            "定时置为静音模式任务设置失败，选择的时间段无效", Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
        Log.i("SilentService", "定时置为静音模式任务已经设置完成");
    }


}
