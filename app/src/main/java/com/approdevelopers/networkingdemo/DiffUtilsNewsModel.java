package com.approdevelopers.networkingdemo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class DiffUtilsNewsModel extends DiffUtil.ItemCallback<NewsModel> {
    @Override
    public boolean areItemsTheSame(@NonNull NewsModel oldItem, @NonNull NewsModel newItem) {
        return Objects.equals(oldItem.getTitle(),newItem.getTitle());
    }

    @Override
    public boolean areContentsTheSame(@NonNull NewsModel oldItem, @NonNull NewsModel newItem) {
        return oldItem.toString().equals(newItem.toString());
    }
}
