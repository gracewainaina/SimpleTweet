package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;
import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.TweetsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Phaser;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))

public class Tweet {

    @ColumnInfo
    @PrimaryKey
    public long id;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    public long userId;

    @Ignore
    public User user;

    public String mediaURL;

    public int retweetCount;

    public int favoriteCount;

    // empty constructor needed by the parceler library
    public Tweet() {
    }

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        try {
            tweet.mediaURL = jsonObject.getJSONObject("entities").getJSONArray("media").getJSONObject(0).getString("media_url_https");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;
        tweet.userId = user.id;
        tweet.retweetCount = jsonObject.getInt("retweet_count");
        tweet.favoriteCount = jsonObject.getInt("favorite_count");

        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getBody() {
        return body;
    }

    public String getMediaURL() {
        return mediaURL;
    }

    public User getUser() {
        return user;
    }

    public long getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getUserId() {
        return userId;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }
}
