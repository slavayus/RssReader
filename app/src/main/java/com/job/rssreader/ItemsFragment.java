package com.job.rssreader;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.job.rssreader.adapter.ItemsAdapter;
import com.job.rssreader.dagger.component.DaggerItemsFragmentComponent;
import com.job.rssreader.dagger.module.ContextModule;
import com.job.rssreader.presenter.ItemsPresenter;
import com.job.rssreader.presenter.ItemsPresenterContract;

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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_items, container, false);

        bind = ButterKnife.bind(this, view);

        DaggerItemsFragmentComponent
                .builder()
                .contextModule(new ContextModule(getContext()))
                .build()
                .injectItemsFragment(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mItemsAdapter);

        mPresenter.attachView(this);
        mPresenter.viewIsReady();
        return view;
    }

    @Override
    public synchronized void showItems(List<ItemWithImage> items) {
        mItemsAdapter.addData(items);
    }


    @Override
    public void setStarState(boolean starState) {
        mItemsAdapter.setStarState(starState);
        mItemsAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean getStarState() {
        return mItemsAdapter.getStarState();
    }

    @Override
    public synchronized void notifyItemChanged(int position) {
        mItemsAdapter.notifyItemChanged(position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_star:
                mPresenter.menuClicked();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
