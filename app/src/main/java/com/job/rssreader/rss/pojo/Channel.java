package com.job.rssreader.rss.pojo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by slavik on 6/4/18.
 */

@Root(name = "channel", strict = false)
public class Channel {

    @Element(name = "title")
    private String title;

    @Element(name = "link")
    private String link;

    @ElementList(name = "item", inline = true)
    private List<Item> itemList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<Item> getItems() {
        return itemList;
    }

    public void setItem(List<Item> itemList) {
        this.itemList = itemList;
    }
}
