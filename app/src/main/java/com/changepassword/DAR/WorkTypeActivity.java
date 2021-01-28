package com.changepassword.DAR;

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
import androidx.cardview.widget.CardView;

import com.changepassword.R;

public class WorkTypeActivity extends AppCompatActivity {

    public static String selectedRoute, selectedCity, selectedEmployee, selectedCustomer;
    public String date;
    TextView dateTv;
    RadioGroup radioGroup;
    RadioButton field, other;
    LinearLayout fieldLayout, firstlayoout, secondlayoout;
    Button next, nextOtherWork;
    TextView selctedDate;
    String cityText, reasonText;
    Spinner route, employee, city, customer;
    Spinner cityForOther, reason;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_type);

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
                        nextOtherWork = secondlayoout.findViewById(R.id.next);
                        cityForOther = secondlayoout.findViewById(R.id.city_for_other);
                        reason = secondlayoout.findViewById(R.id.reason);
                        fieldLayout.addView(secondlayoout);

                        reason.setOnItemSelectedListener(new CustomReasonSelectedListener());
                        cityForOther.setOnItemSelectedListener(new CustomCityItemSelectedListener());
                        nextOtherWork.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent workSelection = new Intent(WorkTypeActivity.this, WorkWithSelectionActivity.class);
                                workSelection.putExtra("SelectedDate", date);
                                workSelection.putExtra("City", cityText);
                                workSelection.putExtra("Reason", reasonText);
                                workSelection.putExtra("From WorkTypeActivity", "other");
                                startActivity(workSelection);
                            }
                        });
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
                        CardView customerCard = firstlayoout.findViewById(R.id.customer_card_view);
                        customerCard.setVisibility(View.GONE);
                        customer.setVisibility(View.GONE);

                        fieldLayout.removeAllViews();
                        fieldLayout.addView(firstlayoout);

                        route.setOnItemSelectedListener(new WorkTypeActivity.CustomRouteItemSelectedListener());
                        city.setOnItemSelectedListener(new WorkTypeActivity.CustomCityItemSelectedListener());
                        employee.setOnItemSelectedListener(new WorkTypeActivity.CustomEmployeeItemSelectedListener());
//                        customer.setOnItemSelectedListener(new WorkTypeActivity.CustomCustomerItemSelectedListener());

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
        CardView customerCard = firstlayoout.findViewById(R.id.customer_card_view);
        customerCard.setVisibility(View.GONE);
        customer.setVisibility(View.GONE);

        fieldLayout.removeAllViews();
        fieldLayout.addView(firstlayoout);

        route.setOnItemSelectedListener(new WorkTypeActivity.CustomRouteItemSelectedListener());
        city.setOnItemSelectedListener(new WorkTypeActivity.CustomCityItemSelectedListener());
        employee.setOnItemSelectedListener(new WorkTypeActivity.CustomEmployeeItemSelectedListener());
//        customer.setOnItemSelectedListener(new WorkTypeActivity.CustomCustomerItemSelectedListener());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fieldValidation();
            }
        });
    }

    private void fieldValidation() {
        if (selectedRoute.equals("Select Base Route") || selectedCity.equals("Select City")
                || selectedEmployee.equals("Select Employee")) {

            Toast.makeText(WorkTypeActivity.this, "Please Select valid field.", Toast.LENGTH_SHORT).show();
        } else if (selectedRoute.equals("") || selectedCity.equals("")
                || selectedEmployee.equals("")) {
            Toast.makeText(WorkTypeActivity.this, "Please Select valid field.", Toast.LENGTH_SHORT).show();
        } else {
            Intent select = new Intent(WorkTypeActivity.this, WorkWithSelectionActivity.class);
            select.putExtra("SelectedDate", date);
            select.putExtra("SelectedCity", selectedCity);
            select.putExtra("FieldWork", "Field");
            startActivity(select);
        }
    }

    public void goBack(View view) {
        Intent calendar = new Intent(WorkTypeActivity.this, DarMainActivity.class);
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

            cityText = adapterView.getItemAtPosition(i).toString();
            Log.d("item", cityText);
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

    private class CustomReasonSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            reasonText = adapterView.getItemAtPosition(i).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}