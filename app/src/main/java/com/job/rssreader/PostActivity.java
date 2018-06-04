package com.job.rssreader;

import android.support.v4.app.Fragment;

public class PostActivity extends SingleFragmentActivity {

    private static final String TAG = "PostActivity";

    @Override
    protected Fragment createFragment() {
        return ItemsFragment.newInstance();
    }
}
