package com.abumanga.countdowncalender;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements CalenderAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calenderRecyclerView;
    private LocalDate selectedDate;

    LocalDateTime date;
    int yearDays;
    int progressDay;
    Today today = new Today();
    TextView eventLabel;

    TextView dayOfYear, currentDate;
    TextView remainingMonths, remainingWeeks, remainingDays;
    TextView remMonthsLabel, remWeeksLabel, remDaysLabel;
    TextView fadingYear, pendingYear;
    TextView progressText;
    ProgressBar progressBar;
    LinearLayout layout;



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

        Quotes quotes = new Quotes(eventLabel);

        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();

        calenderRecyclerView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            public void onSwipeRight()
            {
                nextMonth(getCurrentFocus());
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft()
            {
                previousMonth(getCurrentFocus());
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setMonthView()
    {
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        CalenderAdapter calenderAdapter = new CalenderAdapter(this, daysInMonth, this);
        calenderRecyclerView.setLayoutManager(layoutManager);
        calenderRecyclerView.setAdapter(calenderAdapter);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        int rows = 42;
        for (int i = 1; i <= rows; i++)
        {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                if (i > 35 && daysInMonth + dayOfWeek <= 35) rows = 35;
                else
                {
                    rows = 42;
                    daysInMonthArray.add("");
                }
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");
        return date.format(formatter);
    }

    private void initWidgets()
    {
        calenderRecyclerView = findViewById(R.id.calendar_recycler_view);
        monthYearText = findViewById(R.id.month_year_text);

        dayOfYear = findViewById(R.id.day_of);
        currentDate = findViewById(R.id.current_date);

        remainingMonths = findViewById(R.id.months_rem);
        remainingWeeks = findViewById(R.id.weeks_rem);
        remainingDays = findViewById(R.id.days_rem);

        remMonthsLabel = findViewById(R.id.months_rem_label);
        remWeeksLabel = findViewById(R.id.weeks_rem_label);
        remDaysLabel = findViewById(R.id.days_rem_label);

        fadingYear = findViewById(R.id.dying_year);
        pendingYear = findViewById(R.id.rising_year);

        progressText = findViewById(R.id.progress_text);
        progressBar = findViewById(R.id.progressBar);

        layout = findViewById(R.id.parent_layout);

    }

    public void previousMonth(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonth(View view)
    {
        selectedDate = selectedDate.plusMonths(1);
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

        Customize customize = new Customize(eventLabel, progressText, progressBar, fadingYear, pendingYear, layout);
    }

    @Override
    public void onItemClick(int position, String dayText)
    {
        if (!dayText.equals(""))
        {
            String msg = today.getToday(selectedDate, dayText);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void weeklyAction(View view)
    {
        startActivity(new Intent(this, WeekViewActivity.class));
    }
}