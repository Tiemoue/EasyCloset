package com.example.easycloset;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public class SuggestFragment extends Fragment {

    private TextView tvDate;
    private MainActivity activity;
    private TextView tvTemp;
    private Weather suggestWeather;
    private ImageView base, layer, midlayer, bottomlayer;

    public SuggestFragment() {
        // Required empty public constructor
    }

    public SuggestFragment(MainActivity mainActivity) {
        activity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suggest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        DateFormat df = new SimpleDateFormat("EEE, MMM d, ''yy");
        df.setTimeZone(TimeZone.getDefault());
        String date = df.format(Calendar.getInstance().getTime());
        tvDate = view.findViewById(R.id.tvDate);
        tvDate.setText(date);

        suggestWeather = activity.getHomeFragment().getWeather();

        tvTemp = view.findViewById(R.id.tvWeatherTemp);
        tvTemp.setText(String.format("%s℉", suggestWeather.getTemp()));

        base = view.findViewById(R.id.baseLayer);
        layer = view.findViewById(R.id.outerLayer);
        midlayer = view.findViewById(R.id.middleLayer);
        bottomlayer = view.findViewById(R.id.shoes);

        Query("Sweater", base);
        Query("T-shirt", layer);
        Query("Shorts", midlayer);
        Query("Sneakers", bottomlayer);
    }


    protected void Query(String category, ImageView imageView) {
        // specify what type of data we want to query - Post.class
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        // include data referred by user key
        query.whereEqualTo(Item.KEY_CATEGORY, category);
        query.findInBackground((items, e) -> {
            // check for errors
            if (e != null) {
                return;
            }
            Random rand = new Random();
            int num = rand.nextInt(items.size());
            Item item = items.get(num);
            Glide.with(requireContext()).load(item.getImage().getUrl()).into(imageView);
            // save received posts to list and notify adapter of new daa
        });
    }
}