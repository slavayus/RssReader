package com.job.rssreader.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.job.rssreader.ItemWithImage;
import com.job.rssreader.model.ItemsModelContract;
import com.job.rssreader.rss.pojo.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by slavik on 6/4/18.
 */

public class ItemsPresenter {
    private static final String TAG = "ItemsPresenter";
    private final ItemsModelContract model;
    private ItemsPresenterContract view;
    private final String IMG_SRC_REG_EX = "<img src=\"([^>]*)\".*?>";
    private boolean lifehackerIsDone = false;
    private int lifehackerOffset = 0;

    public ItemsPresenter(ItemsModelContract model) {
        this.model = model;
    }

    public void attachView(ItemsPresenterContract view) {
        this.view = view;
    }

    public void viewIsReady() {
        downloadFromLifehacker();
        downloadFromFeedburner();
    }

    private void downloadFromFeedburner() {
        model.loadItemsFromFeedburner(new ItemsModelContract.OnLoadItems() {
            @Override
            public void onSuccess(List<Item> items) {
                List<ItemWithImage> itemWithImages = new ArrayList<>();
                for (Item item : items) {
                    itemWithImages.add(new ItemWithImage(item));
                }
                if (!lifehackerIsDone) {
                    lifehackerOffset = itemWithImages.size();
                }
                view.showItems(itemWithImages);
            }

            @Override
            public void onError() {
                Log.d(TAG, "onError: ");
            }
        });
    }

    private void downloadFromLifehacker() {
        model.loadItemsFromLifehacker(new ItemsModelContract.OnLoadItems() {
            @Override
            public void onSuccess(List<Item> items) {
                List<ItemWithImage> itemWithImages = new ArrayList<>();
                for (Item item : items) {
                    itemWithImages.add(new ItemWithImage(item));
                }
                lifehackerIsDone = true;
                view.showItems(itemWithImages);
                downloadImages(items);
            }

            @Override
            public void onError() {
                Log.d(TAG, "onSuccess: ");
            }
        });
    }

    private void downloadImages(List<Item> items) {
        for (int i = 0; i < items.size(); i++) {
            String url = parseImageUrl(items.get(i).getDescription());
            if (url == null) {
                continue;
            }
            int finalI = i;
            model.loadItemImage(url, new ItemsModelContract.OnLoadImage() {

                @Override
                public void onSuccess(Bitmap bm) {
                    view.onLoadImage(finalI + lifehackerOffset, bm);
                    Log.d(TAG, "onSuccess: load image");
                }

                @Override
                public void onError() {
                    Log.d(TAG, "onError: load image");
                }
            });
        }
    }

    private String parseImageUrl(String description) {
        Pattern imageLinkPattern = Pattern.compile(IMG_SRC_REG_EX);
        Matcher matcher = imageLinkPattern.matcher(description);
        return matcher.find() ? matcher.group(1) : null;
    }
}
