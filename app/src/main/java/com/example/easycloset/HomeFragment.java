package com.example.easycloset;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;


public class HomeFragment extends Fragment {

    private Weather weather;
    private ImageView search;
    private EditText etCity;
    private TextView tvCity, tvCountry, tvTemp, tvForecast, tvHumidity, tvMinTemp, tvMaxTemp, tvSunrise, tvSunset;

    public HomeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search = view.findViewById(R.id.ivSearchButton);
        etCity = view.findViewById(R.id.etYourCity);
        tvCity = view.findViewById(R.id.tvCity);
        tvCountry = view.findViewById(R.id.tvCountry);
        tvTemp = view.findViewById(R.id.tvTemp);
        tvForecast = view.findViewById(R.id.tvForecast);
        tvHumidity = view.findViewById(R.id.tvHumidity);
        tvMinTemp = view.findViewById(R.id.tvMinTemp);
        tvMaxTemp = view.findViewById(R.id.tvMaxTemp);
        tvSunrise = view.findViewById(R.id.tvSunrises);
        tvSunset = view.findViewById(R.id.tvSunsets);
    }
}