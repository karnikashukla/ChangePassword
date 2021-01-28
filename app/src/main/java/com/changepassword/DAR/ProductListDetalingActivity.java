package com.changepassword.DAR;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.changepassword.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ProductListDetalingActivity extends AppCompatActivity {

    Button done;
    String date, city, name;
    FloatingActionButton add;
    ImageView back;
    TextView dateTV, cityTV, customerNameTV;
    ListView listView;
    List<ProductModel> productModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_product_list_detailing);

        date = getIntent().getStringExtra("SelectedDate");
        city = getIntent().getStringExtra("City");
        name = getIntent().getStringExtra("Name");
        back = findViewById(R.id.back);
        add = findViewById(R.id.add);
        dateTV = findViewById(R.id.date);
        cityTV = findViewById(R.id.city);
        customerNameTV = findViewById(R.id.customer_name);
        listView = findViewById(R.id.product_details);

        dateTV.setText(date);
        cityTV.setText(city);
        customerNameTV.setText(name);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent product = new Intent(ProductListDetalingActivity.this, ProductDetalingActivity.class);
                startActivity(product);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customer = new Intent(ProductListDetalingActivity.this, CustomerSelectionActivity.class);
                customer.putExtra("SelectedDate", date);
                customer.putExtra("City", city);
                startActivity(customer);
                finish();
            }
        });

        productModels = new ArrayList<>();
        ProductAdapter productAdapter = new ProductAdapter(ProductListDetalingActivity.this, productModels);
        for (int i = 0; i <= 3; i++) {
            ProductModel productModel = new ProductModel();
            productModel.setName("Product " + i);
            productModel.setQty("Quantity " + i);
            productModel.setRemarks("Remark " + i);

            productModels.add(productModel);
        }

        listView.setAdapter(productAdapter);
    }
}

class ProductModel {
    String name, qty, remarks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}

class ProductAdapter extends BaseAdapter {

    Context context;
    List<ProductModel> productModelList;

    public ProductAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
    }

    @Override
    public int getCount() {
        return productModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return productModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.product_list, viewGroup, false);

        TextView name, qty, remark;

        name = view.findViewById(R.id.product_name);
        qty = view.findViewById(R.id.product_qty);
        remark = view.findViewById(R.id.product_remark);

        name.setText(productModelList.get(i).getName());
        qty.setText(productModelList.get(i).getQty());
        remark.setText(productModelList.get(i).getRemarks());

        return view;
    }
}