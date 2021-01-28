package com.changepassword;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    Context context;
    List<ListModel> listModels;

    public ListAdapter(Context context, List<ListModel> listModels) {
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
            view = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.subtitleText = view.findViewById(R.id.subtitle);
            viewHolder.titleText = view.findViewById(R.id.title);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ListModel listModel;
        listModel = (ListModel) getItem(i);
        viewHolder.titleText.setText(listModel.getTitle());
        viewHolder.subtitleText.setText(listModel.getSubtitle());

        return view;
    }

    public static class ViewHolder {
        TextView titleText, subtitleText;
    }
}
