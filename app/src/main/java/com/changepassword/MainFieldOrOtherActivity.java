package com.changepassword;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainFieldOrOtherActivity extends AppCompatActivity {

    public static String selectedRoute, selectedCity, selectedEmployee, selectedCustomer;
    public String date;
    TextView dateTv;
    RadioGroup radioGroup;
    RadioButton field, other;
    LinearLayout fieldLayout, firstlayoout, secondlayoout;
    Button next, add;
    TextView selctedDate;
    //    String date;
    Spinner route, employee, city, customer;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_field_or_other);

        dateTv = findViewById(R.id.selectedDateTV);
        date = getIntent().getStringExtra("SelectedDate");
        radioGroup = findViewById(R.id.radio);
        firstlayoout = (LinearLayout) this.getLayoutInflater().inflate(R.layout.activity_field_work, null);
        secondlayoout = (LinearLayout) this.getLayoutInflater().inflate(R.layout.activity_other_work, null);
        fieldLayout = findViewById(R.id.field_work);

        initialLayout();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                field = findViewById(R.id.field);
                other = findViewById(R.id.other);

                switch (i) {
//                        fieldLayout.addView(firstlayoout);

//                        selctedDate.setText(date);

                    case R.id.other:
                        fieldLayout.removeAllViews();
                        fieldLayout.addView(secondlayoout);
//                        add = fieldLayout.findViewById(R.id.add);
//                        add.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                otherWorkValidation();
//                            }
//                        });
                        break;
                    case R.id.field:
                    default:
                        fieldLayout.removeAllViews();
//                        fieldLayout.addView(firstlayoout);
                        route = firstlayoout.findViewById(R.id.base_route);
                        employee = firstlayoout.findViewById(R.id.employee);
                        city = firstlayoout.findViewById(R.id.city);
                        customer = firstlayoout.findViewById(R.id.customer_type);
                        next = firstlayoout.findViewById(R.id.next);

                        fieldLayout.removeAllViews();
                        fieldLayout.addView(firstlayoout);

                        route.setOnItemSelectedListener(new MainFieldOrOtherActivity.CustomRouteItemSelectedListener());
                        city.setOnItemSelectedListener(new MainFieldOrOtherActivity.CustomCityItemSelectedListener());
                        employee.setOnItemSelectedListener(new MainFieldOrOtherActivity.CustomEmployeeItemSelectedListener());
                        customer.setOnItemSelectedListener(new MainFieldOrOtherActivity.CustomCustomerItemSelectedListener());

                        next.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                fieldValidation();
                            }
                        });
                        break;
                }

            }
        });

        dateTv.setText(date);
    }

    private void initialLayout() {
        fieldLayout.removeAllViews();
        route = firstlayoout.findViewById(R.id.base_route);
        employee = firstlayoout.findViewById(R.id.employee);
        city = firstlayoout.findViewById(R.id.city);
        customer = firstlayoout.findViewById(R.id.customer_type);
        next = firstlayoout.findViewById(R.id.next);

        fieldLayout.removeAllViews();
        fieldLayout.addView(firstlayoout);

        route.setOnItemSelectedListener(new MainFieldOrOtherActivity.CustomRouteItemSelectedListener());
        city.setOnItemSelectedListener(new MainFieldOrOtherActivity.CustomCityItemSelectedListener());
        employee.setOnItemSelectedListener(new MainFieldOrOtherActivity.CustomEmployeeItemSelectedListener());
        customer.setOnItemSelectedListener(new MainFieldOrOtherActivity.CustomCustomerItemSelectedListener());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fieldValidation();
            }
        });
    }

    private void fieldValidation() {
        if (selectedRoute.equals("Select Base Route") || selectedCity.equals("Select City")
                || selectedEmployee.equals("Select Employee") || selectedCustomer.equals("Select Customer Type")) {

            Toast.makeText(MainFieldOrOtherActivity.this, "Please Select valid field.", Toast.LENGTH_SHORT).show();
        } else if (selectedRoute.equals("") || selectedCity.equals("")
                || selectedEmployee.equals("") || selectedCustomer.equals("")) {
            Toast.makeText(MainFieldOrOtherActivity.this, "Please Select valid field.", Toast.LENGTH_SHORT).show();
        } else {
            Intent list = new Intent(MainFieldOrOtherActivity.this, FieldWorkListActivity.class);
            list.putExtra("SelectedDate", date);
            list.putExtra("SelectedCity", selectedCity);
            startActivity(list);
        }
    }

    public void goBack(View view) {
        Intent calendar = new Intent(MainFieldOrOtherActivity.this, TourPlanActivity.class);
        startActivity(calendar);
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