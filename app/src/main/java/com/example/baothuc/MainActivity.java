package com.example.baothuc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button button_set, button_stop;
    TextView textView_show;
    TimePicker timePicker;
    Calendar calendar;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mappingView();
        intent();
        eventClickSet();
        eventClickStop();
    }

    private void intent() {
        intent = new Intent(MainActivity.this, AlarmReceiver.class);
    }

    private void eventClickStop() {
        button_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);
                intent.putExtra("extra", "off");
                sendBroadcast(intent);
                textView_show.setText("Stop !");
                button_stop.setEnabled(false);
            }
        });
    }

    private void eventClickSet() {
        button_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                int gio = timePicker.getCurrentHour();
                int phut = timePicker.getCurrentMinute();

                String string_gio = String.valueOf(gio);
                String string_phut = String.valueOf(phut);

                if(gio > 12){
                    string_gio = String.valueOf(gio - 12);
                }
                if(phut < 10){
                    string_phut = "0" + String.valueOf(phut);
                }

                intent.putExtra("extra", "on");

                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                textView_show.setText("Time : " + string_gio + ":" + string_phut);

                button_stop.setEnabled(true);
            }
        });
    }

    private void mappingView() {
        button_set = (Button) findViewById(R.id.button_set);
        button_stop = (Button) findViewById(R.id.button_stop);
        textView_show = (TextView) findViewById(R.id.textView_show);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }
}
