package com.job.rssreader;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.job.rssreader.dagger.component.DaggerRssApiComponent;
import com.job.rssreader.rss.pojo.Rss;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostActivity extends SingleFragmentActivity {

    private static final String TAG = "PostActivity";

    @Override
    protected Fragment createFragment() {
        return ItemsFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerRssApiComponent
                .create()
                .getRssApi()
                .getLifehacker().enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                Log.d(TAG, "onResponse: " + response.body().getChannel().getItem().get(0).toString());

            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });

    }
}
