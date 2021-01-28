package com.changepassword;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CardFragment extends BottomSheetDialogFragment {

    public static Dialog bottom;
    public static int yearNumber;
    RecyclerView monthList;
    RecyclerView yearList;
    TextView year;
    SimpleDateFormat simpleDateFormat;
    LinearLayout layout_left, layout_right;

    public CardFragment() {
    }

    public static CardFragment newInstance() {
        CardFragment fragment = new CardFragment();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cardview_bottom_sheet, container, false);
        layout_left = view.findViewById(R.id.layout_left);
        layout_right = view.findViewById(R.id.layout_right);

        monthList = view.findViewById(R.id.month_list);
        year = view.findViewById(R.id.text_year);
        simpleDateFormat = new SimpleDateFormat("YYYY");
        Date c = Calendar.getInstance().getTime();

        Calendar cal = Calendar.getInstance();
        cal.setTime(c);
//                cal.add(Calendar.YEAR, 1);
        SimpleDateFormat yearFormat = new SimpleDateFormat("YYYY");
        simpleDateFormat = new SimpleDateFormat("MMMM");
        year.setText(String.valueOf(CalendarViewActivity.currentYear));


        yearNumber = cal.get(Calendar.YEAR);
        layout_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yearNumber > (cal.get(Calendar.YEAR)) + 5) {
                    Toast.makeText(view.getContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                } else {
                    year.setText(String.valueOf(yearNumber + 1));
                    yearNumber = yearNumber + 1;
                }
            }
        });

        layout_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (yearNumber < (cal.get(Calendar.YEAR)) - 10) {
                    Toast.makeText(view.getContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                } else {
                    year.setText(String.valueOf(yearNumber - 1));
                    yearNumber = yearNumber - 1;
                }
            }
        });


        MonthAdapter adapter;
        List<String> monthNames;

        monthNames = new ArrayList<String>();
//        Month month;
        for (Month month : Month.values()
        ) {
            monthNames.add(month.toString());
        }
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        monthList.setLayoutManager(gridLayoutManager);
        adapter = new MonthAdapter(monthNames, getActivity());
//        int height = view.getHeight()/2;
//        view.setMinimumHeight(height);
        //        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
//        monthList.setLayoutManager(gridLayoutManager);
        monthList.setAdapter(adapter);

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                YearAdapter yearAdapter = null;
                ArrayList<String> years;

                years = new ArrayList<String>();

                for (int i = yearNumber; yearNumber >= yearNumber + 5; yearNumber++) {
                    years.add(String.valueOf(yearNumber));
                }
                for (int i = yearNumber; yearNumber < yearNumber - 10; yearNumber++) {
                    years.add(String.valueOf(yearNumber));
                }

                yearAdapter = new YearAdapter(years, getActivity());

                monthList.setAdapter(yearAdapter);
//                RecyclerView.LayoutManager gridLayoutManager1 = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
//                yearList.setLayoutManager(gridLayoutManager1);
//                yearAdapter = new YearAdapter(years, getActivity());
//                yearList.setAdapter(yearAdapter);

            }
        });


        return view;
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        bottom = dialog;
        View view = View.inflate(getContext(), R.layout.cardview_bottom_sheet, null);
        dialog.setContentView(view);

    }
}
