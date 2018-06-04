package com.job.rssreader.presenter;

import com.job.rssreader.model.ItemsModelContract;

/**
 * Created by slavik on 6/4/18.
 */

public class ItemsPresenter {
    private final ItemsModelContract model;
    private ItemsPresenterContract view;

    public ItemsPresenter(ItemsModelContract model) {
        this.model = model;
    }

    public void attachView(ItemsPresenterContract view) {
        this.view = view;
    }

    public void viewIsReady() {

    }
}
