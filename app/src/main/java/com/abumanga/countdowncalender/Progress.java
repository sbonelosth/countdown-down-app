package com.abumanga.countdowncalender;

import android.view.View;
import android.widget.TextView;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Progress
{
    LocalDateTime date = LocalDateTime.now();
    int currentYear = date.getYear();
    int currentMonth = date.getMonthValue();

    Instant now = Instant.now();
    Instant startOfYear = LocalDateTime.of(currentYear, 1, 1, 0, 0, 0).toInstant(date.atZone(ZoneId.systemDefault()).getOffset());
    Instant endOfYear = LocalDateTime.of(currentYear, 12, 31, 23, 59, 59).toInstant(date.atZone(ZoneId.systemDefault()).getOffset());

    Duration progressDuration = Duration.between(startOfYear, now);
    long progress = progressDuration.getSeconds();

    Duration remainingDuration = Duration.between(now, endOfYear);
    long remaining = remainingDuration.getSeconds();

    String[] labels = {"month", "week", "day", "hour", "minute", "second"};
    String pluralize = "s";

    int remainingDays = getDaysInMonth(date.getMonthValue(), date.getYear()) - date.getDayOfMonth();
    int fmonths = 12 - currentMonth;
    int fweeks = remainingDays / 7;
    int fdays = remainingDays % 7;
    int fhours = (int) (remaining / 3600) % 24;
    int fminutes = (int) (remaining / 60) % 60;
    int fseconds = (int) remaining % 60;

    public void setProgress(ProgressObject progressObject)
    {
        TextView remainingMonths = progressObject.getRemainingMonths();
        TextView remainingWeeks = progressObject.getRemainingWeeks();
        TextView remainingDays = progressObject.getRemainingDays();
        TextView remMonthsLabel = progressObject.getRemMonthsLabel();
        TextView remWeeksLabel = progressObject.getRemWeeksLabel();
        TextView remDaysLabel = progressObject.getRemDaysLabel();

        int[] fvalues = { fmonths, fweeks, fdays, fhours, fminutes, fseconds };
        TextView[] remainingViews = { remainingMonths, remainingWeeks, remainingDays };
        TextView[] remainingLabels = { remMonthsLabel, remWeeksLabel, remDaysLabel };

        for (int i = 0; i < remainingViews.length; i++)
        {
            int value = fvalues[i];
            String label = labels[i];

            if (value == 0)
            {
                remainingViews[i].setVisibility(View.GONE);
                remainingLabels[i].setVisibility(View.GONE);
            }
            else
            {
                remainingViews[i].setText(String.valueOf(value));
                remainingLabels[i].setText((value != 1) ? label + pluralize : label);
            }
        }

        progressObject.getToday();
    }

    private int getDaysInMonth(int month, int year)
    {
        return new Date(year, month, 0).getDate();
    }
}