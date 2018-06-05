package com.job.rssreader.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.job.rssreader.ItemWithImage;
import com.job.rssreader.R;
import com.job.rssreader.model.ItemsModelContract;
import com.job.rssreader.rss.pojo.Item;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
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
    private final Context context;
    private WeakReference<ItemsPresenterContract> view;
    private final String IMG_SRC_REG_EX = "<img src=\"([^>]*)\".*?>";

    public ItemsPresenter(ItemsModelContract model, Context context) {
        this.model = model;
        this.context = context;
    }

    public void attachView(ItemsPresenterContract view) {
        this.view = new WeakReference<>(view);
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
                if (viewIsValid()) {
                    view.get().showItems(itemWithImages);
                }
            }

            @Override
            public void onError() {
                Log.d(TAG, "onError: ");
            }
        });
    }

    private boolean viewIsValid() {
        return view.get() != null;
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
                if (viewIsValid()) {
                    view.get().showItems(itemWithImages);
                }
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
                    if (viewIsValid()) {
                        view.get().notifyItemChanged(items.get(finalI).getId());
                    }
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

    public void itemClicked(ItemWithImage item, int position) {
        if (!viewIsValid()) {
            return;
        }
        ItemsPresenterContract storedView = view.get();
        LayoutInflater inflater = storedView.getLayoutInflater();
        View view = inflater.inflate(R.layout.list_item, null);
        ImageView itemImage = view.findViewById(R.id.item_image);
        TextView itemTitle = view.findViewById(R.id.item_title);

        itemTitle.setText(item.getItem().getTitle());
        if (item.getImage() != null) {
            itemImage.setImageBitmap(item.getImage());
        } else {
            itemImage.setImageResource(R.drawable.empty_image);
        }

        new AlertDialog.Builder(context)
                .setView(view)
                .setPositiveButton(item.isStarred() ? R.string.unstar : R.string.star, (dialog, which) -> {
                    item.setStarred(!item.isStarred());
                    storedView.notifyItemChanged(position);
                })
                .setNeutralButton(R.string.open_browser, (dialog, which) -> {
                    String url = item.getItem().getLink();
                    if (url == null) {
                        Toast.makeText(context, R.string.there_is_not_url, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "http://" + url;
                    }
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse(url));
                    context.startActivity(intent);
                })
                .create()
                .show();
    }

    public void menuClicked() {
        if (viewIsValid()) {
            view.get().setStarState(!view.get().getStarState());
        }
    }
}
