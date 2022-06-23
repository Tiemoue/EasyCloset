package com.example.easycloset;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;


public class HomeFragment extends Fragment {

    public Weather weather;
    private final String API_BY_CITY = "https://api.openweathermap.org/data/2.5/weather?q=" + "seattle" + "&units=imperial" + "&appid=" + "942dd77fa358eb3439a8212cb16724cd";
    private ImageView search;
    private EditText etCity;
    private ImageButton btnSearch;
    private TextView tvCity, tvCountry, tvTemp, tvForecast, tvHumidity, tvMinTemp, tvMaxTemp, tvSunrise, tvSunset;
    private ProgressDialog progressDialog;

    public HomeFragment() {
        // Required empty public constructor
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching Weather...");
        progressDialog.show();

        search = view.findViewById(R.id.search);
        btnSearch = view.findViewById(R.id.ivSearchButton);
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

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.get(API_BY_CITY, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    progressDialog.dismiss();
                    weather = new Weather(jsonObject);
                    tvCity.setText(weather.getCityName());
                    tvCountry.setText(weather.getCountryName());
                    tvForecast.setText(weather.getCast());
                    tvTemp.setText(weather.getTemp());
                    tvHumidity.setText(weather.getHumidity());
                    tvMinTemp.setText(weather.getTemp_min());
                    tvMaxTemp.setText(weather.getTemp_max());
                    tvSunrise.setText(weather.getSunrise());
                    tvSunset.setText(weather.getSunset());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e("response from server", "error");
            }
        });

    }
}