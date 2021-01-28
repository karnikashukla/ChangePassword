package com.changepassword.DAR;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.changepassword.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class RecordDetailListAdapter extends BaseAdapter {

    Context context;
    List<RecordDetailListModel> recordDetailListModels;

    public RecordDetailListAdapter(Context context, List<RecordDetailListModel> recordDetailListModels) {
        this.context = context;
        this.recordDetailListModels = recordDetailListModels;
    }

    @Override
    public int getCount() {
        return recordDetailListModels.size();
    }

    @Override
    public Object getItem(int i) {
        return recordDetailListModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.record_detail_list_dar, viewGroup, false);

        TextView reason, city;
        FloatingActionButton edit, add;
        CheckBox checkBox;

        reason = view.findViewById(R.id.title);
        city = view.findViewById(R.id.subtitle);
        edit = view.findViewById(R.id.edit);
        add = view.findViewById(R.id.add);
        checkBox = view.findViewById(R.id.missed);

        reason.setText(recordDetailListModels.get(i).getReasonType());
        city.setText(recordDetailListModels.get(i).getCity());

        RecordDetailListModel detailListModel;
        detailListModel = (RecordDetailListModel) getItem(i);
        if (detailListModel.getReasonType().equals("Reason Type 3")) {
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams")
                View popupView = inflater.inflate(R.layout.update_reason_popup, null);
                boolean focusable = true;
                int height = 900;
                int width = 1000;
                ImageView cancel = popupView.findViewById(R.id.cancel);
                Button cancelButton = popupView.findViewById(R.id.btn_close);
                PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                popupView.setOnTouchListener((view1, motionEvent) -> {
                    popupWindow.dismiss();
                    return true;
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DarMainActivity.goToWork();
            }
        });

        return view;
    }
}
