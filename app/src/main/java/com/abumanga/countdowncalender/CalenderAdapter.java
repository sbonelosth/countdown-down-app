package com.abumanga.countdowncalender;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalenderAdapter extends RecyclerView.Adapter<CalenderViewHolder>
{
    private final Context context;
    private final ArrayList<String> daysOfMonth;
    private final OnItemListener onItemListener;

    public CalenderAdapter(Context context, ArrayList<String> daysOfMonth, OnItemListener onItemListener)
    {
        this.context = context;
        this.daysOfMonth = daysOfMonth;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalenderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.calender_cell, viewGroup, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (viewGroup.getHeight() * 0.14);
        return new CalenderViewHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalenderViewHolder holder, int position)
    {
        if (position % 7 == 0)
        {
            holder.dayOfMonth.setTextColor(context.getResources().getColor(R.color.accent));
        }
        holder.dayOfMonth.setText(daysOfMonth.get(position));
    }

    @Override
    public int getItemCount()
    {
        return daysOfMonth.size();
    }

    public interface OnItemListener
    {
        void onItemClick(int position, String dayText);
    }
}
