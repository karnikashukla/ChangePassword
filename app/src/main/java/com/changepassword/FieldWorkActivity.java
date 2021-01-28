package com.changepassword;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FieldWorkActivity extends AppCompatActivity {

    public static String selectedRoute, selectedCity, selectedEmployee, selectedCustomer;
    TextView selctedDate;
    String date;
    Spinner route, employee, city, customer;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_work);

        selctedDate = findViewById(R.id.selectedDateTV);
        route = findViewById(R.id.base_route);
        employee = findViewById(R.id.employee);
        city = findViewById(R.id.city);
        customer = findViewById(R.id.customer_type);
        next = findViewById(R.id.next);

        route.setOnItemSelectedListener(new CustomRouteItemSelectedListener());
        city.setOnItemSelectedListener(new CustomCityItemSelectedListener());
        employee.setOnItemSelectedListener(new CustomEmployeeItemSelectedListener());
        customer.setOnItemSelectedListener(new CustomCustomerItemSelectedListener());

        selctedDate.setText(date);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedRoute.equals("Select Base Route") || selectedCity.equals("Select City")
                        || selectedEmployee.equals("Select Employee") || selectedCustomer.equals("Select Customer Type")) {

                    Toast.makeText(FieldWorkActivity.this, "Please Select valid field.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void goBack(View view) {
        Intent tour = new Intent(FieldWorkActivity.this, TourPlanActivity.class);
        tour.putExtra("SelectedDate", date);
        startActivity(tour);
        finish();
    }

    private class CustomRouteItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selectedRoute = adapterView.getItemAtPosition(i).toString();
            Log.d("item", selectedRoute);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private class CustomCityItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selectedCity = adapterView.getItemAtPosition(i).toString();
            Log.d("item", selectedCity);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private class CustomEmployeeItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selectedEmployee = adapterView.getItemAtPosition(i).toString();
            Log.d("item", selectedEmployee);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private class CustomCustomerItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            selectedCustomer = adapterView.getItemAtPosition(i).toString();
            Log.d("item", selectedCustomer);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}