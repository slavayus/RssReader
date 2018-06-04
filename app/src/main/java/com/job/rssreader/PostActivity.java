package com.job.rssreader;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.job.rssreader.github.DaggerRssApiComponent;
import com.job.rssreader.rss.RssApi;
import com.job.rssreader.rss.pojo.Rss;
import com.job.rssreader.utils.URLHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

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
