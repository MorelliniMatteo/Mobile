package com.example.MyMangaList.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.MyMangaList.R;

/**
 * A ViewHolder describes an item view and the metadata about its place within the RecyclerView.
 */
public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView itemImageView;
    TextView itemTextView;
    TextView dateTextView;
    TextView priceTextView;
    TextView wear_count_textview;

    private final OnItemListener itemListener;

    public CardViewHolder(@NonNull View itemView, OnItemListener listener) {
        super(itemView);
        itemImageView = itemView.findViewById(R.id.item_imageview);
        itemTextView = itemView.findViewById(R.id.item_textview);
        dateTextView = itemView.findViewById(R.id.date_textview);
        priceTextView = itemView.findViewById(R.id.price_textview);
        wear_count_textview = itemView.findViewById(R.id.wear_count_textview);
        itemListener = listener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemListener.onItemClick(getAdapterPosition());
    }
}
