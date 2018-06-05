package com.job.rssreader.presenter;

import android.graphics.Bitmap;

import com.job.rssreader.ItemWithImage;

import java.util.List;

/**
 * Created by slavik on 6/4/18.
 */

public interface ItemsPresenterContract {
    void showItems(List<ItemWithImage> items);
}
