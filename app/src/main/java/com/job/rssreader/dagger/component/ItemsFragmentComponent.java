package com.job.rssreader.dagger.component;

import com.job.rssreader.ItemsFragment;
import com.job.rssreader.dagger.module.ItemsFragmentModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by slavik on 6/4/18.
 */

@Singleton
@Component(modules = {ItemsFragmentModule.class})
public interface ItemsFragmentComponent {
    void injectItemsFragment(ItemsFragment itemsFragment);
}
