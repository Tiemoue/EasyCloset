package com.example.easycloset;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Item.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("Ip3Oh4XMzgSUw2HLU5ZcpIBUgoCJU7MXlv8z09qY")
                .clientKey("KaVpIZjMLg9CWtFcfY5FmwQruN2mDcw7nAZJA8lw")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
