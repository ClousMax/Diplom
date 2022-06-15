package com.example.mainmodule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
// выбирает значение из layout Потом создаётся класс adapter
public class FotoViewHolder extends RecyclerView.ViewHolder {
    final TextView nameView, commentView,dateView;

    private FotoViewHolder(View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.foto_view_name);
        commentView = itemView.findViewById(R.id.foto_view_comment);
        dateView = itemView.findViewById(R.id.foto_view_date);
    }
    public void bind(String name, String comm) {
        nameView.setText(name);
        commentView.setText(comm);

    }

    static FotoViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);
        return new FotoViewHolder(view);
    }


}

