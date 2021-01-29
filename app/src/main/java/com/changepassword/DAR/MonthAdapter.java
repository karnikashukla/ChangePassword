package com.changepassword.DAR;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.changepassword.R;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
            final int[] i = {DarMainActivity.currentYear};
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    String month_name = month.get(pos);
                    // Toast.makeText(view.getContext(), month_name + CardFragment.yearNumber, Toast.LENGTH_SHORT).show();
                    Date date = new GregorianCalendar(i[0], pos, 1).getTime();
                    //  CalendarViewActivity calendarViewActivity = new CalendarViewActivity();

                    String flag = CardFragment.flag;
                    if (flag.equals("month")) {
                        ((DarMainActivity) context).setCalendar(date);
                    } else {
                        CardFragment.flag = "month";
                        i[0] = Integer.parseInt(month.get(pos));
                        DarMainActivity.currentYear = i[0];
                        Date date1 = new GregorianCalendar(i[0], DarMainActivity.monthNumber - 1, 1).getTime();
                        ((DarMainActivity) context).setCalendar(date1);
                    }
                    com.changepassword.DAR.CardFragment.bottom.dismiss();
                }
            });

        }
    }
}
