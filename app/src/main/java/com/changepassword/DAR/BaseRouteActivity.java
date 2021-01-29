package com.changepassword.DAR;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.changepassword.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class BaseRouteActivity extends AppCompatActivity {

    Spinner position;
    ListView routeList;
    ArrayList<RouteModel> routeModelArrayList = new ArrayList<>();
    FloatingActionButton add, copy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_route);

        position = findViewById(R.id.position);
        routeList = findViewById(R.id.route_list);
        add = findViewById(R.id.float_add);
        copy = findViewById(R.id.float_copy);

        position.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).equals("Select Position")) {
                    routeList.setVisibility(View.GONE);
                } else {
                    routeList.setVisibility(View.VISIBLE);

                    RouteAdapter routeAdapter = new RouteAdapter(BaseRouteActivity.this, routeModelArrayList);

                    for (int j = 1; j <= 3; j++) {
                        RouteModel model = new RouteModel();
                        model.setRoute("Route " + j);
                        model.setFromCity("From City " + j);
                        model.setToCity("To City " + j);
                        model.setDistance("Distance " + j);
                        model.setAllowanceType("Allowance Type " + j);
                        model.setAmount("Amount " + j);
                        model.setFare("Fare " + j);
                        model.setStatus("Status " + j);

                        routeModelArrayList.add(model);
                    }
                    routeList.setAdapter(routeAdapter);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent route = new Intent(BaseRouteActivity.this, RouteSubmissionActivity.class);
                route.putExtra("From Class", "Coming From Base");
                startActivity(route);
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent route = new Intent(BaseRouteActivity.this, BaseRouteCopyActivity.class);
                startActivity(route);
            }
        });
    }
}

class RouteModel {
    String route, fromCity, toCity, distance, allowanceType, amount, fare, status;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAllowanceType() {
        return allowanceType;
    }

    public void setAllowanceType(String allowanceType) {
        this.allowanceType = allowanceType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

class RouteAdapter extends BaseAdapter {

    Context context;
    List<RouteModel> routeModels;

    public RouteAdapter(Context context, List<RouteModel> routeModels) {
        this.context = context;
        this.routeModels = routeModels;
    }

    @Override
    public int getCount() {
        return routeModels.size();
    }

    @Override
    public Object getItem(int i) {
        return routeModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.base_route_list, viewGroup, false);

        TextView route, fromCity, toCity, distance, allowanceType, amount, fare, status;

        route = view.findViewById(R.id.route);
        fromCity = view.findViewById(R.id.from_city);
        toCity = view.findViewById(R.id.to_city);
        distance = view.findViewById(R.id.distance);
        allowanceType = view.findViewById(R.id.allowance_type);
        amount = view.findViewById(R.id.amount);
        fare = view.findViewById(R.id.fare);
        status = view.findViewById(R.id.status);

        route.setText(routeModels.get(i).getRoute());
        fromCity.setText(routeModels.get(i).getFromCity());
        toCity.setText(routeModels.get(i).getToCity());
        distance.setText(routeModels.get(i).getDistance());
        allowanceType.setText(routeModels.get(i).getAllowanceType());
        amount.setText(routeModels.get(i).getAmount());
        fare.setText(routeModels.get(i).getFare());
        status.setText(routeModels.get(i).getStatus());

        notifyDataSetChanged();
        return view;
    }
}