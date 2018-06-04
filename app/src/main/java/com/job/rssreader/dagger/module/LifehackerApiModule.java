package com.job.rssreader.dagger.module;

import com.job.rssreader.dagger.annotation.LifehackerApi;
import com.job.rssreader.dagger.annotation.LifehackerRetrofit;
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
public class LifehackerApiModule {

    @Singleton
    @Provides
    @LifehackerApi
    RssApi rssApi(@LifehackerRetrofit Retrofit retrofit) {
        return retrofit.create(RssApi.class);
    }

    @Singleton
    @Provides
    @LifehackerRetrofit
    Retrofit retrofit(SimpleXmlConverterFactory simpleXmlConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(URLHelper.LIFEHACKER_BASE_URL)
                .addConverterFactory(simpleXmlConverterFactory)
                .build();
    }


}
