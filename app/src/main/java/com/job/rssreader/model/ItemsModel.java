package com.job.rssreader.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.job.rssreader.rss.RssApi;
import com.job.rssreader.rss.pojo.Rss;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by slavik on 6/4/18.
 */

public class ItemsModel implements ItemsModelContract {

    private static final String TAG = "ItemsModel";
    private final RssApi lifehacker;
    private final RssApi feedburner;

    public ItemsModel(RssApi lifehacker, RssApi feedburner) {
        this.lifehacker = lifehacker;
        this.feedburner = feedburner;
    }

    @Override
    public void loadItemsFromLifehacker(OnLoadItems onLoadItems) {
        lifehacker.getLifehacker().enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onLoadItems.onSuccess(response.body().getChannel().getItems());
                        return;
                    }
                }
                onLoadItems.onError();
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                onLoadItems.onError();
            }
        });
    }

    @Override
    public void loadItemImage(String url, OnLoadImage onLoadImage) {
        lifehacker.loadImage(url).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Bitmap bm = BitmapFactory.decodeStream(response.body().byteStream());
                        onLoadImage.onSuccess(bm);
                        return;
                    }
                }
                onLoadImage.onError();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                onLoadImage.onError();
            }
        });
    }

    @Override
    public void loadItemsFromFeedburner(OnLoadItems onLoadItems) {
        feedburner.getFeedburner().enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                Log.d(TAG, "onResponse: " + response.body().getChannel().getItems().size());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        onLoadItems.onSuccess(response.body().getChannel().getItems());
                        return;
                    }
                }
                onLoadItems.onError();
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                onLoadItems.onError();
            }
        });
    }
}
