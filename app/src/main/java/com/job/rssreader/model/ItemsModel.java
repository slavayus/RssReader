package com.job.rssreader.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

    private final RssApi rssApi;

    public ItemsModel(RssApi rssApi) {
        this.rssApi = rssApi;
    }

    @Override
    public void loadItems(OnLoadItems onLoadItems) {
        rssApi.getLifehacker().enqueue(new Callback<Rss>() {
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
        rssApi.loadImage(url).enqueue(new Callback<ResponseBody>() {
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
                onLoadImage.onError();
            }
        });
    }
}
