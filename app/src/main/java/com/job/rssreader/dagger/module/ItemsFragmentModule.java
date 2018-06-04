package com.job.rssreader.dagger.module;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

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

@Module(includes = {LifehackerApiModule.class, ApplicationContextModule.class, FeedburnerApiModule.class})
public class ItemsFragmentModule {

    @Provides
    @Singleton
    ItemsAdapter.OnItemClickListener onItemClickListener(Context context) {
        return item -> {
            String url = item.getLink();
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(url));
            context.startActivity(intent);
        };
    }

    @Provides
    @Singleton
    ItemsAdapter itemsAdapter(ItemsAdapter.OnItemClickListener onItemClickListener) {
        return new ItemsAdapter(onItemClickListener);
    }

    @Provides
    @Singleton
    ItemsPresenter itemsPresenter(ItemsModelContract itemsModelContract) {
        return new ItemsPresenter(itemsModelContract);
    }

    @Provides
    @Singleton
    ItemsModelContract itemsModelContract(@LifehackerApi RssApi lifehacker, @FeedburnerApi RssApi feedburner) {
        return new ItemsModel(lifehacker, feedburner);
    }
}
