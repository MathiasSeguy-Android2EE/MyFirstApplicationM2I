package com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android2ee.formation.m2i.octmmxvi.myfirstapplicationm.service.MySmsService;

public class MySmsReceiver extends BroadcastReceiver {
    private static final String TAG = "MySmsReceiver";
    public MySmsReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG," Ok MySmsReceiver receive an SMS [action="+intent.getAction());
        Intent startServiceIntent=new Intent(context, MySmsService.class);
        startServiceIntent.setAction(intent.getAction());
        startServiceIntent.putExtras(intent);
        context.startService(startServiceIntent);
    }
}
