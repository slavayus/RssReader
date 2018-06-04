package com.job.rssreader.rss;

import com.job.rssreader.rss.pojo.Rss;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by slavik on 6/4/18.
 */

public interface RssApi {
    @GET("/rss")
    Call<Rss> getLifehacker();

}
