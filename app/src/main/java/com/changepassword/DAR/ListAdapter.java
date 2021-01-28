package com.changepassword.DAR;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.changepassword.R;
import com.daimajia.swipe.SwipeLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    protected List<com.changepassword.DAR.ListModel> listModels;
    Context context;

//    public ListAdapter(Context context, List<com.changepassword.DAR.ListModel> listModels) {
//        this.context = context;
//        this.listModels = listModels;
//    }

    public ListAdapter(Context context, List<com.changepassword.DAR.ListModel> listModels) {
        this.context = context;
        this.listModels = listModels;
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

        ViewHolder viewHolder = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_dar, null);
            viewHolder = new ViewHolder();
            viewHolder.subtitleText = view.findViewById(R.id.subtitle);
            viewHolder.titleText = view.findViewById(R.id.title);
            viewHolder.missed = view.findViewById(R.id.missed);
            viewHolder.swipeLayout = view.findViewById(R.id.swipe_layout);
            viewHolder.delete = view.findViewById(R.id.delete);
            viewHolder.product = view.findViewById(R.id.product);
            viewHolder.history = view.findViewById(R.id.history);
            viewHolder.add = view.findViewById(R.id.add);

            DarMainActivity.city = listModels.get(i).getSubtitle();
            DarMainActivity.name = listModels.get(i).getTitle();
            ViewHolder finalViewHolder = viewHolder;
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listModels.remove(i);
                    finalViewHolder.swipeLayout.close();
                    notifyDataSetChanged();
                }
            });

            viewHolder.product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DarMainActivity.goToProduct();
                }
            });

            viewHolder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DarMainActivity.goToWork();
                }
            });

            viewHolder.history.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
                    @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.dar_list_item_details, null);
                    int height = 600;
                    int width = 1000;
                    TextView title = popupView.findViewById(R.id.popup_title);
                    ImageView cancel = popupView.findViewById(R.id.cancel);
                    TextView date = popupView.findViewById(R.id.date);
                    title.setText(listModels.get(i).getTitle());
                    date.setText(DarMainActivity.dateString);
                    PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                    popupView.setOnTouchListener((View view1, @SuppressLint("ClickableViewAccessibility") MotionEvent motionEvent) -> {
                        popupWindow.dismiss();
                        return true;
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });
                }
            });

            viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ListModel listModel;
        listModel = (com.changepassword.DAR.ListModel) getItem(i);
        viewHolder.titleText.setText(listModel.getTitle());
        viewHolder.subtitleText.setText(listModel.getSubtitle());

        if (listModel.getTitle().equals("B")) {
            viewHolder.missed.setVisibility(View.VISIBLE);
        } else {
            viewHolder.missed.setVisibility(View.GONE);
        }

        viewHolder.missed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                LayoutInflater inflater = (LayoutInflater) compoundButton.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.is_missed_popup, null);
                int height = 1000;
                int width = 1000;
//                TextView title = popupView.findViewById(R.id.popup_title);
                ImageView cancel = popupView.findViewById(R.id.cancel);
//                TextView date = popupView.findViewById(R.id.date);
//                title.setText(listModels.get(i).getTitle());
//                date.setText(DarMainActivity.dateString);
                Button cancelButton = popupView.findViewById(R.id.btn_close);
                PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.showAtLocation(compoundButton, Gravity.CENTER, 0, 0);
                popupView.setOnTouchListener((View view1, @SuppressLint("ClickableViewAccessibility") MotionEvent motionEvent) -> {
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

        return view;
    }

    public static class ViewHolder {
        TextView titleText, subtitleText;
        CheckBox missed;
        SwipeLayout swipeLayout;
        FloatingActionButton product, history, add;
        private View delete;
    }

}
