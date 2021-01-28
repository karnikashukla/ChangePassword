package com.changepassword;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FinalCustomerListAdapter extends BaseAdapter {

    public static int count = 0;
    private final List<String> filteredData = null;
    List<CustomerListModel> listModels = null;
    Context context;
    ArrayList<CustomerListModel> arrayList;
    ArrayList<String> newCustomerList = new ArrayList<>();
    boolean checked = false;
    TextView titleText;
    CheckBox titleSelected;

    public FinalCustomerListAdapter(List<CustomerListModel> listModels, Context context) {
        this.listModels = listModels;
        this.context = context;
        this.arrayList = new ArrayList<CustomerListModel>();
        this.arrayList.addAll(listModels);
    }

    public FinalCustomerListAdapter(List<CustomerListModel> listModels, Context context, ArrayList<CustomerListModel> arrayList) {
        this.listModels = listModels;
        this.context = context;
        this.arrayList = arrayList;
        this.arrayList.addAll(listModels);
    }


    public void listFilter(String name) {
        name = name.toLowerCase(Locale.getDefault());
        listModels.clear();
        if (name.length() == 0) {
            listModels.addAll(arrayList);
        } else {
            for (CustomerListModel list :
                    arrayList) {
                if (list.getTitle().toLowerCase(Locale.getDefault()).contains(name)) {
                    listModels.add(list);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listModels.size();
    }

    @Override
    public Object getItem(int i) {
        return listModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listModels.indexOf(listModels.get(i));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        FinalCustomerListAdapter.ViewHolder viewHolder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.customer_list_delete_item, viewGroup, false);

        titleText = view.findViewById(R.id.delete_title);
        titleText.setText(listModels.get(i).getTitle());
        titleSelected = view.findViewById(R.id.title_check_delete);
        titleSelected.setTag(i);
        if (FieldWorkListActivity.isAction) {
            titleSelected.setVisibility(View.VISIBLE);
        } else {
            titleSelected.setVisibility(View.GONE);
        }

        CustomerListModel listModel = listModels.get(i);

        titleText.setText(listModel.getTitle());

//        titleSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                int position = (int) compoundButton.getTag();
//                if (FieldWorkListActivity.newCustomerList.contains(listModels.get(position))){
//                    FieldWorkListActivity.newCustomerList.remove(listModels.get(position).getTitle());
//                    titleSelected.setChecked(false);
//                    titleSelected.setSelected(false);
//                }else{
//                    FieldWorkListActivity.newCustomerList.add(listModels.get(position).getTitle());
//                    titleSelected.setChecked(true);
//                    titleSelected.setSelected(true);
//                }
//                Log.d("insert",newCustomerList.toString());
//            }
//        });


        titleSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = (int) view.getTag();
                boolean isChecked = false;
                if (listModels.get(currentPosition).getChecked() == false) {
                    isChecked = true;
                    count++;
                }
                listModels.get(currentPosition).setChecked(isChecked);

                notifyDataSetChanged();
            }
        });

        titleText.setText(listModels.get(i).getTitle());
        notifyDataSetChanged();
        return view;
    }

    public class ViewHolder {
        TextView titleText, subtitleText;
        CheckBox titleSelected;
    }
}
