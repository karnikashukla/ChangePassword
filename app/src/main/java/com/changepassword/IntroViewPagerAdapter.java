package com.changepassword;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context context;
    List<ScreenItem> screenItems;

    public IntroViewPagerAdapter(Context context, List<ScreenItem> screenItems) {
        this.context = context;
        this.screenItems = screenItems;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout_screen = inflater.inflate(R.layout.layout_screen, null);
        ImageView imageView = layout_screen.findViewById(R.id.intro_image);
        TextView titleView = layout_screen.findViewById(R.id.title);
        TextView descriptionView = layout_screen.findViewById(R.id.description);

        titleView.setText(screenItems.get(position).getTitle());
        descriptionView.setText(screenItems.get(position).getDescription());
        imageView.setImageResource(screenItems.get(position).getImage());

        container.addView(layout_screen);
        return layout_screen;
    }

    @Override
    public int getCount() {
        return screenItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
