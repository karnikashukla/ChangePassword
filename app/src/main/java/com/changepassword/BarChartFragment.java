package com.changepassword;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.rgb;

public class BarChartFragment extends Fragment implements OnChartValueSelectedListener {

    private static final int MAX_X_VALUE = 7;
    private static final int MAX_Y_VALUE = 50;
    private static final int MIN_Y_VALUE = 5;
    private static final String group1 = "Actual Products";
    private static final String group2 = "Sale Products";
    private static final String[] DAYS = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
    Toolbar toolbar;
    private BarChart chart;

    public BarChartFragment(MainActivity mainActivity) {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bar_chart, container, false);

        chart = view.findViewById(R.id.bar);
        toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) (getActivity())).setSupportActionBar(toolbar);
        ((AppCompatActivity) (getActivity())).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) (getActivity())).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


        BarData data = createChartData();
        configureChartAppearance();
        prepareChartData(data);
        chart.animateXY(2000, 2000);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void prepareChartData(BarData data) {
        data.setValueTextSize(12f);
        chart.setData(data);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.TOP);
        chart.setOnChartValueSelectedListener(this);
        chart.getBarData().setBarWidth(0.5f);
        float groupSpace = 2f - ((0.2f + 0.5f) * 3);
        chart.groupBars(0, groupSpace, 0.2f);
        chart.setDrawValueAboveBar(true);
        chart.invalidate();
    }

    private void configureChartAppearance() {
        chart.getDescription().setEnabled(false);
        chart.setDrawValueAboveBar(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return DAYS[(int) value];
            }
        });

        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setGranularity(10f);
        axisLeft.setAxisMinimum(0);

        YAxis axisRight = chart.getAxisRight();
        axisRight.setGranularity(10f);
        axisRight.setAxisMinimum(0);
    }

    private BarData createChartData() {
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < MAX_X_VALUE; i++) {
            float x = i;
            float y = new Random().nextFloat() * (MAX_Y_VALUE - MIN_Y_VALUE) + MIN_Y_VALUE;
            values.add(new BarEntry(x, y));
        }

        ArrayList<BarEntry> values_new = new ArrayList<>();
        for (int i = 0; i < MAX_X_VALUE; i++) {
            float x = i;
            float y = new Random().nextFloat() * (MAX_Y_VALUE - MIN_Y_VALUE) + MIN_Y_VALUE;
            values_new.add(new BarEntry(x, y));
        }

        BarDataSet set1 = new BarDataSet(values, group1);
        set1.setColor(rgb(221, 160, 221));

        BarDataSet set2 = new BarDataSet(values_new, group2);
        set2.setColor(rgb(139, 0, 139));

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        BarData data = new BarData(dataSets);

        return data;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        final String x = chart.getXAxis().getValueFormatter().getFormattedValue(e.getX(), chart.getXAxis());
    }

    @Override
    public void onNothingSelected() {

    }
}