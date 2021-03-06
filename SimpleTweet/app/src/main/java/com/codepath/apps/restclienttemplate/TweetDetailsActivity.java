package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailsActivity extends AppCompatActivity {

    public Tweet tweet;

    public TextView tvBody;
    public TextView tvName;
    public TextView tvScreenName;
    public TextView tvTimestamp;
    public ImageView ivProfileImage;
    public ImageView mediaEmbedded;
    public ImageView ivLike;
    public ImageView ivRetweet;
    public ImageView ivReply;
    public TextView tvRetweet;
    public TextView tvLike;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        tvBody = findViewById(R.id.tvBody);
        tvName = findViewById(R.id.tvName);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvTimestamp = findViewById(R.id.tvTimestamp);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        mediaEmbedded = findViewById(R.id.mediaEmbedded);

        ivLike = findViewById(R.id.ivLike);
        ivReply = findViewById(R.id.ivReply);
        ivRetweet = findViewById(R.id.ivRetweet);

        tvRetweet = findViewById(R.id.tvRetweet);
        tvLike = findViewById(R.id.tvLike);

        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        tvBody.setText(tweet.getBody());
        tvName.setText(tweet.getUser().name);
        tvScreenName.setText(tweet.getUser().screenName);
        tvTimestamp.setText(getIntent().getStringExtra("Timestamp"));
        tvRetweet.setText(getIntent().getStringExtra("retweet count"));
        tvLike.setText(getIntent().getStringExtra("like count"));

        Glide.with(TweetDetailsActivity.this).load(tweet.getUser().profileImageUrl).into(ivProfileImage);
        Glide.with(TweetDetailsActivity.this).load(tweet.getMediaURL()).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mediaEmbedded);


    }
}