package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    Context context;
    List<Tweet> tweets;

    //pass in the context and list of tweets by the use of a constructor
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //for each row, inflate layout for a tweet
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.item_tweet, parent,false);
        return new ViewHolder(view);
    }

    //bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //get the data at position
        Tweet tweet = tweets.get(position);
        //bind the tweet with view holder
        holder.bind(tweet);

    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }
    // Clean all elements of the recycler
    public void clear(){
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    // Function to get timestamp
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    //define a viewholder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView ivProfileImage;
        TextView tvBody;
        TextView tvScreenName;
        TextView tvTimestamp;
        ImageView mediaEmbedded;
        TextView tvRetweet;
        TextView tvLike;
        ImageView ivLike;
        ImageView ivRetweet;
        ImageView ivReply;

        String relativeTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvTimestamp = itemView.findViewById(R.id.tvTimestamp);
            mediaEmbedded = itemView.findViewById(R.id.mediaEmbedded);
            ivLike = itemView.findViewById(R.id.ivLike);
            ivRetweet = itemView.findViewById(R.id.ivRetweet);
            ivReply = itemView.findViewById(R.id.ivReply);
            tvLike = itemView.findViewById(R.id.tvLike);
            tvRetweet = itemView.findViewById(R.id.tvRetweet);


            itemView.setOnClickListener(this);
        }

        public void bind(Tweet tweet) {
            int radius = 30;
            int margin = 10;
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);
            relativeTime = getRelativeTimeAgo(tweet.createdAt);
            tvTimestamp.setText(relativeTime);
            tvLike.setText(""+tweet.favoriteCount);
            tvRetweet.setText(""+tweet.retweetCount);
            Glide.with(context).load(tweet.user.profileImageUrl).transform(new RoundedCornersTransformation(radius, margin)).into(ivProfileImage);
            if (tweet.mediaURL != null) {
                Log.i("testglides", "mediaURL is "+tweet.mediaURL);
                Glide.with(context).load(tweet.mediaURL).override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).into(mediaEmbedded);
                mediaEmbedded.setVisibility(View.VISIBLE);
            } else {
                mediaEmbedded.setVisibility(View.GONE);
            }

            ivReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, ComposeActivity.class);
                    context.startActivity(i);
                }
            });


        }

        @Override
        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the tweet at the position, this won't work if the class is static
                Tweet tweet = tweets.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, TweetDetailsActivity.class);
                // serialize the tweet using parceler, use its short name as a key
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                intent.putExtra("Timestamp", relativeTime);
                intent.putExtra("retweet count", tvRetweet.getText());
                intent.putExtra("like count", tvLike.getText());
                // show the activity
                context.startActivity(intent);
                Toast.makeText(context, "Tweet clicked", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
