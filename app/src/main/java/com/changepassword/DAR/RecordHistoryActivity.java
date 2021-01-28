package com.changepassword.DAR;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.changepassword.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class RecordHistoryActivity extends AppCompatActivity {

    TextView date, city, name;
    String dateString, cityString, nameString;
    FloatingActionButton product;
    ImageView back;
    ArrayList<RecordModel> recordModelList = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_history);

        date = findViewById(R.id.date);
        city = findViewById(R.id.city);
        name = findViewById(R.id.customer_name);
        product = findViewById(R.id.product);
        back = findViewById(R.id.back);
        listView = findViewById(R.id.history_list);

        dateString = getIntent().getStringExtra("SelectedDate");
        cityString = getIntent().getStringExtra("City");
        nameString = getIntent().getStringExtra("CustomerName");

        date.setText(dateString);
        city.setText(cityString);
        name.setText(nameString);

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productActivity = new Intent(RecordHistoryActivity.this, ProductDetalingActivity.class);
                startActivity(productActivity);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToWorkSelect = new Intent(RecordHistoryActivity.this, WorkWithSelectionActivity.class);
                startActivity(backToWorkSelect);
                finish();
            }
        });


        RecordAdapter recordAdapter = new RecordAdapter(recordModelList, RecordHistoryActivity.this);

        for (int i = 0; i <= 2; i++) {
            RecordModel model = new RecordModel();
            model.setDate(dateString + " " + i);
            model.setMonthlyPotential("Monthly Potential" + " " + i);
            model.setProductGroup("Product Group" + " " + i);
            model.setProductPOB("Product POB" + " " + i);
            model.setProductQuantity("Product Quantity" + " " + i);
            model.setRemarks("Remarks" + " " + i);
            recordModelList.add(model);
        }

        listView.setAdapter(recordAdapter);
    }
}

class RecordModel {
    String date, productGroup, productQuantity, monthlyPotential, productPOB, remarks;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getMonthlyPotential() {
        return monthlyPotential;
    }

    public void setMonthlyPotential(String monthlyPotential) {
        this.monthlyPotential = monthlyPotential;
    }

    public String getProductPOB() {
        return productPOB;
    }

    public void setProductPOB(String productPOB) {
        this.productPOB = productPOB;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

class RecordAdapter extends BaseAdapter {

    List<RecordModel> recordModels;
    Context context;

    public RecordAdapter(List<RecordModel> recordModels, Context context) {
        this.recordModels = recordModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return recordModels.size();
    }

    @Override
    public Object getItem(int i) {
        return recordModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.record_activity_history, viewGroup, false);

        TextView date, productGroup, productQuantity, monthlyPotential, productPOB, remarks;

        date = view.findViewById(R.id.date);
        productGroup = view.findViewById(R.id.product_group);
        productQuantity = view.findViewById(R.id.product_qty);
        monthlyPotential = view.findViewById(R.id.monthly_potential);
        productPOB = view.findViewById(R.id.product_pob);
        remarks = view.findViewById(R.id.remarks);

        date.setText(recordModels.get(i).getDate());
        productGroup.setText(recordModels.get(i).getProductGroup());
        productQuantity.setText(recordModels.get(i).getProductQuantity());
        monthlyPotential.setText(recordModels.get(i).getMonthlyPotential());
        productPOB.setText(recordModels.get(i).getProductPOB());
        remarks.setText(recordModels.get(i).getRemarks());

        return view;
    }
}