package com.job.rssreader.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.job.rssreader.ItemWithImage;
import com.job.rssreader.model.ItemsModelContract;
import com.job.rssreader.rss.pojo.Item;

import java.util.ArrayList;
import java.util.Collections;
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
                sortItems(items);
                List<ItemWithImage> itemWithImages = new ArrayList<>();
                for (Item item : items) {
                    itemWithImages.add(new ItemWithImage(item));
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
                sortItems(items);
                List<ItemWithImage> itemWithImages = new ArrayList<>();
                for (Item item : items) {
                    itemWithImages.add(new ItemWithImage(item));
                }
                view.showItems(itemWithImages);
                downloadImages(itemWithImages);
            }

            @Override
            public void onError() {
                Log.d(TAG, "onSuccess: ");
            }
        });
    }

    private void sortItems(List<Item> items) {
        Collections.sort(items, (o1, o2) -> o1.getPubDate().compareTo(o2.getPubDate()));
        Collections.reverse(items);
    }

    private void downloadImages(List<ItemWithImage> items) {
        for (int i = 0; i < items.size(); i++) {
            String url = parseImageUrl(items.get(i).getItem().getDescription());
            if (url == null) {
                continue;
            }
            int finalI = i;
            model.loadItemImage(url, new ItemsModelContract.OnLoadImage() {

                @Override
                public void onSuccess(Bitmap bm) {
                    items.get(finalI).setImage(bm);
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
