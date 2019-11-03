package com.thealien.topnews.Fragments.GeneralFragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.thealien.topnews.databinding.FragmentGeneralBinding;

import java.util.ArrayList;
import java.util.List;

import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_AUTHOR;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_DESCRIPTION;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_FULL_ARTICLE_URL;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_IMAGE;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_TITLE;

public class GeneralFragment extends Fragment {

    private static final String TAG = "GeneralFragment";
    private NewsAdapter adapter;
    private GeneralViewModel generalViewModel;
    private FragmentGeneralBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_general, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_general,container,false);


        initUI();


        generalViewModel = ViewModelProviders.of(this).get(GeneralViewModel.class);

        generalViewModel.getData(getActivity()).observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable final List<Article> articles) {
                Log.d("size of the list : ", String.valueOf(articles.size()));
                if (articles.size() > 0) {
                    binding.generalProgress.setVisibility(View.GONE);
                }
                setUpRecyclerView(articles);
            }
        });
        return binding.getRoot();
    }

    private void initUI() {
        binding.generalRecyclerView.setHasFixedSize(true);
        binding.generalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void setUpRecyclerView(final List<Article> articles) {

        adapter = new NewsAdapter(getActivity(), (ArrayList<Article>) articles);
        binding.generalRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Article article, ImageView imageView) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);

                Article currentArticle = articles.get(position);

                intent.putExtra(KEY_ARTICLE_TITLE, currentArticle.getNewsHeadLine());
                intent.putExtra(KEY_ARTICLE_DESCRIPTION, currentArticle.getNewsDescription());
                intent.putExtra(KEY_ARTICLE_IMAGE, currentArticle.getNewsImageUrl());
                intent.putExtra(KEY_ARTICLE_AUTHOR, currentArticle.getNewsAuthor());
                intent.putExtra(KEY_ARTICLE_FULL_ARTICLE_URL, currentArticle.getFullArticleUrl());
                intent.putExtra("transition_name", ViewCompat.getTransitionName(imageView));

                //imageView animation
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), imageView, ViewCompat.getTransitionName(imageView)
                );

                startActivity(intent, optionsCompat.toBundle());
            }
        });
    }
}
