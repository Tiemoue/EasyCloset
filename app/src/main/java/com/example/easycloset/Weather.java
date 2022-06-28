package com.example.easycloset;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather {

    private String temp;
    private String tempMin;
    private String tempMax;
    private String icon;
    private int timezone;
    private String humidity;
    private long updatedAt;
    private String cityName;
    private String countryName;
    private String cast;
    private JSONObject main;
    private JSONObject weather;
    private JSONObject sys;
    private long sunrise;
    private long sunset;
    private String weatherMain;

    public Weather() {
    }

    public Weather(JSONObject jsonObject) throws JSONException {
        main = jsonObject.getJSONObject("main");
        cityName = jsonObject.getString("name");
        timezone = jsonObject.getInt("timezone");
        weather = jsonObject.getJSONArray("weather").getJSONObject(0);
        sys = jsonObject.getJSONObject("sys");
        countryName = sys.getString("country");
        updatedAt = jsonObject.getLong("dt");
        temp = main.getString("temp");
        cast = weather.getString("description");
        icon = weather.getString("icon");
        weatherMain = weather.getString("main");
        humidity = main.getString("humidity");
        tempMin = main.getString("temp_min");
        tempMax = main.getString("temp_max");
        sunrise = sys.getLong("sunrise");
        sunset = sys.getLong("sunset");
    }

    public String getTemp() {
        return temp;
    }

    public String getTempMin() {
        return tempMin;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getIcon() {
        return icon;
    }

    public int getTimezone() {
        return timezone;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getUpdatedAt() {
        return "Last Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCast() {
        return cast;
    }

    public JSONObject getMain() {
        return main;
    }

    public JSONObject getWeather() {
        return weather;
    }

    public JSONObject getSys() {
        return sys;
    }

    public String getSunrise() {
        return new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000));
    }

    public String getSunset() {
        return new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000));
    }
}