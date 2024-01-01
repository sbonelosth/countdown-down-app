package com.abumanga.countdowncalender;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalenderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public final TextView dayOfMonth;
    private final CalenderAdapter.OnItemListener onItemListener;
    public CalenderViewHolder(@NonNull View itemView, CalenderAdapter.OnItemListener onItemListener) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cell_text);
        this.onItemListener = onItemListener;
    }

    @Override
    public void onClick(View view) {
        onItemListener.onItemClick(getAdapterPosition(), (String) dayOfMonth.getText());
    }
}
