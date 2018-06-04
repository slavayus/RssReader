package com.job.rssreader;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.job.rssreader.rss.RssApi;
import com.job.rssreader.rss.pojo.Rss;
import com.job.rssreader.utils.URLHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class PostActivity extends AppCompatActivity {

    private static final String TAG = "PostActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLHelper.LIFEHACKER_BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        retrofit.create(RssApi.class).getLifehacker().enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                Log.d(TAG, "onResponse: " + response.body().getChannel().getTitle());

            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());

            }
        });

    }
}

/*
new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "onResponse: " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();

                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());

            }
        }
 */

/*new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                Log.d(TAG, "onResponse: " + response.body().getTitle());
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        }*/
