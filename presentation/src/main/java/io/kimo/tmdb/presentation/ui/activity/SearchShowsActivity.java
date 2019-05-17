package io.kimo.tmdb.presentation.ui.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import io.kimo.tmdb.R;
import io.kimo.tmdb.presentation.ui.BaseActivity;
import io.kimo.tmdb.presentation.ui.fragment.SearchShowsFragment;

public class SearchShowsActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_with_toolbar;
    }

    @Override
    public Fragment getMainFragment() {
        return SearchShowsFragment.newInstance();
    }
}
