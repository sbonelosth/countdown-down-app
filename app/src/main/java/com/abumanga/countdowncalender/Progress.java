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

    public void setProgress(TextView remainingMonths, TextView remainingWeeks, TextView remainingDays,
                            TextView remMonthsLabel, TextView remWeeksLabel, TextView remDaysLabel)
    {
        int[] fvalues = { fmonths, fweeks, fdays, fhours, fminutes, fseconds };

        for (int i = 0; i < labels.length; i++)
        {
            if (fvalues[i] > 0)
            {
                if (i < 4)
                {
                    remainingMonths.setText(String.valueOf(fvalues[i]));
                    remMonthsLabel.setText(fvalues[i] != 1 ? labels[i] + pluralize : labels[i]);
                    i++;

                    if (fvalues[i] == 0)
                    {
                        remainingWeeks.setVisibility(View.GONE);
                        remWeeksLabel.setVisibility(View.GONE);
                    }
                    remainingWeeks.setText(String.valueOf(fvalues[i]));
                    remWeeksLabel.setText(fvalues[i] != 1 ? labels[i] + pluralize : labels[i]);
                    i++;

                    if (fvalues[i] == 0)
                    {
                        remainingDays.setVisibility(View.GONE);
                        remDaysLabel.setVisibility(View.GONE);
                    }
                    remainingDays.setText(String.valueOf(fvalues[i]));
                    remDaysLabel.setText(fvalues[i] != 1 ? labels[i] + pluralize : labels[i]);
                    break;
                }
                else if (i == 4)
                {
                    remainingDays.setVisibility(View.GONE);
                    remDaysLabel.setVisibility(View.GONE);

                    remainingMonths.setText(String.valueOf(fvalues[i]));
                    remMonthsLabel.setText(fvalues[i] != 1 ? labels[i] + pluralize : labels[i]);
                    i++;

                    if (fvalues[i] == 0)
                    {
                        remainingWeeks.setVisibility(View.GONE);
                        remWeeksLabel.setVisibility(View.GONE);
                    }

                    remainingWeeks.setText(String.valueOf(fvalues[i]));
                    remWeeksLabel.setText(fvalues[i] != 1 ? labels[i] + pluralize : labels[i]);
                    break;
                }
                else
                {
                    remainingDays.setVisibility(View.GONE);
                    remDaysLabel.setVisibility(View.GONE);
                    remainingWeeks.setVisibility(View.GONE);
                    remWeeksLabel.setVisibility(View.GONE);

                    remainingMonths.setText(String.valueOf(fvalues[i]));
                    remMonthsLabel.setText(fvalues[i] != 1 ? labels[i] + pluralize : labels[i]);
                    break;
                }
            }
        }
    }

    private int getDaysInMonth(int month, int year)
    {
        return new Date(year, month, 0).getDate();
    }
}