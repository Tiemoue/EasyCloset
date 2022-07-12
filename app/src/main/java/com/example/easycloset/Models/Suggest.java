package com.example.easycloset.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Suggest")
public class Suggest extends ParseObject {

    public static final String KEY_BASE = "baseLayer";
    public static final String KEY_OUTER = "outerLayer";
    public static final String KEY_BOTTOM = "bottomLayer";
    public static final String KEY_FOOT = "feet";

    public ParseFile getOuter() {
        return getParseFile(KEY_OUTER);
    }

    public void setOuter(ParseFile image) {
        put(KEY_OUTER, image);
    }

    public ParseFile getBase() {
        return getParseFile(KEY_BASE);
    }

    public void setBase(ParseFile image) {
        put(KEY_BASE, image);
    }

    public ParseFile getBottom() {
        return getParseFile(KEY_BOTTOM);
    }

    public void setBottom(ParseFile image) {
        put(KEY_BOTTOM, image);
    }

    public ParseFile getFoot() {
        return getParseFile(KEY_FOOT);
    }

    public void setFoot(ParseFile image) {
        put(KEY_FOOT, image);
    }
}
