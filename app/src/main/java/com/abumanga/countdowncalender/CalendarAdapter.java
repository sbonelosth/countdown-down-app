package com.abumanga.countdowncalender;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final Context context;
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    public CalendarAdapter(Context context, ArrayList<LocalDate> days, OnItemListener onItemListener)
    {
        this.context = context;
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.calender_cell, viewGroup, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();

        if (days.size() > 15) // month view
            layoutParams.height = (int) (viewGroup.getHeight() * 0.15);
        else // week view
            layoutParams.height = (int) (viewGroup.getHeight());


        return new CalendarViewHolder(view, onItemListener, days);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        final LocalDate date = days.get(position);
        final int currentDay = LocalDate.now().getDayOfMonth();
        final int currentMonth = LocalDate.now().getMonthValue();
        final int currentYear = LocalDate.now().getYear();

        holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

        if (date.equals(CalendarUtils.selectedDate))
        {
            holder.parentView.setBackground(context.getResources().getDrawable(R.drawable.current_day_bg));
        }

        if (date.getMonth().equals(CalendarUtils.selectedDate.getMonth()))
        {
            holder.dayOfMonth.setTextColor(context.getResources().getColor(R.color.white));
            if (position % 7 == 0)
            {
                holder.dayOfMonth.setTextColor(context.getResources().getColor(R.color.red));
            }
            if (date.getDayOfMonth() == currentDay && date.getMonthValue() == currentMonth && date.getYear() == currentYear)
            {
                holder.dayOfMonth.setTextColor(context.getResources().getColor(R.color.blue));
            }
        }
        else
        {
            holder.dayOfMonth.setTextColor(Color.GRAY);
            if (position % 7 == 0)
            {
                holder.dayOfMonth.setTextColor(context.getResources().getColor(R.color.red_transparent));
            }
        }

    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}
