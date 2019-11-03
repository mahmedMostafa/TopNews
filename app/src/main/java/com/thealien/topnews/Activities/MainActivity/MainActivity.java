package com.thealien.topnews.Activities.MainActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.thealien.topnews.Activities.SearchActivity.SearchActivity;
import com.thealien.topnews.Activities.StartActivity;
import com.thealien.topnews.Adapters.SimpleFragmentPagerAdapter;
import com.thealien.topnews.Fragments.GeneralFragment.GeneralFragment;
import com.thealien.topnews.Fragments.HealthFragment.HealthFragment;
import com.thealien.topnews.Fragments.ScienceFragment.ScienceFragment;
import com.thealien.topnews.Fragments.SportsFragment.SportsFragment;
import com.thealien.topnews.R;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_SEARCH_WORD = "search";
    public static final String KEY_ARTICLE_TITLE = "article_title";
    public static final String KEY_ARTICLE_IMAGE = "article_image";
    public static final String KEY_ARTICLE_DESCRIPTION = "article_des";
    public static final String KEY_ARTICLE_AUTHOR = "article_author";
    public static final String KEY_ARTICLE_FULL_ARTICLE_URL = "article_url";
    private ImageView noInternetImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        noInternetImageView = findViewById(R.id.no_wifi);


        setupTabLayout();
    }


    //this method takes care of creating the view pager , tab layout , binding them together
    // ,setting up the icons & titles for the tab layout
    private void setupTabLayout(){
        //find the view pager
        ViewPager viewPager =findViewById(R.id.view_pager);
        //create an adapter that knows which fragment should show in each tab
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        //set the adapter to the view pager
        viewPager.setAdapter(adapter);
        setupViewPager(viewPager);
        //give the tab layout the view pager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.colorBlack));
        tabLayout.setupWithViewPager(viewPager);
        //and finally setup the icons
        //SimpleFragmentPagerAdapter.setupTabIcons(tabLayout,this);
    }

    private void setupViewPager(ViewPager viewPager) {
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new GeneralFragment(), "General");
        adapter.addFrag(new ScienceFragment(), "Science");
        adapter.addFrag(new SportsFragment(), "Sports");
        adapter.addFrag(new HealthFragment(), "Health");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //get the main menu we created by xml
        MenuInflater menuInflater = getMenuInflater();
        //put the menu into the tool bar we have
        menuInflater.inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_change_country){
            startActivity(new Intent(MainActivity.this, StartActivity.class));
            finish();
        }else if(id == R.id.action_search ) {
            //create the search view
            SearchView searchView = (SearchView) item.getActionView();
            //and now we can use the search view as an edit text
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                //this result value is what the user has typed in the edit text
                @Override
                public boolean onQueryTextSubmit(String result) {

                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra(KEY_SEARCH_WORD,result);
                    startActivity(intent);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

}
