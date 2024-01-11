package com.abumanga.countdowncalender;

import android.widget.TextView;

public class Quotes
{
    public Quotes(TextView eventLabel, String[] quotesArray)
    {
        int rand = (int) (Math.random() * quotesArray.length);
        eventLabel.setText(quotesArray[rand]);
    }
}
