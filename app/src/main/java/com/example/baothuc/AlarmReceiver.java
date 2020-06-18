package com.example.baothuc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Toi trong receiver", "hello");

        String s = intent.getExtras().getString("extra");
        Log.e("Ban truyen key", s);

        Intent myIntent = new Intent(context, Music.class);
        myIntent.putExtra("extra", s);
        context.startService(myIntent);
    }
}
