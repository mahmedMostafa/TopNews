package com.thealien.topnews.Fragments.SportsFragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.thealien.topnews.Activities.details.DetailsActivity;
import com.thealien.topnews.Adapters.NewsAdapter;
import com.thealien.topnews.Models.Article;
import com.thealien.topnews.R;
import com.thealien.topnews.databinding.FragmentSportsBinding;

import java.util.ArrayList;
import java.util.List;

import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_AUTHOR;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_DESCRIPTION;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_FULL_ARTICLE_URL;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_IMAGE;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_TITLE;

public class SportsFragment extends Fragment {

    private static final String TAG = "SportsFragment";

    private FragmentSportsBinding binding;
    private NewsAdapter adapter;
    private SportsViewModel sportsViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sports,container,false);
        initRecyclerView();
        sportsViewModel = ViewModelProviders.of(this).get(SportsViewModel.class);
        sportsViewModel.getDate(getActivity()).observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles.size() > 0) {
                    binding.sportsProgress.setVisibility(View.GONE);
                }
                setUpRecyclerView(articles);
            }
        });

        return binding.getRoot();
    }

    private void setUpRecyclerView(final List<Article> articles) {
        //then we set up the adapter with our filled list and set it to the recycler view
        adapter = new NewsAdapter(getActivity(), (ArrayList<Article>) articles);
        binding.sportsRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Article article, ImageView imageView) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);

                Article currentArticle = articles.get(position);

                intent.putExtra(KEY_ARTICLE_TITLE,currentArticle.getNewsHeadLine());
                intent.putExtra(KEY_ARTICLE_DESCRIPTION,currentArticle.getNewsDescription());
                intent.putExtra(KEY_ARTICLE_IMAGE,currentArticle.getNewsImageUrl());
                intent.putExtra(KEY_ARTICLE_AUTHOR,currentArticle.getNewsAuthor());
                intent.putExtra(KEY_ARTICLE_FULL_ARTICLE_URL,currentArticle.getFullArticleUrl());
                intent.putExtra("transition_name", ViewCompat.getTransitionName(imageView));

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),imageView,ViewCompat.getTransitionName(imageView)
                );

                startActivity(intent,optionsCompat.toBundle());
            }
        });
    }

    private void initRecyclerView(){
        binding.sportsRecyclerView.setHasFixedSize(true);
        binding.sportsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
