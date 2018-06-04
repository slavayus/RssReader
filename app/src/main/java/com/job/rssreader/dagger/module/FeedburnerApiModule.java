package com.job.rssreader.dagger.module;

import com.job.rssreader.dagger.annotation.FeedburnerApi;
import com.job.rssreader.dagger.annotation.FeedburnerRetrofit;
import com.job.rssreader.rss.RssApi;
import com.job.rssreader.utils.URLHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by slavik on 6/4/18.
 */

@Module(includes = {XmlConverterModule.class})
public class FeedburnerApiModule {

    @Singleton
    @Provides
    @FeedburnerApi
    RssApi rssApi(@FeedburnerRetrofit Retrofit retrofit) {
        return retrofit.create(RssApi.class);
    }

    @Singleton
    @Provides
    @FeedburnerRetrofit
    Retrofit retrofit(SimpleXmlConverterFactory simpleXmlConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(URLHelper.FEEDBURNER_BASE_URL)
                .addConverterFactory(simpleXmlConverterFactory)
                .build();
    }


}
