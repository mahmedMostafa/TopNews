package com.thealien.topnews.Activities.SearchActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.thealien.topnews.Activities.details.DetailsActivity;
import com.thealien.topnews.Adapters.NewsAdapter;
import com.thealien.topnews.Models.Article;
import com.thealien.topnews.R;
import com.thealien.topnews.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;

import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_AUTHOR;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_DESCRIPTION;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_FULL_ARTICLE_URL;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_IMAGE;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_TITLE;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_SEARCH_WORD;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private NewsAdapter adapter;
    private ActivitySearchBinding binding;
    private SearchViewModel searchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_search);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        initRecyclerView();
        setUpToolBar();
        Intent intent = getIntent();
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.setSearchInput(intent.getStringExtra(KEY_SEARCH_WORD));
        searchViewModel.getData(this).observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                if (articles.size() > 0) {
                    binding.searchProgress.setVisibility(View.GONE);
                }
                setUpRecyclerView(articles);
            }
        });
        searchViewModel.searchInput.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //searchViewModel.setSearchInput(s);
                Log.d(TAG, "onChanged: the new search value is : " + s);
            }
        });
    }

    private void setUpToolBar() {
        Toolbar toolbar = findViewById(R.id.back_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle("Search");
    }

    private void initRecyclerView() {
        binding.searchRecyclerView.setHasFixedSize(true);
        binding.searchRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpRecyclerView(final List<Article> articles){
        //then we set up the adapter with our filled list and set it to the recycler view
        adapter = new NewsAdapter(SearchActivity.this, (ArrayList<Article>) articles);
        binding.searchRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position,Article article, ImageView imageView) {
                Intent intent = new Intent(SearchActivity.this, DetailsActivity.class);

                Article currentArticle = articles.get(position);

                intent.putExtra(KEY_ARTICLE_TITLE, currentArticle.getNewsHeadLine());
                intent.putExtra(KEY_ARTICLE_DESCRIPTION, currentArticle.getNewsDescription());
                intent.putExtra(KEY_ARTICLE_IMAGE, currentArticle.getNewsImageUrl());
                intent.putExtra(KEY_ARTICLE_AUTHOR, currentArticle.getNewsAuthor());
                intent.putExtra(KEY_ARTICLE_FULL_ARTICLE_URL, currentArticle.getFullArticleUrl());
                intent.putExtra("transition_name", ViewCompat.getTransitionName(imageView));

                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        SearchActivity.this, imageView, ViewCompat.getTransitionName(imageView)
                );

                startActivity(intent, optionsCompat.toBundle());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
