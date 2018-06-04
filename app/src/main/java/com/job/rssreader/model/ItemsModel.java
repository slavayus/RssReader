package com.job.rssreader.model;

import com.job.rssreader.rss.RssApi;
import com.job.rssreader.rss.pojo.Rss;

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
}
