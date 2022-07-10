package com.example.easycloset;

import android.app.ProgressDialog;
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

        progressDialog.setTitle("Generating Outfit...");
        progressDialog.show();
        if (!shouldFetch) {
            multipleQueries(outerLayer, baseLayer, pants, feet, "sweaters", "t-shirt", "joggers", "sneakers");
        }
    }

    public void multipleQueries(ImageView outerLayer, ImageView baseLayer, ImageView pants, ImageView feet, String outer, String base, String bottom, String foot) {
        ParseQuery myQuery1 = new ParseQuery(Item.class);
        myQuery1.whereEqualTo(Item.KEY_CATEGORY, outer);

        ParseQuery myQuery2 = new ParseQuery(Item.class);
        myQuery2.whereEqualTo(Item.KEY_CATEGORY, base);

        ParseQuery myQuery3 = new ParseQuery(Item.class);
        myQuery3.whereEqualTo(Item.KEY_CATEGORY, bottom);

        ParseQuery myQuery4 = new ParseQuery(Item.class);
        myQuery4.whereEqualTo(Item.KEY_CATEGORY, foot);

        List<ParseQuery<Item>> queries = new ArrayList<ParseQuery<Item>>();
        queries.add(myQuery1);
        queries.add(myQuery2);
        queries.add(myQuery3);
        queries.add(myQuery4);

        ParseQuery<Item> mainQuery = ParseQuery.or(queries);
        mainQuery.findInBackground(new FindCallback<Item>() {
            @Override
            public void done(List<Item> objects, ParseException e) {
                if (e != null) {
                    return;
                }

                List<ParseFile> outerArray = new ArrayList<>();
                List<ParseFile> baseArray = new ArrayList<>();
                List<ParseFile> bottomArray = new ArrayList<>();
                List<ParseFile> feetArray = new ArrayList<>();

                for (Item object : objects) {
                    if (object.getCategory().equals(outer)) {
                        outerArray.add(object.getImage());
                    } else if (object.getCategory().equals(base)) {
                        baseArray.add(object.getImage());
                    } else if (object.getCategory().equals(bottom)) {
                        bottomArray.add(object.getImage());

                    } else if (object.getCategory().equals(foot)) {
                        feetArray.add(object.getImage());
                    }
                }
                progressDialog.dismiss();
                Suggest suggest = new Suggest();
                Random random = new Random();

                if (outerArray.size() != 0) {
                    ParseFile outerItem = outerArray.get(random.nextInt(outerArray.size()));
                    Glide.with(requireContext()).load(outerItem.getUrl()).into(outerLayer);
                    suggest.setOuter(outerItem);
                } else {
                    tvOuter.setText(R.string.outer_not_found);
                    tvOuter.setTextSize(10);
                    Glide.with(requireContext()).load(R.drawable.notfound).into(outerLayer);
                }

                if (baseArray.size() != 0) {
                    ParseFile baseItem = baseArray.get(random.nextInt(baseArray.size()));
                    Glide.with(getContext()).load(baseItem.getUrl()).into(baseLayer);
                    suggest.setBase(baseItem);
                } else {
                    tvBase.setText(R.string.base_not_found);
                    tvBase.setTextSize(10);
                    Glide.with(getContext()).load(R.drawable.notfound).into(baseLayer);
                }

                if (bottomArray.size() != 0) {
                    ParseFile bottomItem = bottomArray.get(random.nextInt(bottomArray.size()));
                    Glide.with(getContext()).load(bottomItem.getUrl()).into(pants);
                    suggest.setBottom(bottomItem);
                } else {
                    tvBottom.setText(R.string.bottom_not_found);
                    tvBottom.setTextSize(10);
                    Glide.with(getContext()).load(R.drawable.notfound).into(pants);
                }

                if (feetArray.size() != 0) {
                    ParseFile footItem = feetArray.get(random.nextInt(feetArray.size()));
                    Glide.with(getContext()).load(footItem.getUrl()).into(feet);
                    suggest.setFoot(footItem);
                } else {
                    tvFeet.setText(R.string.not_found_foot);
                    tvFeet.setTextSize(10);
                    Glide.with(getContext()).load(R.drawable.notfound).into(feet);
                }

                setSuggestions(suggest);
                shouldFetch = true;
            }
        });
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