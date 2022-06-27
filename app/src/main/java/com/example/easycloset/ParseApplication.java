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
                .applicationId("WBKRlAViXZ9fyoYCAwFQkzMbM0ykRVgwsj6Izovg")
                .clientKey("XfkTYzYvXIwXhH5tmaCtdxU4aq4FpNOleXPofi0V")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
