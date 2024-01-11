package com.abumanga.countdowncalender;

import android.annotation.SuppressLint;
import android.content.Context;
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

        if (date == null)
            holder.dayOfMonth.setText("");
        else {
            if (position % 7 == 0) {
                holder.dayOfMonth.setTextColor(context.getResources().getColor(R.color.red));
            }
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

            if (date.equals(CalendarUtils.selectedDate)) {
                holder.parentView.setBackground(context.getResources().getDrawable(R.drawable.current_day_bg));
                holder.dayOfMonth.setTextColor(context.getResources().getColor(R.color.blue));
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
        void onItemClick(int position, String dayText, LocalDate date);
    }
}
