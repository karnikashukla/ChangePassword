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
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class WorkWithActivity extends AppCompatActivity {

    String[] employees = new String[]{"Employee 1", "Employee 2", "Employee 3", "Work Alone"};
    ListView employeeList;
    List<WorkWithModel> employeeModelList;
    ArrayAdapter<String> employeeAdapter;
    FloatingActionButton add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_with);

        employeeList = findViewById(R.id.employee_list);
        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mptMain = new Intent(WorkWithActivity.this, MainFieldOrOtherActivity.class);
                startActivity(mptMain);
            }
        });

        ArrayList<WorkWithModel> workWithModels = new ArrayList<>();
        ///CustomerAdapter listAdapter = new CustomerAdapter(CustomerSelectionActivity.this, customerModel);

        WorkWithEmployee workWithEmployee = new WorkWithEmployee(WorkWithActivity.this, workWithModels);

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


        return view;
    }
}