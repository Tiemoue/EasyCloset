package com.example.easycloset.Models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_CAPTION = "caption";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_LIKED_BY = "liked_by";

    public String getCaption() {
        return getString(KEY_CAPTION);
    }

    public void setCaption(String caption) {
        put(KEY_CAPTION, caption);
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

    public List<ParseUser> getLikedBy() {
        List<ParseUser> likedby = getList(KEY_LIKED_BY);
        if (likedby != null) {
            return likedby;
        } else {
            return new ArrayList<ParseUser>();
        }
    }

    public void setLikedBy(List<ParseUser> user) {
        put(KEY_LIKED_BY, user);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }
        return "";
    }

    public String getLikesCount() {
        int likeCount = getLikedBy().size();
        return likeCount + (likeCount == 1 ? " likes" : " like");
    }

    public boolean isLikedByCurrentUser() {
        List<ParseUser> likedBy = getLikedBy();
        for (int i = 0; i < getLikedBy().size(); i++) {
            if (likedBy.get(i).hasSameId(ParseUser.getCurrentUser())) {
                return true;
            }
        }
        return false;
    }

    public void unlike() {
        List<ParseUser> likedBy = getLikedBy();
        for (int i = 0; i < getLikedBy().size(); i++) {
            if (likedBy.get(i).hasSameId(ParseUser.getCurrentUser())) {
                likedBy.remove(i);
            }
            setLikedBy(likedBy);
            saveInBackground();
        }
    }

    public void like() {
        unlike();
        List<ParseUser> likedby = getLikedBy();
        likedby.add(ParseUser.getCurrentUser());
        setLikedBy(likedby);
        saveInBackground();
    }
}
