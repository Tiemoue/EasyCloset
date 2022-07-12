package com.example.easycloset.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.easycloset.Activities.ClothesActivity;
import com.example.easycloset.Activities.MainActivity;
import com.example.easycloset.Models.Item;
import com.example.easycloset.Models.Suggest;
import com.example.easycloset.Models.Weather;
import com.example.easycloset.Queries;
import com.example.easycloset.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class SuggestFragment extends Fragment {

    private MainActivity activity;
    private ImageView baseLayer, outerLayer, feet, pants;
    private TextView tvOuter, tvBase, tvFeet, tvBottom;
    private ProgressDialog progressDialog;
    private Suggest suggestions;
    private boolean shouldFetch = false;

    @Override
    public void onResume() {
        super.onResume();
        if (shouldFetch) {
            fetchData();
        }
    }

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
        TextView tvDate = view.findViewById(R.id.tvDate);
        tvDate.setText(date);

        Weather suggestWeather = activity.getHomeFragment().getWeather();

        TextView tvTemp = view.findViewById(R.id.tvWeatherTemp);
        tvTemp.setText(String.format("Today  %s℉", suggestWeather.getTemp()));

        baseLayer = view.findViewById(R.id.ivBaseLayer);
        outerLayer = view.findViewById(R.id.ivOuterLayer);
        feet = view.findViewById(R.id.ivFeet);
        pants = view.findViewById(R.id.ivPants);
        tvOuter = view.findViewById(R.id.tvOuterLayer);
        tvBase = view.findViewById(R.id.tvBaseLayer);

        tvFeet = view.findViewById(R.id.tvFeet);
        tvBottom = view.findViewById(R.id.tvBottomLayer);
        progressDialog = new ProgressDialog(getContext());

//        progressDialog.setTitle("Generating Outfit...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
        Queries queries = new Queries(outerLayer, baseLayer, pants, feet, getContext());
        queries.multipleQueries("jacket", "t-shirt", "jogger", "sneaker");
    }


    public void setSuggestions(Suggest suggestions) {
        this.suggestions = suggestions;
    }

    public void fetchData() {
        progressDialog.dismiss();
        if (suggestions.getOuter() != null) {
            Glide.with(requireContext()).load(suggestions.getOuter().getUrl()).into(outerLayer);
        } else {
            tvOuter.setText(R.string.outer_not_found);
            tvOuter.setTextSize(10);
            Glide.with(requireContext()).load(R.drawable.notfound).into(outerLayer);
        }

        if (suggestions.getBase() != null) {
            Glide.with(requireContext()).load(suggestions.getBase().getUrl()).into(baseLayer);
        } else {
            tvBase.setText(R.string.base_not_found);
            tvBase.setTextSize(10);
            Glide.with(requireContext()).load(R.drawable.notfound).into(baseLayer);
        }

        if (suggestions.getBottom() != null) {
            Glide.with(requireContext()).load(suggestions.getBottom().getUrl()).into(pants);
        } else {
            tvBottom.setText(R.string.bottom_not_found);
            tvBottom.setTextSize(10);
            Glide.with(requireContext()).load(R.drawable.notfound).into(pants);
        }

        if (suggestions.getFoot() != null) {
            Glide.with(requireContext()).load(suggestions.getFoot().getUrl()).into(feet);
        } else {
            tvFeet.setText(R.string.not_found_foot);
            tvFeet.setTextSize(10);
            Glide.with(requireContext()).load(R.drawable.notfound).into(feet);
        }
    }

}