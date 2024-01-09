package com.abumanga.countdowncalender;

import static android.graphics.Color.rgb;

import android.annotation.SuppressLint;
import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.time.LocalDateTime;

public class Customize extends Progress {
    int yearDays = date.toLocalDate().lengthOfYear();
    double fprogress = (double) (progress) / (864 * yearDays);
    double fprogressRounded = round(fprogress, 1);
    String fprogressStr = String.valueOf(fprogressRounded);

    float pendingRadius = (float) (5 - 0.0499995 * fprogress);
    float fadingRadius = (float) (0.0499995 * fprogress + 0.00005);

    BlurMaskFilter pendingFilter = new BlurMaskFilter(pendingRadius, BlurMaskFilter.Blur.NORMAL);
    BlurMaskFilter fadingFilter = new BlurMaskFilter(fadingRadius, BlurMaskFilter.Blur.NORMAL);

    @SuppressLint("DefaultLocale")
    public Customize(TextView eventLabel, TextView progressText, ProgressBar progressBar, TextView fadingYear, TextView pendingYear, LinearLayout layout)
    {
        progressText.setText((fprogressStr.endsWith("0")) ? fprogressStr.substring(0, fprogressStr.length() - 2) + "% COMPLETE" : fprogressStr + "% COMPLETE");
        progressBar.setProgress((int) fprogress, true);

        fadingYear.setText(String.format("%d", currentYear));
        pendingYear.setText(String.format("%d", currentYear + 1));

        pendingYear.setTextColor(Color.argb((int)(2.55 * (fprogress + 50)), 235, 235, 235));
        fadingYear.setTextColor(Color.argb((int)(255 * (1 - (fprogress / 100))), 235, 235, 235));

        pendingYear.getPaint().setMaskFilter(pendingFilter);
        fadingYear.getPaint().setMaskFilter(fadingFilter);

        LayerDrawable layerDrawable = (LayerDrawable) progressBar.getProgressDrawable();
        Drawable progressFill = layerDrawable.getDrawable(1);

        Drawable eventLabelBg = eventLabel.getBackground();

        colors(progressText, eventLabelBg, progressFill, layout);
    }

    public double round(double value, int precision)
    {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    public void colors(TextView progressText, Drawable eventLabelBg, Drawable progressFill, LinearLayout layout){

        int R, G;
        if (fprogress <= 25)
        {
            R = (int) round(fprogress * 2.0416, 0);
            progressFill.setColorFilter(Color.rgb(R,255, 75), PorterDuff.Mode.SRC_IN);
            eventLabelBg.setColorFilter(Color.rgb(R,255, 75), PorterDuff.Mode.SRC_IN);
            progressText.setTextColor(rgb(R, 255, 75));
            //layout.getBackground().setTint(Color.rgb(R, 255, 75));
        }

        else if (fprogress > 25 && fprogress <= 50)
        {
            R = (int) (fprogress * 5.1);
            progressFill.setColorFilter(Color.rgb(R,255, 75), PorterDuff.Mode.SRC_IN);
            eventLabelBg.setColorFilter(Color.rgb(R,255, 75), PorterDuff.Mode.SRC_IN);
            progressText.setTextColor(rgb(R, 255, 75));
            //layout.getBackground().setTint(Color.rgb(R, 255, 75));
        }

        else
        {
            G = (int) (2 - fprogress / 50) * 255;
            progressFill.setColorFilter(Color.rgb(255, G, 75), PorterDuff.Mode.SRC_IN);
            eventLabelBg.setColorFilter(Color.rgb(255, G, 75), PorterDuff.Mode.SRC_IN);
            progressText.setTextColor(rgb(255, G, 75));
            //layout.getBackground().setTint(Color.rgb(255, G, 75));
        }
    }
}