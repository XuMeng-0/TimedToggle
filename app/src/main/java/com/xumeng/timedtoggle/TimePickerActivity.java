package com.xumeng.timedtoggle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimePickerActivity extends AppCompatActivity {

    private String time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        Intent intent = getIntent();
        Date date = (Date) intent.getExtras().get("time");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        time = simpleDateFormat.format(date);

        Button confirm = findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                //TODO 选择当前时间空指针异常 传递当前时间
//                Date date1 = new Date();
//                try {
//                    date1 = simpleDateFormat.parse(time);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
                intent.putExtra("time",time);
                setResult(0, intent);
                finish();
            }
        });

        TimePicker timePicker = findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        //设置点击事件不弹键盘
        timePicker.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

        //时间更改监听器
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                String[] timeStringArray = time.split(":");
//                timeStringArray[3] = String.valueOf(hourOfDay);
//                timeStringArray[4] = String.valueOf(minute);
//                TimePickerActivity.this.time = String.join(":", timeStringArray);
                TimePickerActivity.this.time = hourOfDay + ":" + minute;
            }
        });
    }
}
