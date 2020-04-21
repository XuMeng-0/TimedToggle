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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView startTimeTextView;

    private TextView endTimeTextView;

    private AlarmManager alarmManager;

    private boolean isStartTime = true;

    private String timeStart = "";

    private String timeEnd = "";

    private View.OnClickListener onClickListener = new View.OnClickListener() {
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

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new
            CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    switch (buttonView.getId()) {
                        case R.id.Monday: {
                            if (isChecked) {
                                Log.i("MainActivity", "1 checked");
                                TimedUtil.toggleButtonsIsChecked[0] = true;
                            } else {
                                Log.i("MainActivity", "1 unchecked");
                                TimedUtil.toggleButtonsIsChecked[0] = false;
                            }
                        }
                        break;
                        case R.id.Tuesday: {
                            if (isChecked) {
                                Log.i("MainActivity", "2 checked");
                                TimedUtil.toggleButtonsIsChecked[1] = true;
                            } else {
                                Log.i("MainActivity", "2 unchecked");
                                TimedUtil.toggleButtonsIsChecked[1] = false;
                            }
                        }
                        break;
                        case R.id.Wednesday: {
                            if (isChecked) {
                                Log.i("MainActivity", "3 checked");
                                TimedUtil.toggleButtonsIsChecked[2] = true;
                            } else {
                                Log.i("MainActivity", "3 unchecked");
                                TimedUtil.toggleButtonsIsChecked[2] = false;
                            }
                        }
                        break;
                        case R.id.Thursday: {
                            if (isChecked) {
                                Log.i("MainActivity", "4 checked");
                                TimedUtil.toggleButtonsIsChecked[3] = true;
                            } else {
                                Log.i("MainActivity", "4 unchecked");
                                TimedUtil.toggleButtonsIsChecked[3] = false;
                            }
                        }
                        break;
                        case R.id.Friday: {
                            if (isChecked) {
                                Log.i("MainActivity", "5 checked");
                                TimedUtil.toggleButtonsIsChecked[4] = true;
                            } else {
                                Log.i("MainActivity", "5 unchecked");
                                TimedUtil.toggleButtonsIsChecked[4] = false;
                            }
                        }
                        break;
                        case R.id.Saturday: {
                            if (isChecked) {
                                Log.i("MainActivity", "6 checked");
                                TimedUtil.toggleButtonsIsChecked[5] = true;
                            } else {
                                Log.i("MainActivity", "6 unchecked");
                                TimedUtil.toggleButtonsIsChecked[5] = false;
                            }
                        }
                        break;
                        case R.id.Sunday: {
                            if (isChecked) {
                                Log.i("MainActivity", "7 chcecked");
                                TimedUtil.toggleButtonsIsChecked[6] = true;
                            } else {
                                Log.i("MainActivity", "7 unchecked");
                                TimedUtil.toggleButtonsIsChecked[6] = false;
                            }
                        }
                        break;
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTimeTextView = findViewById(R.id.startTime);
        startTimeTextView.setText("请点此选择时间");
        startTimeTextView.setOnClickListener(onClickListener);

        endTimeTextView = findViewById(R.id.endTime);
        endTimeTextView.setText("请点此选择时间");
        endTimeTextView.setOnClickListener(onClickListener);

        ToggleButton[] toggleButtons = new ToggleButton[7];
        toggleButtons[0] = findViewById(R.id.Monday);
        toggleButtons[1] = findViewById(R.id.Tuesday);
        toggleButtons[2] = findViewById(R.id.Wednesday);
        toggleButtons[3] = findViewById(R.id.Thursday);
        toggleButtons[4] = findViewById(R.id.Friday);
        toggleButtons[5] = findViewById(R.id.Saturday);
        toggleButtons[6] = findViewById(R.id.Sunday);
        for (ToggleButton toggleButton : toggleButtons) {
            toggleButton.setOnCheckedChangeListener(onCheckedChangeListener);
        }

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(onClickListener);

        Button endButton = findViewById(R.id.endButton);
        endButton.setOnClickListener(onClickListener);
    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //计算时间
        if (timeStart.equals("") || timeEnd.equals("")) {
            Toast.makeText(this, "请选择时间段", Toast.LENGTH_SHORT).show();
            return;
        }

        long timestampStart = TimedUtil.computeTime(timeStart);
        long timestampEnd = TimedUtil.computeTime(timeEnd);

        Log.i("MainActivity", String.valueOf(timestampStart));
        Log.i("MainActivity", String.valueOf(timestampEnd));

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
        TimedUtil.date = new Date();
        Intent intent = new Intent();
        intent.putExtra("time", TimedUtil.date);
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
            if (isStartTime) {
                startTimeTextView.setText(time);
                timeStart = time;
            } else {
                endTimeTextView.setText(time);
                timeEnd = time;
            }
        }
    }

}
