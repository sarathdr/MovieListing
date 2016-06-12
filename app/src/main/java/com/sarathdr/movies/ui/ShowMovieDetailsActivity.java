package com.sarathdr.movies.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sarathdr.movies.R;

public class ShowMovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extraMovieId";
    private static final String TAG_FRAGMENT_MOVIES = "fragment.showMovie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager mgr = getFragmentManager();

        if (mgr.findFragmentByTag(TAG_FRAGMENT_MOVIES) == null) {
            final Fragment fragment = ShowMovieDetailsFragment
                    .getInstance(getIntent().getStringExtra(EXTRA_MOVIE_ID));

            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, TAG_FRAGMENT_MOVIES)
                    .commit();
        }
    }
}
