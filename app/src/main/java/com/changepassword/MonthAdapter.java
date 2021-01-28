package com.changepassword;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class MonthAdapter extends RecyclerView.Adapter<MonthAdapter.ViewHolder> {

    List<String> month;
    Context context;
    LayoutInflater layoutInflater;


    public MonthAdapter(List<String> month, Context context) {
        this.month = month;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public MonthAdapter(List<String> monthNames) {
        this.month = monthNames;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MonthAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.months_years, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.monthName.setText(month.get(position));

    }

    @Override
    public int getItemCount() {
        return month.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView monthName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            monthName = itemView.findViewById(R.id.months);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    String month_name = month.get(pos);
                    // Toast.makeText(view.getContext(), month_name + CardFragment.yearNumber, Toast.LENGTH_SHORT).show();
                    Date date = new GregorianCalendar(CardFragment.yearNumber, pos, 1).getTime();
                    //  CalendarViewActivity calendarViewActivity = new CalendarViewActivity();
                    ((CalendarViewActivity) context).setCalendar(date);
                    CardFragment.bottom.dismiss();
                }
            });

        }
    }
}
