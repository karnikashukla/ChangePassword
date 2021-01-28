package com.changepassword;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CustomerSelectionActivity extends AppCompatActivity {

    public static CheckBox selectAll;
    ArrayList<String> customer = new ArrayList<>();
    ListView customerList;
    List<CustomerModel> customerModelList;
    ArrayAdapter<String> customerAdapter;
    FloatingActionButton add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_selection);

        customerList = findViewById(R.id.customer_list);
        selectAll = findViewById(R.id.select_all);
        add = findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mptMain = new Intent(CustomerSelectionActivity.this, MainFieldOrOtherActivity.class);
                startActivity(mptMain);
            }
        });

        ArrayList<CustomerModel> customerModel = new ArrayList<>();
        CustomerAdapter listAdapter = new CustomerAdapter(CustomerSelectionActivity.this, customerModel);

        for (int i = 1; i <= 4; i++) {
            customer.add("Customer " + i);
        }

        customerAdapter = new ArrayAdapter<String>(
                this,
                R.layout.cusomer_list_item,
                R.id.title,
                customer
        );

        customerModelList = new ArrayList<>();
//        customerModel = new ArrayList<>();

        for (int i = 0; i < customer.size(); i++) {
            CustomerModel model = new CustomerModel();
            model.setName(customer.get(i));
            customerModel.add(model);
        }

        customerList.setAdapter(listAdapter);

    }
}

class CustomerModel {
    String name;
    boolean isChecked = false;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class CustomerAdapter extends BaseAdapter {

    Context context;
    List<CustomerModel> customerModels = null;

    public CustomerAdapter(Context context, ArrayList<CustomerModel> customerModels) {
        this.context = context;
        this.customerModels = customerModels;
    }

    @Override
    public int getCount() {
        return customerModels.size();
    }

    @Override
    public Object getItem(int i) {
        return customerModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.cusomer_list_item, viewGroup, false);

        CheckBox customerItem = view.findViewById(R.id.title_check);
        customerItem.setVisibility(View.VISIBLE);

        TextView name = view.findViewById(R.id.title);
        name.setText(customerModels.get(i).getName());

        CustomerSelectionActivity.selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (CustomerModel customer :
                        customerModels) {
                    customer.setChecked(customer.isChecked == false);

                }
                notifyDataSetChanged();
            }
        });

        customerItem.setChecked(customerModels.get(i).isChecked());

        return view;
    }
}