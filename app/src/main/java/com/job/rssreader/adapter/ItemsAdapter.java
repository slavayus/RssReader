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

    public ItemsAdapter(OnItemClickListener listener) {
        this.listener = listener;
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
        holder.bind(mData.get(position), listener, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
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
            linearLayout.setOnClickListener(v -> listener.onItemClick(item, position));
            itemTitle.setText(item.getItem().getTitle());

            if (item.isStarred()) {
                itemTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_star, 0);
            } else {
                itemTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }


            if (item.getImage() != null) {
                imageView.setImageBitmap(item.getImage());
            } else {
                imageView.setImageResource(R.drawable.empty_image);
            }
        }
    }
}
