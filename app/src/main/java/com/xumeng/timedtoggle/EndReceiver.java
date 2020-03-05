package com.xumeng.timedtoggle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

public class EndReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if("timedEnd".equals(intent.getAction())){
            Toast.makeText(context, "切换为正常模式", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(context, NormalService.class);
            intent1.setAction("normalService");
            context.startService(intent1);
        }
    }
}
