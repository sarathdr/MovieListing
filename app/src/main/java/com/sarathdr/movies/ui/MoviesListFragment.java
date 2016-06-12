package com.sarathdr.movies.ui;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.google.gson.reflect.TypeToken;
import com.sarathdr.movies.BuildConfig;
import com.sarathdr.movies.R;
import com.sarathdr.movies.adapter.MovieListAdapter;
import com.sarathdr.movies.model.Movie;
import com.sarathdr.movies.model.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author sdevadas on 11/06/16.
 */
public class MoviesListFragment extends Fragment
        implements AdapterView.OnItemClickListener, SearchView.OnQueryTextListener {

    private Movies mMovies;
    private MovieListAdapter mAdapter;

    private static final String TAG = "MovieList";
    private static final String SEARCH_RESULT_NODE = "Search";

    private static final String KEY_MOVIES_LIST = "moviesList";

    public MoviesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            final String jsonCities = savedInstanceState.getString(KEY_MOVIES_LIST);
            if (!TextUtils.isEmpty(jsonCities)) {
                mMovies = MoviesApplication.getInstance()
                        .getGson().fromJson(jsonCities, Movies.class);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mMovies != null) {
            onDataLoaded(mMovies);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mMovies != null) {
            outState.putString(KEY_MOVIES_LIST,
                    MoviesApplication.getInstance().getGson().toJson(mMovies));
        }
        super.onSaveInstanceState(outState);
    }

    public void loadContents(final String url) {
        final ServerResponseListener responseListener = new ServerResponseListener();
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, responseListener, responseListener);
        MoviesApplication.getInstance().addRequestToQueue(request);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        mAdapter = new MovieListAdapter(getActivity(), R.layout.list_item_movie);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);
        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(this);

    }

    public void onDataLoaded(Movies movies) {
        mMovies = movies;
        mAdapter.setList(mMovies.getMovies());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Object item = parent.getItemAtPosition(position);
        if (item instanceof Movie) {
            final Movie movie = (Movie) item;
            Intent i = new Intent(getActivity(), ShowMovieDetailsActivity.class);
            i.putExtra(ShowMovieDetailsActivity.EXTRA_MOVIE_ID,
                    movie.getId());
            startActivity(i);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!TextUtils.isEmpty(newText) && newText.length() > 2) {

            final String url = UrlBuilder.getSearch(newText, 1);
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Search API url: " + url);
            }

            loadContents(url);
        }
        return true;
    }

    private class ServerResponseListener implements Response.Listener<JSONObject>, Response.ErrorListener {

        @Override
        public void onErrorResponse(VolleyError error) {
            // Handle error here
            Log.e(TAG, "onErrorResponse " + error);
        }

        @Override
        public void onResponse(JSONObject response) {

            if (BuildConfig.DEBUG) {
                Log.d(TAG, "Response " + response.toString());
            }

            // Make sure that the response has results
            // else ignore the results
            if (response.has(SEARCH_RESULT_NODE)) {
                JSONArray jsonArray = null;
                try {
                    jsonArray = (JSONArray) response.get("Search");
                    Type listType = new TypeToken<List<Movie>>() {
                    }.getType();

                    List<Movie> myModelList = MoviesApplication.getInstance().getGson()
                            .fromJson(jsonArray.toString(), listType);
                    onDataLoaded(new Movies(myModelList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
