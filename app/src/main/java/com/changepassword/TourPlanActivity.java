package com.changepassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TourPlanActivity extends AppCompatActivity {

    TextView dateSelected;
    String date;
    ImageView back;
    FloatingActionButton work;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_plan);

        dateSelected = findViewById(R.id.selectedDateTV);
        back = findViewById(R.id.back);
        work = findViewById(R.id.float_work);

        date = getIntent().getStringExtra("SelectedDate");
        Log.d("Date", "Date found : " + date);
        dateSelected.setText(date);
        Log.d("Date", "After Setting date : ");

    }

    public void goBack(View view) {
        Intent calendar = new Intent(TourPlanActivity.this, CalendarViewActivity.class);
        startActivity(calendar);
        finish();
    }

    public void goForOtherWork(View view) {
        Intent field = new Intent(TourPlanActivity.this, MainFieldOrOtherActivity.class);
        field.putExtra("SelectedDate", date);
        startActivity(field);
    }
}