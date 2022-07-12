package com.example.easycloset.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.easycloset.Adapters.ClothesAdapter;
import com.example.easycloset.Models.Clothes;
import com.example.easycloset.Models.User;
import com.example.easycloset.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class ClothesActivity extends AppCompatActivity {
    public static final String TAG = ".ClothesActivity";
    List<Clothes> clothesList;
    ClothesAdapter clothesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes);
        RecyclerView rvClothes = findViewById(R.id.rvClothes);
        clothesList = new ArrayList<>();
        clothesAdapter = new ClothesAdapter(this, clothesList);
        rvClothes.setAdapter(clothesAdapter);
        rvClothes.setLayoutManager(new LinearLayoutManager(this));
        User user = (User) User.getCurrentUser();
        String gender = user.getKeyGender();
        String item = getIntent().getStringExtra("category");
        fetchShoppingData(gender, item);
    }

    public void fetchShoppingData(String gender, String item) {
        String ApiSearch = "https://serpapi.com/search.json?q=" + gender + "+" + item + "+buy&location=Austin,+Texas,+United+States&hl=en&gl=us&api_key=6a846e9a7d4830674270838382f4ba7594a70e32bd441ac942d9f51ead70f3fa";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(ApiSearch, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("shopping_results");
                    clothesList.addAll(Clothes.fromJsonArray(results));
                    clothesAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json exception", e);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}
