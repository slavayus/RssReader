package com.job.rssreader.github.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by slavik on 6/4/18.
 */

@Module
public class XmlConverterModule {

    @Provides
    @Singleton
    SimpleXmlConverterFactory simpleXmlConverterFactory() {
        return SimpleXmlConverterFactory.create();
    }
}
