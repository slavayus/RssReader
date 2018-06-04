package com.job.rssreader.presenter;

import android.util.Log;

import com.job.rssreader.model.ItemsModelContract;
import com.job.rssreader.rss.pojo.Item;

import java.util.List;

/**
 * Created by slavik on 6/4/18.
 */

public class ItemsPresenter {
    private static final String TAG = "ItemsPresenter";
    private final ItemsModelContract model;
    private ItemsPresenterContract view;

    public ItemsPresenter(ItemsModelContract model) {
        this.model = model;
    }

    public void attachView(ItemsPresenterContract view) {
        this.view = view;
    }

    public void viewIsReady() {
        model.loadItems(new ItemsModelContract.OnLoadItems() {
            @Override
            public void onSuccess(List<Item> items) {
                view.showItems(items);
                Log.d(TAG, "onSuccess: "+items.get(0).toString());
            }

            @Override
            public void onError() {
                Log.d(TAG, "onSuccess: ");
            }
        });
    }
}
