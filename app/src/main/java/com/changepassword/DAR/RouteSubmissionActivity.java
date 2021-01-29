package com.changepassword.DAR;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.changepassword.R;

public class RouteSubmissionActivity extends AppCompatActivity {

    Button get;
    CardView fareDetails;
    TextView route;
    CheckBox city1, city2, city3, city4;
    Spinner startCity, endCity;
    String fromClass = "Coming From Base";
    EditText routeName, distance;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_submission);

        get = findViewById(R.id.get_fare);
        fareDetails = findViewById(R.id.fare_detail_card);
        route = findViewById(R.id.route_name);
        startCity = findViewById(R.id.start_city);
        endCity = findViewById(R.id.end_city);
        city1 = findViewById(R.id.city1);
        city2 = findViewById(R.id.city2);
        city3 = findViewById(R.id.city3);
        city4 = findViewById(R.id.city4);
        routeName = findViewById(R.id.custom_route_name);
        distance = findViewById(R.id.distance_text);

        fromClass = getIntent().getStringExtra("From Class");

        if (fromClass.equals("Coming From Copy")) {
            startCity.setEnabled(false);
            endCity.setEnabled(false);
            city1.setEnabled(false);
            city2.setEnabled(false);
            city3.setEnabled(false);
            city4.setEnabled(false);
            route.setText(getIntent().getStringExtra("routeName"));
            fareDetails.setVisibility(View.VISIBLE);
            get.setEnabled(false);
            distance.setEnabled(false);
        } else {
            startCity.setEnabled(true);
            endCity.setEnabled(true);
            city1.setEnabled(true);
            city2.setEnabled(true);
            city3.setEnabled(true);
            city4.setEnabled(true);
//            route.setText(getIntent().getStringExtra("routeName"));
            fareDetails.setVisibility(View.GONE);
            get.setEnabled(true);
            routeName.setEnabled(true);
            distance.setEnabled(true);

            route.setText("Please Select cities");

            startCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!adapterView.getItemAtPosition(i).equals("Start City")) {
                        route.setText(adapterView.getItemAtPosition(i).toString());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            city1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (city1.isChecked()) {
                        Log.d("City", city1.getText().toString());
                        route.append("-" + city1.getText().toString());
                    } else {
                        route.append("");
                    }
                }
            });
            city2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        Log.d("City", city2.getText().toString());
                        route.append("-" + city2.getText().toString());
                    } else {
                        route.append("");
                    }
                }
            });
            city3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        Log.d("City", city3.getText().toString());
                        route.append("-" + city3.getText().toString());
                    } else {
                        route.append("");
                    }
                }
            });
            city4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        Log.d("City", city4.getText().toString());
                        route.append("-" + city4.getText().toString());
                    } else {
                        route.append("");
                    }
                }
            });
            endCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!adapterView.getItemAtPosition(i).equals("End City")) {
                        route.append("-" + adapterView.getItemAtPosition(i).toString());
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            get.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fareDetails.setVisibility(View.VISIBLE);
                }
            });

        }
    }

    public void goToBaseRoute(View view) {
        Intent back = new Intent(RouteSubmissionActivity.this, BaseRouteActivity.class);
        startActivity(back);
        finish();
    }
}