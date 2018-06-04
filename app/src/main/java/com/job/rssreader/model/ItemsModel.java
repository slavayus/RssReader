package com.job.rssreader.model;

import com.job.rssreader.rss.RssApi;

/**
 * Created by slavik on 6/4/18.
 */

public class ItemsModel implements ItemsModelContract {

    private final RssApi rssApi;

    public ItemsModel(RssApi rssApi) {
        this.rssApi = rssApi;
    }


}
