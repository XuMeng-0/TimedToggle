package com.xumeng.timedtoggle;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;

public class SilentService extends IntentService {

    public SilentService(){
        super("SilentService");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        silent();
    }

    private void silent(){
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if(audioManager != null){
            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }
}
