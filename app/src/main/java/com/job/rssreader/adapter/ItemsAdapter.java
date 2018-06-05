package com.job.rssreader.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.job.rssreader.ItemWithImage;
import com.job.rssreader.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by slavik on 6/4/18.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {
    private final OnItemClickListener listener;
    private List<ItemWithImage> mData = new ArrayList<>();
    private boolean starState;

    public ItemsAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setStarState(boolean starState) {
        this.starState = starState;
    }

    public boolean getStarState() {
        return starState;
    }

    public interface OnItemClickListener {
        void onItemClick(ItemWithImage item, int position);
    }

    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
        if (starState) {
            position++;
            int count = 0;
            int i = 0;
            while (count < position) {
                if (mData.get(i++).isStarred()) {
                    count++;
                }
            }
            holder.bind(mData.get(i - 1), listener, position);
            return;
        }
        holder.bind(mData.get(position), listener, position);
    }

    @Override
    public int getItemCount() {
        if (starState) {
            int count = 0;
            for (ItemWithImage mDatum : mData) {
                if (mDatum.isStarred()) {
                    count++;
                }
            }
            return count;
        } else {
            return mData.size();
        }
    }

    public void addData(List<ItemWithImage> data) {
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    class ItemsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_image)
        ImageView imageView;
        @BindView(R.id.item_title)
        TextView itemTitle;
        @BindView(R.id.data_container)
        LinearLayout linearLayout;

        ItemsHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(ItemWithImage item, OnItemClickListener listener, int position) {
            if (starState) {
                linearLayout.setOnClickListener(null);
            } else {
                linearLayout.setOnClickListener(v -> listener.onItemClick(item, position));
            }
            itemTitle.setText(item.getItem().getTitle());

            if (item.isStarred()) {
                itemTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_star, 0);
            } else {
                itemTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }


            if (item.getImage() != null && item.getImage().get() != null) {
                imageView.setImageBitmap(item.getImage().get());
            } else {
                imageView.setImageResource(R.drawable.empty_image);
            }
        }
    }
}
