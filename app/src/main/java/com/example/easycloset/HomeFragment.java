package com.example.easycloset;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.gms.location.LocationRequest;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;


public class HomeFragment extends Fragment {

    private Weather weather;
    private EditText etCity;
    private ImageButton btnSearch;
    private TextView tvCity, tvCountry, tvTemp, tvForecast, tvHumidity, tvMinTemp, tvMaxTemp, tvSunrise, tvSunset;
    private ProgressDialog progressDialog;
    private double latitude;
    private double longitude;
    private LocationRequest mLocationRequest;
    private NestedScrollView nestedScrollView;
    private MainActivity activity;

    public HomeFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        populateHomeFragment();

    }

    public HomeFragment(MainActivity mainActivity) {
        activity = mainActivity;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching Weather...");
        progressDialog.show();

        btnSearch = view.findViewById(R.id.ivSearchBtn);
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
        nestedScrollView = view.findViewById(R.id.homeScreen);
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void populateHomeFragment() {
        AsyncHttpClient client = new AsyncHttpClient();
        String apiByLat = "https://api.openweathermap.org/data/2.5/weather?lat=" + getLatitude() + "&lon=" + getLongitude() + "&units=imperial&appid=942dd77fa358eb3439a8212cb16724cd";

        client.get(apiByLat, new JsonHttpResponseHandler() {
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
                    tvMinTemp.setText(weather.getTempMin());
                    tvMaxTemp.setText(weather.getTempMax());
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