package com.abumanga.countdowncalender;

import static com.abumanga.countdowncalender.CalendarUtils.daysInMonthArray;
import static com.abumanga.countdowncalender.CalendarUtils.monthYearFromDate;
import static com.abumanga.countdowncalender.CalendarUtils.selectedDate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    LocalDate date;
    TextView eventLabel;
    TextView dayOfYear, currentDate;

    private boolean exitPressedOnce = false;
    private Handler handler = new Handler();
    @SuppressLint({"SourceLockedOrientationActivity", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        date = LocalDate.now();

        initWidgets();

        String[] quotesArray = getResources().getStringArray(R.array.quotes);
        setQuote(eventLabel, quotesArray);

        selectedDate = LocalDate.now();
        setMonthView();

        LayoutInflater inflater = LayoutInflater.from(this);
        View progressView = inflater.inflate(R.layout.progress_views, (ViewGroup) getCurrentFocus(), false);

        ProgressObject progressObject = new ProgressObject(progressView);
        progressObject.countDown();

        ViewGroup root = findViewById(R.id.root);
        root.addView(progressView);

        calendarRecyclerView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext())
        {
            public void onSwipeRight()
            {
                previousMonth(getCurrentFocus());
            }
            public void onSwipeLeft()
            {
                nextMonth(getCurrentFocus());
            }
        });
    }


    private void setMonthView()
    {
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray();
        CalendarAdapter calendarAdapter = new CalendarAdapter(this, daysInMonth, this);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendar_recycler_view);
        monthYearText = findViewById(R.id.month_year_text);

        eventLabel = findViewById(R.id.event_label);

        dayOfYear = findViewById(R.id.day_of_year);
        currentDate = findViewById(R.id.current_date);
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
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        selectedDate = date;
        setMonthView();
        Toast.makeText(this, CalendarUtils.getSelectedDate(selectedDate), Toast.LENGTH_SHORT).show();
    }

    public void weeklyAction(View view)
    {
        startActivity(new Intent(this, WeekViewActivity.class));
    }

    public void setQuote(TextView eventLabel, String[] quotesArray)
    {
        int rand = (int) (Math.random() * quotesArray.length);
        eventLabel.setText(quotesArray[rand]);
    }

    @Override
    public void onBackPressed()
    {
        if (exitPressedOnce)
        {
            super.onBackPressed();
            return;
        }

        this.exitPressedOnce = true;

        long monthDiff = ChronoUnit.MONTHS.between(LocalDate.now().withDayOfMonth(1), selectedDate.withDayOfMonth(1));
        if (monthDiff > 0)
            selectedDate = selectedDate.minusMonths(monthDiff);
        else if (monthDiff < 0)
            selectedDate = selectedDate.plusMonths(-monthDiff);
        else
            selectedDate = LocalDate.now();

        setMonthView();

        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_LONG).show();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                exitPressedOnce = false;
            }
        }, 3000);
    }

    private void setCurrentMonth(LocalDate selectedDate)
    {

    }

    public void upcomingAction(View view)
    {

    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }
}