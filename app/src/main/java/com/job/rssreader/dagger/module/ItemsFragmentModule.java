package com.job.rssreader.dagger.module;

import android.content.Context;

import com.job.rssreader.adapter.ItemsAdapter;
import com.job.rssreader.dagger.annotation.FeedburnerApi;
import com.job.rssreader.dagger.annotation.LifehackerApi;
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

@Module(includes = {LifehackerApiModule.class, ContextModule.class, FeedburnerApiModule.class})
public class ItemsFragmentModule {

    @Provides
    @Singleton
    ItemsAdapter.OnItemClickListener onItemClickListener(ItemsPresenter itemsPresenter) {
        return itemsPresenter::itemClicked;
    }

    @Provides
    @Singleton
    ItemsAdapter itemsAdapter(ItemsAdapter.OnItemClickListener listener) {
        return new ItemsAdapter(listener);
    }

    @Provides
    @Singleton
    ItemsPresenter itemsPresenter(ItemsModelContract itemsModelContract, Context context) {
        return new ItemsPresenter(itemsModelContract, context);
    }

    @Provides
    @Singleton
    ItemsModelContract itemsModelContract(@LifehackerApi RssApi lifehacker, @FeedburnerApi RssApi feedburner) {
        return new ItemsModel(lifehacker, feedburner);
    }
}
