package com.example.mainmodule;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class FotoListAdapter extends ListAdapter<FotoData, FotoViewHolder> {


    public FotoListAdapter(@NonNull DiffUtil.ItemCallback<FotoData> diffCallback) {
        super(diffCallback);
    }
    @Override
    public FotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return FotoViewHolder.create(parent);
    }
    @Override
    public void onBindViewHolder(FotoViewHolder holder, int position) {
        FotoData foto = getItem(position);
        holder.bind(foto.getName(),foto.getComment());
    }

    static class WordDiff extends DiffUtil.ItemCallback<FotoData> {
        @Override
        public boolean areItemsTheSame(@NonNull FotoData oldItem, @NonNull FotoData newItem) {
            return oldItem == newItem;
        }
        @Override
        public boolean areContentsTheSame(@NonNull FotoData oldItem, @NonNull FotoData newItem) {
            return oldItem.getId()==newItem.getId();
        }
    }
}
