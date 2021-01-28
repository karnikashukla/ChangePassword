package com.changepassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MTPCopyActivity extends AppCompatActivity {

    public static ArrayList<String> days = new ArrayList<>();
    ListView dayList;
    ArrayAdapter<String> dayAdapter;
    List<CopyModel> copyModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_t_p_copy);

        dayList = findViewById(R.id.day_list);

        for (int i = 1; i <= 10; i++) {
            days.add("Day " + i);
        }

        dayAdapter = new ArrayAdapter<String>(this,
                R.layout.day_list,
                R.id.day,
                days);

        copyModels = new ArrayList<>();
        ArrayList<CopyModel> arrayList = new ArrayList<>();

        for (int i = 0; i < days.size(); i++) {
            CopyModel model = new CopyModel();
            model.setDays(days.get(i));
            arrayList.add(model);
        }
        CopyAdapter listAdapter = new CopyAdapter(MTPCopyActivity.this, arrayList);
        dayList.setAdapter(listAdapter);

        dayList.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent customer = new Intent(MTPCopyActivity.this, CustomerSelectionActivity.class);
            startActivity(customer);
        });
    }
}

//Model
class CopyModel {
    String days;

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}

//Adapter
class CopyAdapter extends BaseAdapter {

    Context context;
    List<CopyModel> copyModelList = null;

    public CopyAdapter(Context context, ArrayList<CopyModel> copyModelList) {
        this.context = context;
        this.copyModelList = copyModelList;
    }

    @Override
    public int getCount() {
        return copyModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.day_list, viewGroup, false);

        TextView day = view.findViewById(R.id.day);
        day.setText(copyModelList.get(i).getDays());

        return view;
    }
}