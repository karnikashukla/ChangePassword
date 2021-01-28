package com.changepassword.DAR;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.changepassword.R;

public class ProductDetalingActivity extends AppCompatActivity {

    Button done;
    String date, city;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detaling1);

        done = findViewById(R.id.done);
        date = getIntent().getStringExtra("SelectedDate");
        city = getIntent().getStringExtra("City");
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent customer = new Intent(ProductDetalingActivity.this, CustomerSelectionActivity.class);
                customer.putExtra("SelectedDate", date);
                customer.putExtra("City", city);
                startActivity(customer);
                finish();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent record = new Intent(ProductDetalingActivity.this, RecordDetailsActivity.class);
                record.putExtra("SelectedDate", date);
                record.putExtra("City", city);
                startActivity(record);
                finish();
            }
        });
    }
}