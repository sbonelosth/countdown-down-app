package com.abumanga.countdowncalender;

import android.widget.TextView;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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

    int fmonths = 12 - currentMonth;
    int fdays = (int) remainingDuration.toDays() % 7;
    int fweeks = (int) remainingDuration.toDays() / 7;
    int fhours = (int) (remaining / 3600) % 24;
    int fminutes = (int) (remaining / 60) % 60;
    int fseconds = (int) remaining % 60;

    public void setProgress(TextView remainingMonths, TextView remainingWeeks, TextView remainingDays,
                            TextView remMonthsLabel, TextView remWeeksLabel, TextView remDaysLabel)
    {
        int[] fvalues = { fmonths, fweeks, fdays, fhours, fminutes, fseconds };

        for (int i = 0; i < labels.length; i++)
        {
            if (fvalues[i] > 0)
            {
                remainingMonths.setText(String.valueOf(fvalues[i]));
                remMonthsLabel.setText(fvalues[i] > 1 ? labels[i] + pluralize : labels[i]);
                i++;

                remainingWeeks.setText(String.valueOf(fvalues[i]));
                remWeeksLabel.setText(fvalues[i] > 1 ? labels[i] + pluralize : labels[i]);
                i++;

                remainingDays.setText(String.valueOf(fvalues[i]));
                remDaysLabel.setText(fvalues[i] > 1 ? labels[i] + pluralize : labels[i]);
                break;
            }
        }
    }

    public long progress()
    {
        progress = progressDuration.getSeconds();
        return progress;
    }
}
