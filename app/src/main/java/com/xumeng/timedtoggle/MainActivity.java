package com.xumeng.timedtoggle;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button startButton;

    private Button endButton;

    private TextView startTimeTextView;

    private TextView endTimeTextView;

    private AlarmManager alarmManager;

    private Date date;

    private boolean isStartTime = true;

    private long timestampStart = -1;

    private long timestampEnd = -1;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.startButton: {
                    setAlarm();
                }
                break;
                case R.id.endButton: {
                    cancelAlarm();
                }
                break;
                case R.id.startTime: {
                    isStartTime = true;
                    pickTime();
                }
                break;
                case R.id.endTime: {
                    isStartTime = false;
                    pickTime();
                }
                break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(listener);

        endButton = findViewById(R.id.endButton);
        endButton.setOnClickListener(listener);

        startTimeTextView = findViewById(R.id.startTime);
        startTimeTextView.setText("请点此选择时间");
        startTimeTextView.setOnClickListener(listener);

        endTimeTextView = findViewById(R.id.endTime);
        endTimeTextView.setText("请点此选择时间");
        endTimeTextView.setOnClickListener(listener);
    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Log.i("MainActivity", String.valueOf(timestampStart));
        Log.i("MainActivity", String.valueOf(timestampEnd));

        if (timestampStart < 0 || timestampEnd < 0) {
            Toast.makeText(this, "请选择时间段", Toast.LENGTH_SHORT).show();
            return;
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timestampStart, createPendingIntent1());
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, timestampEnd, createPendingIntent2());

        Toast.makeText(this, "启动成功", Toast.LENGTH_SHORT).show();
    }

    private void cancelAlarm() {
        alarmManager.cancel(createPendingIntent1());
        alarmManager.cancel(createPendingIntent2());
        Toast.makeText(this, "关闭成功", Toast.LENGTH_SHORT).show();
    }

    private PendingIntent createPendingIntent1() {
        Intent intent = new Intent(this, StartReceiver.class);
        intent.setAction("timedStart");
        return PendingIntent.getBroadcast(this, 1, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private PendingIntent createPendingIntent2() {
        Intent intent = new Intent(this, EndReceiver.class);
        intent.setAction("timedEnd");
        return PendingIntent.getBroadcast(this, 2, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void pickTime() {
        date = new Date();
        Intent intent = new Intent();
        intent.putExtra("time", date);
        intent.setClass(this, TimePickerActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0) {
            if (data == null) {
                return;
            }
            String time = data.getExtras().getString("time");

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm");
            String dateString = simpleDateFormat.format(date);
            String[] dateStringArray = dateString.split(":");
            dateStringArray[3] = time.split(":")[0];
            dateStringArray[4] = time.split(":")[1];
            dateString = dateStringArray[0] + ":" + dateStringArray[1] + ":" + dateStringArray[2] + ":" +
                    dateStringArray[3] + ":" + dateStringArray[4];

            try {
                date = simpleDateFormat.parse(dateString);
                //Log.i("MainActivity",date.toString());
                if (isStartTime) {
                    startTimeTextView.setText(time);
                    timestampStart = date.getTime();
                } else {
                    endTimeTextView.setText(time);
                    timestampEnd = date.getTime();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
