package com.abumanga.countdowncalender;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.CalendarView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
    TextView eventLabel;

    @SuppressLint("SourceLockedOrientationActivity")
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
    }

    private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        CalenderAdapter calenderAdapter = new CalenderAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
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

        for (int i = 1; i <= 42 ; i++)
        {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
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
    }

    public void previousMonth(View view)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonth(View view)
    {
        selectedDate.plusMonths(1);
        setMonthView();
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

        LinearLayout layout = findViewById(R.id.parent_layout);
        Customize customize = new Customize(eventLabel, progressText, progressBar, fadingYear, pendingYear, layout);
    }

    @Override
    public void onItemClick(int position, String dayText)
    {
        if (dayText.equals(""))
        {
            String message = "Selected Date: " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}