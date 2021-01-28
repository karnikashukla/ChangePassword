package com.changepassword.DAR;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.changepassword.R;

public class RecordDetailsActivity extends AppCompatActivity {

    String date, city;
    TextView dateTV, cityTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);

        dateTV = findViewById(R.id.date);
        cityTV = findViewById(R.id.city);

        date = getIntent().getStringExtra("SelectedDate");
        city = getIntent().getStringExtra("City");

        dateTV.setText(date);
        cityTV.setText(city);

    }
}