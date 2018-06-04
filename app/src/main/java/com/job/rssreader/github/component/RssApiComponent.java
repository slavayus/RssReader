package com.job.rssreader.github.component;

import com.job.rssreader.github.module.RssApiModule;
import com.job.rssreader.rss.RssApi;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by slavik on 6/4/18.
 */

@Singleton
@Component(modules = {RssApiModule.class})
public interface RssApiComponent {
    RssApi getRssApi();
}
