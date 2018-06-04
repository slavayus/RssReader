package com.job.rssreader.rss;

import com.job.rssreader.rss.pojo.Rss;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by slavik on 6/4/18.
 */

public interface RssApi {
    @GET("/rss")
    Call<Rss> getLifehacker();

    @GET
    Call<ResponseBody> loadImage(@Url String url);

}
