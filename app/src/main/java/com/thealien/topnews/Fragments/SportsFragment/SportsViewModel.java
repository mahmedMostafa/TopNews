package com.thealien.topnews.Fragments.SportsFragment;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.thealien.topnews.Models.Article;
import com.thealien.topnews.Preferences.Preferences;
import com.thealien.topnews.Utilities.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.thealien.topnews.Utilities.Urls.SPORTS_NEWS_URL;

public class SportsViewModel extends ViewModel {

    private static final String TAG = "SportsViewModel";

    private Preferences preferences;
    private String imageURl, headLine,description, fullArticleLink, time;
    private RequestQueue mQueue;
    private List<Article> articleList = new ArrayList<>();
    private MutableLiveData<List<Article>> newsList;

    public LiveData<List<Article>> getDate(Context context){
        if(newsList == null){
            newsList = new MutableLiveData<>();
            loadData(context);
        }
        return newsList;
    }

    private void loadData(Context context) {
        mQueue = VolleySingleton.getInstance(context).getRequestQueue();
        preferences = Preferences.getInstance(context);

        String selectedCountry = preferences.getKey(Preferences.KEY_SELECTED_COUNTRY);
        Log.d("the selected country",selectedCountry);
        Uri baseUri = Uri.parse(SPORTS_NEWS_URL);
        Uri.Builder builder = baseUri.buildUpon();

        builder.appendQueryParameter("country",selectedCountry);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, builder.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //get the array called articles
                            JSONArray articles = response.getJSONArray("articles");
                            //iterate in every object inside the array
                            for(int i=0;i<articles.length();i++){
                                JSONObject current = articles.getJSONObject(i);
                                JSONObject source = current.getJSONObject("source");
                                String newsSource = source.getString("name");
                                //get the image url and th head line for the corresponding
                                imageURl = current.getString("urlToImage");
                                headLine = current.getString("title");
                                description = current.getString("description");
                                fullArticleLink = current.getString("url");
                                time = current.getString("publishedAt");
                                //and finally add it to the list as an Article item
                                String newTime = time.substring(0,Math.min(time.length(),10));
                                articleList.add(new Article(newTime,imageURl,headLine,description,newsSource,fullArticleLink));
                            }
                            newsList.setValue(articleList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //just print the error and notify the user for some technical problems
                        error.printStackTrace();
                        //Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

        mQueue.add(request);
    }
}
