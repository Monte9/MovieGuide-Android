package com.montethakkar.movieguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.R.attr.apiKey;
import static android.R.attr.id;
import static android.R.attr.key;

public class MainActivity extends AppCompatActivity {

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private MovieAdapter moviesAdapter;
    private ArrayList<Movie> movies;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Construct the data source

        movies = new ArrayList<Movie>();
        // Create the adapter to convert the array to views
        moviesAdapter = new MovieAdapter(this, movies);
        // Attach the adapter to a ListView
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(moviesAdapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(
                "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
                new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        System.out.println(responseString);

                        if (responseString != null) {
                            try {
                                JSONObject jsonObj = new JSONObject(responseString);

                                // Getting JSON Array node
                                JSONArray moviesJSON = jsonObj.getJSONArray("results");

                                // looping through All Contacts
                                for (int i = 0; i < moviesJSON.length(); i++) {
                                    JSONObject movieJSON = moviesJSON.getJSONObject(i);

                                    Gson gson = new GsonBuilder().create();
                                    Movie movie = gson.fromJson(movieJSON.toString(), Movie.class);

                                    movies.add(movie);

                                    moviesAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("ServiceHandler", "Couldn't get any data from the url");
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        System.out.println(responseString);
                    }
                });
    }

}
