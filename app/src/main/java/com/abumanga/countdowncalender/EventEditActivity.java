package com.abumanga.countdowncalender;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventEditInput;
    private TextView eventEditDate, eventEditTime;

    private LocalTime time;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        initWidgets();
        time = LocalTime.now();
        eventEditDate.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventEditTime.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets()
    {
        eventEditInput = findViewById(R.id.event_edit_input);
        eventEditDate = findViewById(R.id.event_edit_date);
        eventEditTime = findViewById(R.id.event_edit_time);
    }

    public void saveEventAction(View view)
    {
        String eventName = eventEditInput.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }
}