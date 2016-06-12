package com.sarathdr.movies.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.SearchView;

import com.sarathdr.movies.R;

/**
 * @author sdevadas on 11/06/16.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG_FRAGMENT_MOVIES = "fragment.movies";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager mgr = getFragmentManager();

        if (mgr.findFragmentByTag(TAG_FRAGMENT_MOVIES) == null) {
            final Fragment fragment = new MoviesListFragment();
            mgr.beginTransaction()
                    .add(R.id.fragment_container, fragment, TAG_FRAGMENT_MOVIES)
                    .commit();
        }
    }
}
