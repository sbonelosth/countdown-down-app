package com.abumanga.countdowncalender;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
{
    CalendarView calendarView;
    Calendar calendar;
    String selectedDay;
    TextView eventLabel;

    LocalDateTime date;
    int yearDays;
    int progressDay;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        calendarView = findViewById(R.id.calendarView);
        calendar = Calendar.getInstance();
        eventLabel = findViewById(R.id.event_label);

        date = LocalDateTime.now();
        yearDays = date.toLocalDate().lengthOfYear();
        progressDay = date.getDayOfYear();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @SuppressLint("DefaultLocale")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day)
            {
                selectedDay = String.format("%d-%d-%d", day, month+1, year);
                Toast.makeText(MainActivity.this, selectedDay, Toast.LENGTH_SHORT).show();
            }
        });

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        countDown();
                    }
                };
                handler.post(runnable);
            }
        };

        timer.schedule(task, 0, 1000);

        Quotes quotes = new Quotes(eventLabel);
    }

    @SuppressLint("DefaultLocale")
    public void countDown()
    {
        TextView dayOf = findViewById(R.id.day_of);
        TextView currentDate = findViewById(R.id.current_date);

        Today today = new Today();
        today.setToday(dayOf, currentDate);
        today.getToday(date, progressDay, yearDays);

        TextView remainingMonths = findViewById(R.id.months_rem);
        TextView remainingWeeks = findViewById(R.id.weeks_rem);
        TextView remainingDays = findViewById(R.id.days_rem);

        TextView remMonthsLabel = findViewById(R.id.months_rem_label);
        TextView remWeeksLabel = findViewById(R.id.weeks_rem_label);
        TextView remDaysLabel = findViewById(R.id.days_rem_label);

        Progress progressObj = new Progress();
        progressObj.setProgress(remainingMonths, remainingWeeks, remainingDays, remMonthsLabel, remWeeksLabel, remDaysLabel);

        TextView fadingYear, pendingYear;

        fadingYear = findViewById(R.id.dying_year);
        pendingYear = findViewById(R.id.rising_year);

        TextView progressText = findViewById(R.id.progress_text);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        Customize customize = new Customize(eventLabel, progressText, progressBar, fadingYear, pendingYear);
    }
}