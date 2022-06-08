package com.example.mainmodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class photoAdapter  extends RecyclerView.Adapter<photoAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<photoData> photoDa;

    photoAdapter(Context context, List<photoData> photoAD) {
        this.photoDa = photoAD;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public photoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.photo_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(photoAdapter.ViewHolder holder, int position) {
        photoData photoData = photoDa.get(position);
        holder.flagView.setImageResource(photoData.getPhoto_res());
        holder.nameView.setText(photoData.getName());
        holder.capitalView.setText(photoData.getComment());
    }

    @Override
    public int getItemCount() {
        return photoDa.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView flagView;
        final TextView nameView, capitalView,dataView;
        ViewHolder(View view){
            super(view);
            flagView = view.findViewById(R.id.photo);
            nameView = view.findViewById(R.id.name);
            capitalView = view.findViewById(R.id.comment);
            dataView = view.findViewById(R.id.DataView);
        }
    }
}

