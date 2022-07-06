package com.example.easycloset;

import android.util.Log;

import androidx.annotation.NonNull;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@ParseClassName("Item")
public class Item extends ParseObject {

    private static final String KEY_COLOUR = "colour";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_USER = "user";
    private static final String KEY_CATEGORY = "category";

    public String getColour() {
        return getString(KEY_COLOUR);
    }

    public void setColour(String colour) {
        put(KEY_COLOUR, colour);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public String getCategory() {
        return getString(KEY_CATEGORY);
    }

    public void setCategory(String category) {
        put(KEY_CATEGORY, category);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    @NonNull
    public static String Time(Date createdAt) {
        return new SimpleDateFormat("EEE, MMM d, ''yy", Locale.ENGLISH).format(new Date(String.valueOf(createdAt)));
    }
}