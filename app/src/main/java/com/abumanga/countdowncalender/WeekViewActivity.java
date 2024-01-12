package com.abumanga.countdowncalender;

import static com.abumanga.countdowncalender.CalendarUtils.daysInMonthArray;
import static com.abumanga.countdowncalender.CalendarUtils.daysInWeekArray;
import static com.abumanga.countdowncalender.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    private ListView eventsListView;

    Today today = new Today();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setWeekView();

        LayoutInflater inflater = LayoutInflater.from(this);
        View progressView = inflater.inflate(R.layout.activity_progress, (ViewGroup) getCurrentFocus(), false);
        ProgressObject progressObject = new ProgressObject(progressView);
        progressObject.countDown();

        ViewGroup root = findViewById(R.id.root_weekly);
        root.addView(progressView);
    }

    private void setWeekView()
    {
        GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(this, days, this);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendar_recycler_view);
        monthYearText = findViewById(R.id.month_year_text);
        eventsListView = findViewById(R.id.events_list);
    }

    public void nextWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    public void previousWeekAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter()
    {
        ArrayList<Event> dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventsListView.setAdapter(eventAdapter);
    }


    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        CalendarUtils.selectedDate = date;
        setWeekView();
        Toast.makeText(this, today.getToday(CalendarUtils.selectedDate), Toast.LENGTH_SHORT).show();
    }

    public void dailyAction(View view)
    {
        startActivity(new Intent(this, DailyViewActivity.class));
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}