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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.changepassword.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CustomerSelectionActivity extends AppCompatActivity {

    public static boolean isAction = false;
    public static String customer;
    static Context context;
    static String date;
    static String city;
    ListView listView;
    String[] customerName = {"Customer 1", "Customer 2", "Customer 3"};
    String[] customerDesignation = {"Designation 1", "Designation 2", "Designation 3"};
    //    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<CustomerModel> customerModelList = new ArrayList<>();
    FloatingActionButton add;

    public static void GoToHistory() {
        isAction = false;
        Intent history = new Intent(context, RecordHistoryActivity.class);
        history.putExtra("CustomerName", customer);
        context.startActivity(history);
    }

    public static void GoToProducts() {
        isAction = false;
        Intent product = new Intent(context, ProductDetalingActivity.class);
        product.putExtra("CustomerName", customer);
        product.putExtra("SelectedDate", date);
        product.putExtra("SelectedCity", city);
//
        context.startActivity(product);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_selection_dar);

        listView = findViewById(R.id.customer_designation_list);

        add = findViewById(R.id.float_add);

        context = this;
        date = getIntent().getStringExtra("SelectedDate");
        city = getIntent().getStringExtra("SelectedCity");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(CustomerSelectionActivity.this, DarMainActivity.class);
                startActivity(start);
                finish();
            }
        });

        CustomerAdapter customerAdapter = new CustomerAdapter(customerModelList, CustomerSelectionActivity.this);

        for (int i = 0; i < customerName.length; i++) {
            CustomerModel customerModel = new CustomerModel();
            customerModel.setName(customerName[i]);
            customerModel.setDesignation(customerDesignation[i]);
            customerModelList.add(customerModel);
        }

        listView.setAdapter(customerAdapter);

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String itemName;
////                itemName = adapterView.getItemAtPosition(i).toString();
//                Log.d("Selected Customer",customerName[i]);
//                Intent empSelection = new Intent(CustomerSelectionActivity.this, RecordHistoryActivity.class);
//                empSelection.putExtra("CustomerName", customerName[i]);
//          //      empSelection.putExtra("From CustomerSelection", "customer");
//                empSelection.putExtra("SelectedDate",date);
//                empSelection.putExtra("SelectedCity",city);
//                startActivity(empSelection);
//            }
//        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                isAction = true;
                add.setVisibility(View.VISIBLE);
                customerAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }
}

class CustomerModel {
    String name, designation;
    boolean isChecked;

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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}

class CustomerAdapter extends BaseAdapter {

    List<CustomerModel> customerModelList;
    Context context;

    public CustomerAdapter(List<CustomerModel> customerModelList, Context context) {
        this.customerModelList = customerModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return customerModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return customerModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.customer_designation_list, viewGroup, false);

        TextView name, designation;
        CheckBox checkBox = view.findViewById(R.id.title_check);
        ImageView hist = view.findViewById(R.id.history);
        ImageView add = view.findViewById(R.id.add);

        name = view.findViewById(R.id.customer_name);
        designation = view.findViewById(R.id.customer_designation);

        name.setText(customerModelList.get(i).getName());
        designation.setText(customerModelList.get(i).getDesignation());

        if (CustomerSelectionActivity.isAction == true) {
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = (int) view.getTag();
                boolean isChecked = false;
                if (customerModelList.get(currentPosition).isChecked() == false) {
                    isChecked = true;
                }
                customerModelList.get(currentPosition).setChecked(isChecked);

                notifyDataSetChanged();
            }
        });

        hist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                checkBox.setVisibility(View.GONE);
//                CustomerSelectionActivity.isAction = false;
                for (int i = 0; i < customerModelList.size(); i++) {
                    customerModelList.get(i).setChecked(false);
                }
                CustomerSelectionActivity.customer = customerModelList.get(i).getName();
                notifyDataSetChanged();
                CustomerSelectionActivity.GoToHistory();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerSelectionActivity.customer = customerModelList.get(i).getName();
                CustomerSelectionActivity.GoToProducts();
            }
        });


        notifyDataSetChanged();
        return view;
    }

}