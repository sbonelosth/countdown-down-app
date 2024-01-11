package com.abumanga.countdowncalender;

import static com.abumanga.countdowncalender.CalendarUtils.daysInMonthArray;
import static com.abumanga.countdowncalender.CalendarUtils.monthYearFromDate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    LocalDateTime date;
    int yearDays;
    int progressDay;
    Today today = new Today();
    TextView eventLabel;

    TextView dayOfYear, currentDate;
    TextView remainingMonths, remainingWeeks, remainingDays;
    TextView remMonthsLabel, remWeeksLabel, remDaysLabel;

    TextView fadingYear, pendingYear, progressText;
    ProgressBar progressBar;

    @SuppressLint({"SourceLockedOrientationActivity", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        eventLabel = findViewById(R.id.event_label);

        date = LocalDateTime.now();
        yearDays = date.toLocalDate().lengthOfYear();
        progressDay = date.getDayOfYear();

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

        String[] quotesArray = getResources().getStringArray(R.array.quotes);
        Quotes quotes = new Quotes(eventLabel, quotesArray);

        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        calendarRecyclerView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext())
        {
            public void onSwipeRight()
            {
                previousMonth(getCurrentFocus());
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft()
            {
                nextMonth(getCurrentFocus());
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setMonthView()
    {
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(this, daysInMonth, this);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendar_recycler_view);
        monthYearText = findViewById(R.id.month_year_text);

        dayOfYear = findViewById(R.id.day_of);
        currentDate = findViewById(R.id.current_date);

        remainingMonths = findViewById(R.id.months_rem);
        remainingWeeks = findViewById(R.id.weeks_rem);
        remainingDays = findViewById(R.id.days_rem);

        remMonthsLabel = findViewById(R.id.months_rem_label);
        remWeeksLabel = findViewById(R.id.weeks_rem_label);
        remDaysLabel = findViewById(R.id.days_rem_label);

        fadingYear = findViewById(R.id.fading_year);
        pendingYear = findViewById(R.id.pending_year);
        progressText = findViewById(R.id.progress_text);
        progressBar = findViewById(R.id.progress_bar);
    }

    public void previousMonth(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonth(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @SuppressLint("DefaultLocale")
    public void countDown()
    {
        initWidgets();
        today.setToday(dayOfYear, currentDate);
        today.getToday(date, progressDay, yearDays);

        Progress progressObj = new Progress();
        progressObj.setProgress(remainingMonths, remainingWeeks, remainingDays, remMonthsLabel, remWeeksLabel, remDaysLabel);

        Customize customize = new Customize(fadingYear, pendingYear, progressBar, progressText);
    }

    @Override
    public void onItemClick(int position, String dayText, LocalDate date)
    {
        if (!dayText.equals("") || date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
            String msg = today.getToday(CalendarUtils.selectedDate, dayText);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void weeklyAction(View view)
    {
        startActivity(new Intent(this, WeekViewActivity.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}