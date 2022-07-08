package com.example.easycloset;


import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("User")
public class User extends ParseObject {

    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_GENDER = "gender";

    public String getKeyFirstName() {
        return getString(KEY_FIRST_NAME);
    }

    public void setKeyFirstName(String name) {
        put(KEY_FIRST_NAME, name);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public String getKeyLastName() {
        return getString(KEY_LAST_NAME);
    }

    public String getCategory() {
        return getString(KEY_GENDER);
    }

    public void setKeyGender(String gender) {
        put(KEY_GENDER, gender);
    }

    public void setKeyLastName(String lastName) {
        put(KEY_LAST_NAME, lastName);
    }

    public void setUsername(String username) {
    }

    public void setPassword(String password) {
    }
}

