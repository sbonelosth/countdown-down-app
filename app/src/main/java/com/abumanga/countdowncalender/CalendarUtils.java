package com.abumanga.countdowncalender;

import android.annotation.SuppressLint;

import androidx.recyclerview.widget.GridLayoutManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarUtils
{
    public static LocalDate selectedDate;

    public static String formattedDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    public static String formattedTime(LocalTime time)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return time.format(formatter);
    }

    public static String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static String monthDayFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd");
        return date.format(formatter);
    }

    public static String formattedToday(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM");
        return date.format(formatter);
    }

    @SuppressLint("DefaultLocale")
    public static String getToday(LocalDate selectedDate)
    {
        String currentDateStr = formattedToday(selectedDate);
        return String.format("Day %d: %s", selectedDate.getDayOfYear(), currentDateStr);
    }
    public static ArrayList<LocalDate> daysInMonthArray()
    {
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(selectedDate);
        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate previousMonth = selectedDate.minusMonths(1);
        LocalDate nextMonth = selectedDate.plusMonths(1);

        YearMonth previousYearMonth = YearMonth.from(previousMonth);
        int previousDaysInMonth = previousYearMonth.lengthOfMonth();


        LocalDate firstOfMonth = CalendarUtils.selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        int rows = 42;
        for (int i = 1; i <= rows; i++)
        {
            if (i <= dayOfWeek)
            {
                if (i > 35 && daysInMonth + dayOfWeek <= 35) rows = 35;
                else
                {
                    rows = 42;
                    daysInMonthArray.add(LocalDate.of(previousMonth.getYear(), previousMonth.getMonthValue(), previousDaysInMonth + i - dayOfWeek));
                }
            }
            else if (i > daysInMonth + dayOfWeek)
                daysInMonthArray.add(LocalDate.of(nextMonth.getYear(), nextMonth.getMonthValue(), i - dayOfWeek - daysInMonth));
            else
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonthValue(), i - dayOfWeek));
        }
        return daysInMonthArray;
    }

    public static ArrayList<LocalDate> daysInWeekArray(LocalDate selectedDate)
    {
        ArrayList<LocalDate> days = new ArrayList<>();
        LocalDate current = sundayForDate(selectedDate);
        LocalDate endDate = current.plusWeeks(1);

        while (current.isBefore(endDate))
        {
            days.add(current);
            current = current.plusDays(1);
        }
        return days;
    }

    private static LocalDate sundayForDate(LocalDate current)
    {
        LocalDate oneWeekAgo = current.minusWeeks(1);

        while (current.isAfter(oneWeekAgo))
        {
            if (current.getDayOfWeek() == DayOfWeek.SUNDAY) return current;
            current = current.minusDays(1);
        }

        return null;
    }
}
