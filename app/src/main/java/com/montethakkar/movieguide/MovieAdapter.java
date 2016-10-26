package com.montethakkar.movieguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by montewithpillow on 10/25/16.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Movie movie = getItem(position);

        String baseImageURL = "http://image.tmdb.org/t/p/w500";

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_row, parent, false);
        }

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
        ImageView imagePoster = (ImageView) convertView.findViewById(R.id.posterImage);
        ImageView imageBackdrop = (ImageView) convertView.findViewById(R.id.backdropImage);

        // Populate the data into the template view using the data object
        tvName.setText(movie.title);
        tvOverview.setText(movie.overview);
        Picasso.with(this.getContext()).load(baseImageURL + movie.backdrop_path).into(imageBackdrop);
        Picasso.with(this.getContext()).load(baseImageURL + movie.poster_path).into(imagePoster);

        // Return the completed view to render on screen
        return convertView;
    }
}
