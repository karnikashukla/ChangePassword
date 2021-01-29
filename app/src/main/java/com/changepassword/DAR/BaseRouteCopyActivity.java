package com.changepassword.DAR;

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

import java.util.ArrayList;
import java.util.List;

public class BaseRouteCopyActivity extends AppCompatActivity {

    ListView nameList;
    Spinner territory;
    ArrayList<RouteNameModel> routeNameModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_route_copy);

        territory = findViewById(R.id.territory);
        nameList = findViewById(R.id.route_list);

        territory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (!adapterView.getItemAtPosition(i).equals("Select Territory")) {
                    nameList.setVisibility(View.VISIBLE);
                    RouteNameAdapter routeNameAdapter = new RouteNameAdapter(BaseRouteCopyActivity.this, routeNameModels);

                    for (int j = 1; j <= 3; j++) {
                        RouteNameModel model = new RouteNameModel();
                        model.setName("Route Name " + j);

                        routeNameModels.add(model);
                    }
                    nameList.setAdapter(routeNameAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        nameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent routeSelect = new Intent(BaseRouteCopyActivity.this, RouteSubmissionActivity.class);
                routeSelect.putExtra("routeName", String.valueOf(adapterView.getItemAtPosition(i)));
                routeSelect.putExtra("From Class", "Coming From Copy");
                startActivity(routeSelect);
            }
        });
    }

    public void goToBaseRoute(View view) {
        Intent back = new Intent(BaseRouteCopyActivity.this, BaseRouteActivity.class);
        startActivity(back);
        finish();
    }
}

class RouteNameModel {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class RouteNameAdapter extends BaseAdapter {

    Context context;
    List<RouteNameModel> nameModelList;

    public RouteNameAdapter(Context context, List<RouteNameModel> nameModelList) {
        this.context = context;
        this.nameModelList = nameModelList;
    }

    @Override
    public int getCount() {
        return nameModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return nameModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.route_list, viewGroup, false);

        TextView name;
        name = view.findViewById(R.id.route_name);

        name.setText(nameModelList.get(i).getName());

        notifyDataSetChanged();
        return view;
    }
}