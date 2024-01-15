package com.abumanga.countdowncalender;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventEditInput;
    private TextView eventEditDate, eventEditTime;
    private Button saveEvent;

    private LocalTime time;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        initWidgets();

        eventEditDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        eventEditTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        eventEditInput.addTextChangedListener(eventTextWatcher);
        eventEditDate.addTextChangedListener(eventTextWatcher);
        eventEditTime.addTextChangedListener(eventTextWatcher);
    }

    private final TextWatcher eventTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            String eventName = eventEditInput.getText().toString().trim();
            saveEvent.setEnabled(!eventName.isEmpty() && !eventEditDate.getText().toString().endsWith("date") && !eventEditTime.getText().toString().endsWith("time"));
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    private void showTimePicker()
    {
        TimePickerDialog dialog = new TimePickerDialog(this, R.style.DateTimePicker, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                time = LocalTime.of(hourOfDay, minute);
                Calendar selectedTime = Calendar.getInstance();
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedTime.set(Calendar.MINUTE, minute);
                Calendar currentTime = Calendar.getInstance();
                if (selectedTime.getTimeInMillis() > currentTime.getTimeInMillis())
                    eventEditTime.setText("Time: " + CalendarUtils.formattedTime(time));
                else
                {
                    Toast.makeText(getApplicationContext(), "Select a future time", Toast.LENGTH_LONG).show();
                }
            }
        }, LocalTime.now().getHour(), LocalTime.now().getMinute(), true);

        dialog.show();
    }

    private void showDatePicker()
    {
        DatePickerDialog dialog = new DatePickerDialog(this, R.style.DateTimePicker, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                CalendarUtils.selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
                if ((CalendarUtils.selectedDate.getDayOfMonth() == LocalDate.now().getDayOfMonth()+1) && CalendarUtils.selectedDate.getMonth().equals(LocalDate.now().getMonth()))
                    eventEditDate.setText("Date: Tomorrow");
                else
                    eventEditDate.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
            }
        }, LocalDate.now().getYear(), LocalDate.now().getMonthValue() - 1, LocalDate.now().getDayOfMonth());

        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }

    private void initWidgets()
    {
        eventEditInput = findViewById(R.id.event_edit_input);
        eventEditDate = findViewById(R.id.event_edit_date);
        eventEditTime = findViewById(R.id.event_edit_time);
        saveEvent = findViewById(R.id.event_edit_save);
    }

    public void saveEventAction(View view)
    {
        String eventName = eventEditInput.getText().toString().trim();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }
}