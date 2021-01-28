package com.changepassword.DAR;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.changepassword.R;

import java.util.ArrayList;
import java.util.List;

public class WorkWithSelectionActivity extends AppCompatActivity {

    // public static ArrayList<String> empName = new ArrayList<String>();
    public static String empName;
    String[] employees = new String[]{"Employee 1", "Employee 2", "Employee 3", "Work Alone"};
    ListView employeeList;
    List<WorkWithModel> employeeModelList;
    ArrayAdapter<String> employeeAdapter;
    String date, city, reason, custName, flag;
    TextView dateSelected, citySelected;
    ImageView back;
    Button previous, next;
    SparseBooleanArray empList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_with_selection);

        employeeList = findViewById(R.id.employee_list);
        dateSelected = findViewById(R.id.date);
        citySelected = findViewById(R.id.city);
        back = findViewById(R.id.back);
        previous = findViewById(R.id.previous);
        next = findViewById(R.id.next);

        date = getIntent().getStringExtra("SelectedDate");
        city = getIntent().getStringExtra("SelectedCity");
        reason = getIntent().getStringExtra("Reason");
        custName = getIntent().getStringExtra("CustomerName");

        dateSelected.setText(date);
        citySelected.setText(city);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToWorkType = new Intent(WorkWithSelectionActivity.this, WorkTypeActivity.class);
                startActivity(backToWorkType);
                finish();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToWorkType = new Intent(WorkWithSelectionActivity.this, WorkTypeActivity.class);
                startActivity(backToWorkType);
                finish();
            }
        });

        ArrayList<WorkWithModel> workWithModels = new ArrayList<>();
        ///CustomerAdapter listAdapter = new CustomerAdapter(CustomerSelectionActivity.this, customerModel);

        WorkWithEmployee workWithEmployee = new WorkWithEmployee(WorkWithSelectionActivity.this, workWithModels);

        employeeAdapter = new ArrayAdapter<String>(
                this,
                R.layout.employee_list,
                R.id.employee_name,
                employees
        );

//        workWithModels = new ArrayList<>();
//        customerModel = new ArrayList<>();

        for (int i = 0; i < employees.length; i++) {
            WorkWithModel model = new WorkWithModel();
            model.setName(employees[i]);
            workWithModels.add(model);
        }

        employeeList.setAdapter(workWithEmployee);

        flag = getIntent().getStringExtra("FieldWork");
//        String otherFlag = getIntent().getStringExtra("From WorkTypeActivity");
        if (flag == null) {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent recordDetail = new Intent(WorkWithSelectionActivity.this, RecordDetailActivity.class);
                    recordDetail.putExtra("SelectedDate", date);
                    recordDetail.putExtra("City", city);
                    recordDetail.putExtra("Employee", empName);
                    recordDetail.putExtra("Reason", reason);
                    startActivity(recordDetail);
                    finish();
                }
            });
        } else if (flag.equals("Field")) {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent product = new Intent(WorkWithSelectionActivity.this, CustomerSelectionActivity.class);
                    product.putExtra("SelectedDate", date);
                    product.putExtra("City", city);
                    product.putExtra("CustomerName", custName);
                    startActivity(product);
                    finish();
                }
            });

        }
    }
}


class WorkWithModel {
    String name;
    boolean isChecked = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

class WorkWithEmployee extends BaseAdapter {

    Context context;
    List<WorkWithModel> list = null;

    public WorkWithEmployee(Context context, List<WorkWithModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.employee_list, viewGroup, false);

        CheckBox emp = view.findViewById(R.id.employee_check);

        TextView name = view.findViewById(R.id.employee_name);
        name.setText(list.get(i).getName());

        if (list.get(i).getName().equals("Work Alone")) {
            emp.setChecked(true);
        }

        emp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
//                    WorkWithSelectionActivity.empName.add(list.get(i).getName());
                    WorkWithSelectionActivity.empName = list.get(i).getName();
                    Log.d("Employee Selected", WorkWithSelectionActivity.empName);
                }
            }
        });

//        WorkWithSelectionActivity.empName = list.get(i).getName();
//        Log.d("Employee Selected",WorkWithSelectionActivity.empName);

        return view;
    }
}