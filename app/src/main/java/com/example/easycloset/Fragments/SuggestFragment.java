package com.example.easycloset.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.easycloset.Activities.MainActivity;
import com.example.easycloset.Models.Suggest;
import com.example.easycloset.Models.Weather;
import com.example.easycloset.Queries;
import com.example.easycloset.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        verifyStoragePermission(activity);
        FloatingActionButton btnShare = view.findViewById(R.id.floatingShareAction);
        Queries queries = new Queries(outerLayer, baseLayer, pants, feet, getContext());
        queries.multipleQueries("sweater", "t-shirt", "jogger", "sneakers");
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenShot(activity.getWindow().getDecorView());
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

        }

        if (suggestions.getBase() != null) {
            Glide.with(requireContext()).load(suggestions.getBase().getUrl()).into(baseLayer);

        }

        if (suggestions.getBottom() != null) {
            Glide.with(requireContext()).load(suggestions.getBottom().getUrl()).into(pants);

        }

        if (suggestions.getFoot() != null) {
            Glide.with(requireContext()).load(suggestions.getFoot().getUrl()).into(feet);
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public static void verifyStoragePermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    private void takeScreenShot(View view) {
        Date date = new Date();
        CharSequence format = android.text.format.DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);
        try {
            File mainDir = new File(
                    requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
            if (!mainDir.exists()) {
                boolean mkdir = mainDir.mkdir();
            }
            String path = mainDir + "/" + "TrendOceans" + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(activity, "Took a screen shot", Toast.LENGTH_SHORT).show();

            shareScreenShot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shareScreenShot(File imageFile) {
        Uri uri = FileProvider.getUriForFile(requireContext(), "com.codepath.fileprovider.easycloset", imageFile);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Download Application from Instagram");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        Intent chooser = Intent.createChooser(intent, "Share File");

        List<ResolveInfo> resInfoList = getContext().getPackageManager().queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            getContext().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivity(chooser);
    }

}