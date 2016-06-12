package com.sarathdr.movies.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.sarathdr.movies.controller.MovieController;
import com.sarathdr.movies.model.Movie;

/**
 * @author sdevadas on 11/06/16.
 */
public class MovieListAdapter extends GenericListAdapter<Movie> {
    /**
     * Creates a new list adapter.
     *
     * @param context    context
     * @param resourceId resource ID of the layout used for each list element
     */
    public MovieListAdapter(Context context, @LayoutRes int resourceId) {
        super(context, resourceId);
    }

    @Override
    protected void bindView(View view, ViewGroup parent, int position, final Movie movie) {
        final MovieController controller = (MovieController) view.getTag();
        controller.setMovie(movie);
    }

    /** {@inheritDoc} */
    @Override
    protected void setUpNewView(final View view, final ViewGroup parent) {
        MovieController ctc = new MovieController(view);
        view.setTag(ctc);
    }


}
