package com.codepath.apps.restclienttemplate.models;

import androidx.room.Embedded;

import java.util.ArrayList;
import java.util.List;

// SQL query returns a combination of user and the tweet, hence this class
public class TweetWithUser {

    // preserving encapsulation of all attributes of user and tweet
    @Embedded
    public User user;

    @Embedded(prefix = "tweet_")
    public Tweet tweet;


    public static List<Tweet> getTweetList(List<TweetWithUser> tweetWithUsers) {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < tweetWithUsers.size(); i++) {
            Tweet tweet = tweetWithUsers.get(i).tweet;
            tweet.user = tweetWithUsers.get(i).user;
            tweets.add(tweet);
        }
        return tweets;
    }
}
