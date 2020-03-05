package com.xumeng.timedtoggle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class StartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if("timedStart".equals(intent.getAction())){
            Toast.makeText(context,"切换为静音模式",Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(context, SilentService.class);
            intent1.setAction("silentService");
            context.startService(intent1);
        }
    }
}
