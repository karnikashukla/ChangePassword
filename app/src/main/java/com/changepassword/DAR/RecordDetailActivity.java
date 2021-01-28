package com.changepassword.DAR;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.changepassword.R;

import java.util.ArrayList;
import java.util.List;

public class RecordDetailActivity extends AppCompatActivity {

    public static String empName, reason;
    String date, city;
    TextView dateTV, cityTV;
    ListView recordList;
    //    public static String from = "record";
    Button draft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        dateTV = findViewById(R.id.date);
        cityTV = findViewById(R.id.city);
        recordList = findViewById(R.id.record_details_list);
        draft = findViewById(R.id.btn_draft);

        date = getIntent().getStringExtra("SelectedDate");
        city = getIntent().getStringExtra("City");
        empName = getIntent().getStringExtra("Employee");
        reason = getIntent().getStringExtra("Reason");

        dateTV.setText(date);
        cityTV.setText(city);

        draft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main = new Intent(RecordDetailActivity.this, DarMainActivity.class);
                DarMainActivity.listFrom = "Record";
                startActivity(main);
                finish();
            }
        });

        ArrayList<RecordDetailModel> recordDetailModels = new ArrayList<>();
        RecordDetailAdapter detailAdapter = new RecordDetailAdapter(recordDetailModels, RecordDetailActivity.this);

        for (int i = 0; i < 1; i++) {
            RecordDetailModel detailModel = new RecordDetailModel();
            detailModel.setPersonName("Remark");
            detailModel.setReason(reason);
            recordDetailModels.add(detailModel);
        }


        recordList.setAdapter(detailAdapter);
    }
}

class RecordDetailModel {
    String personName, reason;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

class RecordDetailAdapter extends BaseAdapter {

    List<RecordDetailModel> recordDetailModels = null;
    Context context;

    public RecordDetailAdapter(List<RecordDetailModel> recordDetailModels, Context context) {
        this.recordDetailModels = recordDetailModels;
        this.context = context;
    }

    @Override
    public int getCount() {
        return recordDetailModels.size();
    }

    @Override
    public Object getItem(int i) {
        return recordDetailModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.record_detail_list, viewGroup, false);

        TextView name, reason;

        name = view.findViewById(R.id.person);
        reason = view.findViewById(R.id.reason);
//
        name.setText(recordDetailModels.get(i).getPersonName());
        reason.setText(recordDetailModels.get(i).getReason());

        return view;
    }
}