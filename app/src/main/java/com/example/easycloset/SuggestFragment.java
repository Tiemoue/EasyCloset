package com.example.easycloset;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class SuggestFragment extends Fragment {

    TextView tvDate;
    MainActivity activity;
    TextView degree;
    Weather suggestWeather;
    ImageView base;
    List<Item> Sweaters;

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
        DateFormat df = new SimpleDateFormat("h:mm a");
        df.setTimeZone(TimeZone.getDefault());
        String date = df.format(Calendar.getInstance().getTime());
        Sweaters = new ArrayList<>();
        tvDate = view.findViewById(R.id.date);
        tvDate.setText(date);
        degree = view.findViewById(R.id.degree);
        suggestWeather = activity.getHomeFragment().getWeather();
        degree.setText(suggestWeather.getTemp());
        base = view.findViewById(R.id.baseLayer);
        SweaterQuery();
    }

    protected void SweaterQuery() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Item> query = ParseQuery.getQuery(Item.class);
        // include data referred by user key
        query.whereEqualTo(Item.KEY_CATEGORY, "Sweater");
        query.findInBackground((items, e) -> {
            // check for errors
            if (e != null) {
                Log.i("HHERE", e.toString());
                return;
            }
            Sweaters.addAll(items);
            Item item = Sweaters.get(0);
            Glide.with(getContext()).load(item.getImage().getUrl()).into(base);
            // save received posts to list and notify adapter of new daa
        });
    }
}