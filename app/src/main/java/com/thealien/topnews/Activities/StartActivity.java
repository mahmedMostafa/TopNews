package com.thealien.topnews.Activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thealien.topnews.Activities.MainActivity.MainActivity;
import com.thealien.topnews.Adapters.CountriesAdapter;
import com.thealien.topnews.Models.Country;
import com.thealien.topnews.Preferences.Preferences;
import com.thealien.topnews.R;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CountriesAdapter adapter;
    private ArrayList<Country> countriesList;
    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        preferences = Preferences.getInstance(this);
        //preferences.saveKey(Preferences.KEY_SELECTED_COUNTRY,"eg");
        /*SharedPreferences sharedPreferences = getSharedPreferences("this_one",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(COUNTRY_KEY,"eg");
        editor.apply();
        Log.d("-The selected country :",getSharedPreferences("this_one",Context.MODE_PRIVATE).getString(COUNTRY_KEY,"null"));*/
        //preferences.getInstance(this).saveKey(Preferences.KEY_FIRST_TIME_RUN, "yes");
        recyclerView = findViewById(R.id.countries_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        countriesList = new ArrayList<>();

        setupRecyclerView();


        adapter = new CountriesAdapter(countriesList, this);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new CountriesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                returnCountry(position);
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void setupRecyclerView() {
        countriesList.add(new Country(R.drawable.ic_egypt, "Egypt"));
        countriesList.add(new Country(R.drawable.ic_united_states_of_america, "United States"));
        countriesList.add(new Country(R.drawable.ic_germany, "Germany"));
        countriesList.add(new Country(R.drawable.ic_brazil, "Brazil"));
        countriesList.add(new Country(R.drawable.ic_denmark, "Denmark"));
        countriesList.add(new Country(R.drawable.ic_italy, "Italy"));
        countriesList.add(new Country(R.drawable.ic_japan, "Japan"));
        countriesList.add(new Country(R.drawable.ic_belgium, "Belgium"));
        countriesList.add(new Country(R.drawable.ic_france, "France"));
        countriesList.add(new Country(R.drawable.ic_united_kingdom, "United Kingdom"));
        countriesList.add(new Country(R.drawable.ic_norway, "Norway"));
    }

    private void returnCountry(int position){
        String key = Preferences.KEY_SELECTED_COUNTRY;
        switch (position) {
            case 0:
                preferences.saveKey(key, "eg");
                return;
            case 1:
                preferences.saveKey(key, "us");
                return;
            case 2:
                preferences.saveKey(key, "de");
                return;
            case 3:
                preferences.saveKey(key, "br");
                return;
            case 4:
                preferences.saveKey(key, "de");
                return;
            case 5:
                preferences.saveKey(key, "it");
                return;
            case 6:
                preferences.saveKey(key, "jp");
                return;
            case 7:
                preferences.saveKey(key, "fr");
                return;
            case 8:
                preferences.saveKey(key, "gb");
                return;
            case 9:
                preferences.saveKey(key, "no");
                return;
            default:
                preferences.saveKey(key, "eg");
        }

    }
}
