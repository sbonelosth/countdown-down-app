package com.abumanga.countdowncalender;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class ProgressObject
{
    private final TextView remainingMonths;
    private final TextView remainingWeeks;
    private final TextView remainingDays;
    private final TextView remMonthsLabel;
    private final TextView remWeeksLabel;
    private final TextView remDaysLabel;
    private final TextView fadingYear;
    private final TextView pendingYear;
    private final TextView progressText;
    private final ProgressBar progressBar;

    public ProgressObject(View view)
    {
        remainingMonths = view.findViewById(R.id.months_rem);
        remainingWeeks = view.findViewById(R.id.weeks_rem);
        remainingDays = view.findViewById(R.id.days_rem);

        remMonthsLabel = view.findViewById(R.id.months_rem_label);
        remWeeksLabel = view.findViewById(R.id.weeks_rem_label);
        remDaysLabel = view.findViewById(R.id.days_rem_label);

        fadingYear = view.findViewById(R.id.fading_year);
        pendingYear = view.findViewById(R.id.pending_year);
        progressText = view.findViewById(R.id.progress_text);
        progressBar = view.findViewById(R.id.progress_bar);
    }

    public TextView getRemainingMonths() {
        return remainingMonths;
    }

    public TextView getRemainingWeeks() {
        return remainingWeeks;
    }

    public TextView getRemainingDays() {
        return remainingDays;
    }

    public TextView getRemMonthsLabel() {
        return remMonthsLabel;
    }

    public TextView getRemWeeksLabel() {
        return remWeeksLabel;
    }

    public TextView getRemDaysLabel() {
        return remDaysLabel;
    }


    public void countDown()
    {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Handler handler = new Handler(Looper.getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        Progress progress = new Progress();
                        progress.setProgress(ProgressObject.this);
                        Customize customize = new Customize(fadingYear, pendingYear, progressBar, progressText);
                    }
                };
                handler.post(runnable);
            }
        };

        timer.schedule(task, 0, 1000);

    }
}