package com.job.rssreader.model;

import android.graphics.Bitmap;

import com.job.rssreader.rss.pojo.Item;

import java.util.List;

/**
 * Created by slavik on 6/4/18.
 */

public interface ItemsModelContract {
    void loadItems(OnLoadItems onLoadItems);

    void loadItemImage(String url, OnLoadImage onLoadImage);

    interface OnLoadItems {
        void onSuccess(List<Item> items);

        void onError();
    }

    interface OnLoadImage {
        void onSuccess(Bitmap bm);

        void onError();

    }
}
