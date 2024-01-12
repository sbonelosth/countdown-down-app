package com.abumanga.countdowncalender;

import static com.abumanga.countdowncalender.CalendarUtils.selectedDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DailyViewActivity extends AppCompatActivity
{

    private TextView monthDayText;
    private TextView dayOfWeekText;
    private ListView hourlyListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_view);
        initWidgets();

        LayoutInflater inflater = LayoutInflater.from(this);
        View progressView = inflater.inflate(R.layout.progress_views, (ViewGroup) getCurrentFocus(), false);
        ProgressObject progressObject = new ProgressObject(progressView);
        progressObject.countDown();

        ViewGroup root = findViewById(R.id.root_daily);
        root.addView(progressView);
    }

    private void initWidgets()
    {
        monthDayText = findViewById(R.id.month_day_text);
        dayOfWeekText = findViewById(R.id.day_of_week);
        hourlyListView = findViewById(R.id.events_list_hourly);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setDayView();
    }

    private void setDayView()
    {
        monthDayText.setText(CalendarUtils.monthDayFromDate(selectedDate));
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekText.setText(dayOfWeek);
        setDayAdapter();
    }

    private void setDayAdapter()
    {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), hourEventsList());
        hourlyListView.setAdapter(hourAdapter);
        autoScroll();
    }

    private void autoScroll()
    {
        int currentHour = LocalTime.now().getHour();
        hourlyListView.setSelection(currentHour);
    }

    private List<HourEvent> hourEventsList()
    {
        ArrayList<HourEvent> list = new ArrayList<>();

        for (int hour = 0; hour < 24; hour++)
        {
            LocalTime time = LocalTime.of(hour, 0);
            ArrayList<Event> events = Event.eventsForDateTime(selectedDate, time);
            HourEvent hourEvent = new HourEvent(time, events);
            list.add(hourEvent);
        }
        return list;
    }

    public void previousDayAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
        setDayView();
    }

    public void nextDayAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
        setDayView();
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    public void weeklyAction(View view)
    {
        startActivity(new Intent(this, WeekViewActivity.class));
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