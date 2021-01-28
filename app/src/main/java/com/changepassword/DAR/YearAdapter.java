package com.changepassword.DAR;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.changepassword.CardFragment;
import com.changepassword.R;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class YearAdapter extends RecyclerView.Adapter<YearAdapter.ViewHolder> {

    List<String> year;
    Context context;
    LayoutInflater layoutInflater;

    public YearAdapter(List<String> year, Context context) {
        this.year = year;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.months_years, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.monthName.setText(year.get(position));

    }

    @Override
    public int getItemCount() {
        return year.size();
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
                    String month_name = year.get(pos);
                    // Toast.makeText(view.getContext(), month_name + CardFragment.yearNumber, Toast.LENGTH_SHORT).show();
                    Date date = new GregorianCalendar(CardFragment.yearNumber, pos, 1).getTime();
                    //  CalendarViewActivity calendarViewActivity = new CalendarViewActivity();
                    ((DarMainActivity) context).setCalendar(date);
                    CardFragment.bottom.dismiss();
                }
            });

        }
    }
}
