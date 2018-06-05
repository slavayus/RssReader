package com.job.rssreader;

import android.graphics.Bitmap;

import com.job.rssreader.rss.pojo.Item;

import java.lang.ref.SoftReference;


public class ItemWithImage {
    private int id;
    private Item item;
    private Bitmap image;
    private boolean starred;

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

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
