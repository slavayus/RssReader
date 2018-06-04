package com.job.rssreader.model;

import com.job.rssreader.rss.pojo.Item;

import java.util.List;

/**
 * Created by slavik on 6/4/18.
 */

public interface ItemsModelContract {
    void loadItems(OnLoadItems onLoadItems);

    interface OnLoadItems {
        void onSuccess(List<Item> items);

        void onError();
    }
}
