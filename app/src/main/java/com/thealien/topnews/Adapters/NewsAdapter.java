package com.thealien.topnews.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import com.thealien.topnews.Models.Article;
import com.thealien.topnews.R;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private ArrayList<Article> newsList;
    private Context context;
    private OnItemClickListener mListener;

   public interface OnItemClickListener{
       void onItemClick(int position , Article article , ImageView imageView);
   }

   public void setOnItemClickListener(OnItemClickListener listener){
       this.mListener = listener;
   }

    public NewsAdapter(Context context , ArrayList<Article> list){
        this.context = context;
        this.newsList = list;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item,viewGroup,false);
        return new NewsViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder newsViewHolder, int position) {
        Article currentItem = newsList.get(position);

        final String newsImageUrl = currentItem.getNewsImageUrl();
        String newsHeadLine = currentItem.getNewsHeadLine();
        newsViewHolder.newsDescriptionTextView.setText(currentItem.getNewsDescription());
        newsViewHolder.newsTimeTextView.setText(currentItem.getNewsTime());
        newsViewHolder.newsSourceTextView.setText(currentItem.getNewsAuthor());
        newsViewHolder.newsTitleTextView.setText(newsHeadLine);
        //Picasso.get().load(newsImageUrl).centerCrop().fit().into(newsViewHolder.newsImageView);
        Glide.with(context)
                .asBitmap()
                .load(newsImageUrl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        newsViewHolder.progressBar.setVisibility(View.GONE);
                        newsViewHolder.newsImageView.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder{

        public ImageView newsImageView;
        public TextView newsTitleTextView;
        public ProgressBar progressBar;
        public TextView newsTimeTextView;
        public TextView newsSourceTextView;
        public TextView newsDescriptionTextView;

        public NewsViewHolder(@NonNull View itemView , final OnItemClickListener listener) {
            super(itemView);
            newsImageView = itemView.findViewById(R.id.news_image_view);
            newsTitleTextView = itemView.findViewById(R.id.news_title_text_view);
            progressBar = itemView.findViewById(R.id.news_progress_bar);
            newsTimeTextView = itemView.findViewById(R.id.news_time);
            newsSourceTextView = itemView.findViewById(R.id.news_source_text_view);
            newsDescriptionTextView = itemView.findViewById(R.id.news_description_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(listener != null){
                        int position = getAdapterPosition();
                        ViewCompat.setTransitionName(newsImageView,newsList.get(position).getNewsHeadLine());
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position , newsList.get(position),newsImageView);
                        }
                    }
                }
            });
        }
    }
}
