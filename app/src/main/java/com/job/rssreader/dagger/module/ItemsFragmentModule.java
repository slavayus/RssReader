package com.job.rssreader.dagger.module;

import com.job.rssreader.adapter.ItemsAdapter;
import com.job.rssreader.model.ItemsModel;
import com.job.rssreader.model.ItemsModelContract;
import com.job.rssreader.presenter.ItemsPresenter;
import com.job.rssreader.rss.RssApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by slavik on 6/4/18.
 */

@Module(includes = {RssApiModule.class})
public class ItemsFragmentModule {

    @Provides
    @Singleton
    ItemsAdapter itemsAdapter() {
        return new ItemsAdapter();
    }

    @Provides
    @Singleton
    ItemsPresenter itemsPresenter(ItemsModelContract itemsModelContract) {
        return new ItemsPresenter(itemsModelContract);
    }

    @Provides
    @Singleton
    ItemsModelContract itemsModelContract(RssApi rssApi) {
        return new ItemsModel(rssApi);
    }
}
