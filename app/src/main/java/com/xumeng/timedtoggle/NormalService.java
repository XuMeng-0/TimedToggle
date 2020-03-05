package com.xumeng.timedtoggle;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;

public class NormalService extends IntentService {

    public NormalService(){
        super("NormalService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        normal();
    }


    private void normal(){
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if(audioManager != null){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        }
    }
}
