package com.approdevelopers.networkingdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewsAdapter extends ListAdapter<NewsModel,NewsAdapter.NewsViewHolder> {

    public NewsAdapter(@NonNull DiffUtil.ItemCallback<NewsModel> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        NewsModel model = getItem(position);

        holder.txtTitle.setText(model.getTitle());
        holder.txtDesc.setText(model.getDescription());

        String publishedTime = model.getPublishedAt();
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a",Locale.ENGLISH);
        Date parsedDate;
        try {
            parsedDate = inputFormat.parse(publishedTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String formattedDate = null;
        if (parsedDate != null) {
            formattedDate = outputFormat.format(parsedDate);
        }
        holder.txtPublishTime.setText(formattedDate);

        Glide.with(holder.itemView.getContext()).load(model.getUrlToImage()).into(holder.imgNewsBanner);

    }

    static class NewsViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle,txtDesc,txtPublishTime;
        ImageView imgNewsBanner;
        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txt_news_title);
            txtDesc = itemView.findViewById(R.id.txt_news_desc);
            txtPublishTime = itemView.findViewById(R.id.txt_news_publish_time);
            imgNewsBanner = itemView.findViewById(R.id.img_news_banner);

        }
    }
}
