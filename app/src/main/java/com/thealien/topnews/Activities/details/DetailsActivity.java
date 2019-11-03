package com.thealien.topnews.Activities.details;

import android.content.Intent;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thealien.topnews.R;
import com.thealien.topnews.databinding.ActivityDetailsBinding;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_AUTHOR;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_DESCRIPTION;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_FULL_ARTICLE_URL;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_IMAGE;
import static com.thealien.topnews.Activities.MainActivity.MainActivity.KEY_ARTICLE_TITLE;

public class DetailsActivity extends AppCompatActivity {

    public static final String KEY_FULL_ARTICLE = "full_article";

    private ActivityDetailsBinding binding;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_details);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_details);
        initToolBar();
        intent = getIntent();
        makeImageAnimation();
        setDetailsInfo();

        binding.fullArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open a web page in the browser with the link we get from the intent
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(intent.getStringExtra(KEY_ARTICLE_FULL_ARTICLE_URL)));
                startActivity(intent);
            }
        });

    }

    private void setDetailsInfo(){
        binding.detailDescriptionTextView.setText(intent.getStringExtra(KEY_ARTICLE_DESCRIPTION));
        binding.detailTitleTextView.setText(intent.getStringExtra(KEY_ARTICLE_TITLE));
        binding.detailAuthorTextView.setText(intent.getStringExtra(KEY_ARTICLE_AUTHOR));
    }

    private void makeImageAnimation(){

        supportPostponeEnterTransition();

        String transitionName = intent.getStringExtra("transition_name");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.detailImageView.setTransitionName(transitionName);
        }
        Picasso.get().load(intent.getStringExtra(KEY_ARTICLE_IMAGE))
                .fit().centerCrop()
                .into(binding.detailImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        supportStartPostponedEnterTransition();
                    }
                });
    }

    private void initToolBar(){
        //set the tool bar we made to this activity
        setSupportActionBar(binding.detailToolBar);
        //this makes the back button itself
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //put the icon and the title for the activity
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setTitle("Article Details");
    }

    //this controls what happens when you click every item in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.home){
            //this gets us back to the main activity
            //made by android itself
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
