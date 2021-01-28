package com.changepassword;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FieldWorkListActivity extends AppCompatActivity {

    public static final String[] title = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
    public static final String[] newTitleList = {};
    public static boolean isAction = false;
    public static ListView listView, selectedCustomer;
    public static FloatingActionButton add, delete;
    public static Toolbar toolbar;
    public static List<String> newCustomerList = new ArrayList<>();
    TextView dateTv, cityTV;
    String date, city;
    List<CustomerListModel> listModels;
    ArrayAdapter<String> adapter, finalAdapter;
    EditText search;
    CheckBox itemSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_work_list);

        dateTv = findViewById(R.id.selectedDateTV);
        date = getIntent().getStringExtra("SelectedDate");

        cityTV = findViewById(R.id.selectedCityTV);
        city = getIntent().getStringExtra("SelectedCity");

        add = findViewById(R.id.multi_add);
        delete = findViewById(R.id.multi_delete);
        search = findViewById(R.id.search);
        toolbar = findViewById(R.id.toolbar);

        setList();

        adapter = new ArrayAdapter<String>(this,
                R.layout.cusomer_list_item,
                R.id.title,
                title);

        dateTv.setText(date);
        cityTV.setText(city);

        setList();
        selectedCustomerList();
    }

    private void selectedCustomerList() {
        selectedCustomer = findViewById(R.id.selectedCustomerList);

        finalAdapter = new ArrayAdapter<String>(this,
                R.layout.customer_list_delete_item,
                R.id.delete_title,
                title);
        listModels = new ArrayList<>();
        ArrayList<CustomerListModel> arrayList = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {
            CustomerListModel model = new CustomerListModel();
            model.setChecked(false);
            model.setTitle(title[i]);
            arrayList.add(model);
        }
        FinalCustomerListAdapter listAdapter = new FinalCustomerListAdapter(arrayList, FieldWorkListActivity.this);
        selectedCustomer.setAdapter(listAdapter);

        selectedCustomer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                delete.setVisibility(View.VISIBLE);
                isAction = true;
                listAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setList() {
        listModels = new ArrayList<>();
        ArrayList<CustomerListModel> arrayList = new ArrayList<>();

        for (int i = 0; i < title.length; i++) {
            CustomerListModel model = new CustomerListModel();
            model.setChecked(false);
            model.setTitle(title[i]);
            arrayList.add(model);
//            model.setTitle(title[i]);
//         //   model.setSubtitle(subtitle[i]);
//            listModels.add(model);
//         //   CustomerListModel listModel = new CustomerListModel(title[i]);
//            arrayList.add(listModel);
        }
//        listView = findViewById(R.id.event_list);
        listView = findViewById(R.id.customerList);
        CustomerListAdapter listAdapter = new CustomerListAdapter(listModels, FieldWorkListActivity.this, arrayList);
        listView.setAdapter(listAdapter);

//        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                add.setVisibility(View.VISIBLE);
                isAction = true;
                listAdapter.notifyDataSetChanged();
                return true;
            }
        });

//        itemSelected = CustomerListAdapter.ViewHolder.titleSelected;
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                CustomerListModel listModel;
//                listModel = listModels.get(i);
//                itemSelected.setChecked(listModel.getChecked());
//                if (itemSelected.isChecked()){
//                    if (!newCustomerList.contains(i)){
//                        newCustomerList.add(String.valueOf(i));
//                        Log.d("insert", String.valueOf(i));
//                    }
//                }
//                else {
//                    if (newCustomerList.contains(i)){
//                        newCustomerList.remove(String.valueOf(i));
//                    }
//                }
//            }
//        });
//
//        Log.d("insert", newCustomerList.toString());

//        isAction = false;
//        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
//        listView.setMultiChoiceModeListener(modeListener);
//        listView.setOnTouchListener((view, motionEvent) -> {
//            add.setVisibility(View.GONE);
//            isAction = false;
//            for (int i = 0; i < title.length; i++) {
//                CustomerListModel model = new CustomerListModel();
//                model.setChecked(false);
//                listModels.add(model);
//            }
//            return true;
//        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = search.getText().toString().toLowerCase(Locale.getDefault());
                listAdapter.listFilter(name);
//                listView.setAdapter(listAdapter);
                //        listAdapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void goBack(View view) {
        toolbar.setTitle(R.string.field_work);
//        CustomerListAdapter.ViewHolder.titleSelected.setVisibility(View.GONE);
        isAction = false;
        Intent calendar = new Intent(FieldWorkListActivity.this, MainFieldOrOtherActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            startActivity(calendar);
        }
        finish();
    }
//
//    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
//        @Override
//        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
//
//        }
//
//        @RequiresApi(api = Build.VERSION_CODES.M)
//        @Override
//        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
//        //    actionMode = startSupportActionMode(callback);
//            add.setVisibility(View.VISIBLE);
//            isAction = true;
//            return true;
//        }
//
//        @Override
//        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
//            return false;
//        }
//
//        @Override
//        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
//            return false;
//        }
//
//        @Override
//        public void onDestroyActionMode(ActionMode actionMode) {
//            add.setVisibility(View.GONE);
//            isAction = false;
//            for (int i=0; i<title.length; i++){
//                CustomerListModel model = new CustomerListModel();
//                model.setChecked(false);
//                listModels.add(model);
//            }
//        }
//    };
}