package com.job.rssreader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.job.rssreader.adapter.ItemsAdapter;
import com.job.rssreader.dagger.component.DaggerItemsFragmentComponent;
import com.job.rssreader.dagger.module.ApplicationContextModule;
import com.job.rssreader.presenter.ItemsPresenter;
import com.job.rssreader.presenter.ItemsPresenterContract;
import com.job.rssreader.rss.pojo.Item;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ItemsFragment extends Fragment implements ItemsPresenterContract {
    private Unbinder bind;
    @BindView(R.id.recycler_view_items)
    RecyclerView mRecyclerView;
    @Inject
    ItemsPresenter mPresenter;
    @Inject
    ItemsAdapter mItemsAdapter;


    public static ItemsFragment newInstance() {
        return new ItemsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        bind = ButterKnife.bind(this, view);

        DaggerItemsFragmentComponent
                .builder()
                .applicationContextModule(new ApplicationContextModule(getContext()))
                .build()
                .injectItemsFragment(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mItemsAdapter);

        mPresenter.attachView(this);
        mPresenter.viewIsReady();
        return view;
    }

    @Override
    public void showItems(List<Item> items) {
        mItemsAdapter.setData(items);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
