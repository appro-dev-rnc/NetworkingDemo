package com.approdevelopers.networkingdemo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class DiffUtilsNewsModel extends DiffUtil.ItemCallback<NewsModel.Articles> {
    @Override
    public boolean areItemsTheSame(@NonNull NewsModel.Articles oldItem, @NonNull NewsModel.Articles newItem) {
        return Objects.equals(oldItem.getTitle(),newItem.getTitle());
    }

    @Override
    public boolean areContentsTheSame(@NonNull NewsModel.Articles oldItem, @NonNull NewsModel.Articles newItem) {
        return oldItem.toString().equals(newItem.toString());
    }
}
