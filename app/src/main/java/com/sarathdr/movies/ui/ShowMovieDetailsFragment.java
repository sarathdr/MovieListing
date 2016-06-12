package com.sarathdr.movies.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.sarathdr.movies.BuildConfig;
import com.sarathdr.movies.R;
import com.sarathdr.movies.model.Movie;
import com.sarathdr.movies.network.GsonRequest;

/**
 * * @author sdevadas on 11/06/16.
 */
public class ShowMovieDetailsFragment extends Fragment {

    private static final String KEY_MOVIE = "movieDetails";
    private static final String ARG_MOVIE_ID = "movieId";
    private static final String TAG = "shoMovie";


    private Movie mMovie;
    private TextView mTitleText;
    private TextView mDirectorText;
    private TextView mActorText;
    private NetworkImageView mMoviePoster;

    public ShowMovieDetailsFragment() {
    }

    public static ShowMovieDetailsFragment getInstance(final String movieId) {
        final Bundle args = new Bundle();
        args.putString(ARG_MOVIE_ID, movieId);
        final ShowMovieDetailsFragment instance = new ShowMovieDetailsFragment();
        instance.setArguments(args);
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            final String jsonMovie = savedInstanceState.getString(KEY_MOVIE);
            if (!TextUtils.isEmpty(jsonMovie)) {
                mMovie = MoviesApplication.getInstance()
                        .getGson().fromJson(jsonMovie, Movie.class);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mMovie != null) {
            outState.putString(KEY_MOVIE, MoviesApplication.getInstance()
                    .getGson().toJson(mMovie));
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mMovie == null) {
            loadContents();
        } else {
            onDataLoaded(mMovie);
        }
    }

    public void loadContents() {
        ServerResponseListener responseListener = new ServerResponseListener();
        final String movieId = getArguments().getString(ARG_MOVIE_ID);
        final Request<Movie> request = new GsonRequest<Movie>(UrlBuilder.getMovieById(movieId),
                Movie.class, responseListener, responseListener);
        MoviesApplication.getInstance().addRequestToQueue(request);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_show_movie, container, false);

        mTitleText = (TextView) rootView.findViewById(R.id.title);
        mDirectorText = (TextView) rootView.findViewById(R.id.director);
        mActorText = (TextView) rootView.findViewById(R.id.actors);
        mMoviePoster = (NetworkImageView) rootView.findViewById(R.id.poster);

        return rootView;
    }

    public void onDataLoaded(Movie movie) {
        mMovie = movie;

        if (isAdded()) {
            mTitleText.setText(mMovie.getTitle());
            mDirectorText.setText(getString(R.string.movie_director) + mMovie.getDirector());
            mActorText.setText(getString(R.string.movie_actors) + mMovie.getActors());
            mMoviePoster.setImageUrl(mMovie.getPoster(),
                    MoviesApplication.getInstance().getImageLoader());
        }
    }

    private class ServerResponseListener implements Response.Listener<Movie>, Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            // Handle error here
            Log.e(TAG, "onErrorResponse " + error);
        }

        @Override
        public void onResponse(Movie response) {

            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Request finished");
            }

            onDataLoaded(response);
        }
    }
}
