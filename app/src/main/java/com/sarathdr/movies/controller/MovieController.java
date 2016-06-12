package com.sarathdr.movies.controller;

import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.sarathdr.movies.R;
import com.sarathdr.movies.model.Movie;
import com.sarathdr.movies.ui.MoviesApplication;

/**
 * @author sdevadas on 11/06/16.
 */
public class MovieController {

    private final TextView mTitleView;
    private final NetworkImageView mImage;

    public MovieController(View view) {
        mTitleView = (TextView) view.findViewById(R.id.movie_title);
        mImage = (NetworkImageView) view.findViewById(R.id.movie_poster);
    }

    public void setMovie(final Movie movie) {
        mTitleView.setText(movie.getTitle());

        ImageLoader imageLoader = MoviesApplication.getInstance().getImageLoader();
        mImage.setImageUrl(movie.getPoster(), imageLoader);
    }

}
