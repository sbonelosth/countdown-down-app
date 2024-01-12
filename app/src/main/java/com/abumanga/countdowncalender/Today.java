package com.abumanga.countdowncalender;

import static com.abumanga.countdowncalender.CalendarUtils.selectedDate;

import android.annotation.SuppressLint;
import android.widget.TextView;

import java.time.LocalDate;

public class Today
{
    private  TextView dayOfYear, currentDate;
    public void setToday(TextView dayOf, TextView currentDate)
    {
        this.dayOfYear = dayOf;
        this.currentDate = currentDate;
    }

    @SuppressLint("DefaultLocale")
    public void getToday()
    {
        LocalDate date = LocalDate.now();
        dayOfYear.setText(String.format("Day %d of %d", date.getDayOfYear(), date.lengthOfYear()));
        currentDate.setText(CalendarUtils.formattedToday(date));
    }

    public String getToday(LocalDate selectedDate)
    {
        String currentDateStr = CalendarUtils.formattedToday(selectedDate);
        @SuppressLint("DefaultLocale") String finalToastMsg = String.format("Day %d: %s", selectedDate.getDayOfYear(), currentDateStr);
        return finalToastMsg;
    }
}
