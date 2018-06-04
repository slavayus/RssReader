package com.job.rssreader;

import android.graphics.Bitmap;

import com.job.rssreader.rss.pojo.Item;


public class ItemWithImage {
    private Item item;
    private Bitmap image;

    public ItemWithImage(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
