package com.xumeng.timedtoggle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EndReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("timedEnd".equals(intent.getAction())) {
            Intent intent1 = new Intent(context, NormalService.class);
            intent1.setAction("normalService");
            context.startService(intent1);
        }
    }
}
