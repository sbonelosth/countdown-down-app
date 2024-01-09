package com.abumanga.countdowncalender;

import android.annotation.SuppressLint;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Today {
    private  TextView dayOf, currentDate;
    public void setToday(TextView dayOf, TextView currentDate)
    {
        this.dayOf = dayOf;
        this.currentDate = currentDate;
    }

    @SuppressLint("DefaultLocale")
    public void getToday(LocalDateTime date, int progressDay, int yearDays)
    {
        dayOf.setText(String.format("Day %d of %d", progressDay, yearDays));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM");
        String currentDateStr = date.format(formatter);
        currentDate.setText(currentDateStr);
    }

    public String getToday(LocalDate selectedDate, String dayText)
    {
        int year = selectedDate.getYear();
        int month = selectedDate.getMonthValue();
        LocalDateTime date = LocalDateTime.of(year, month, Integer.parseInt(dayText), 23, 59, 59);
        int progressDay = date.getDayOfYear();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM");
        String currentDateStr = date.format(formatter);
        @SuppressLint("DefaultLocale") String finalToastMsg = String.format("Day %d: %s", progressDay, currentDateStr);
        return finalToastMsg;
    }
}
