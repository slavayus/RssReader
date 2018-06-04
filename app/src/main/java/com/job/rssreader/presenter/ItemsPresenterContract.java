package com.job.rssreader.presenter;

import com.job.rssreader.rss.pojo.Item;

import java.util.List;

/**
 * Created by slavik on 6/4/18.
 */

public interface ItemsPresenterContract {
    void showItems(List<Item> items);
}
