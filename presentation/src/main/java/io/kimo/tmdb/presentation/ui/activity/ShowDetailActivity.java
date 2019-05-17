package io.kimo.tmdb.presentation.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import io.kimo.tmdb.R;
import io.kimo.tmdb.presentation.mapper.ShowMapper;
import io.kimo.tmdb.presentation.mvp.model.ShowModel;
import io.kimo.tmdb.presentation.ui.BaseActivity;
import io.kimo.tmdb.presentation.ui.fragment.ShowDetailFragment;

public class ShowDetailActivity extends BaseActivity {

    public static final String TAG = ShowDetailActivity.class.getSimpleName();
    public static final String SHOW_MODEL = TAG + ".SHOW_MODEL";

    private ShowModel showModel;

    public static Intent getCallingIntent(Context context, ShowModel showModel) {
        Intent intentToBeCalled = new Intent(context, ShowDetailActivity.class);
        intentToBeCalled.putExtra(SHOW_MODEL, new ShowMapper().serializeModel(showModel));

        return intentToBeCalled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle args = getIntent().getExtras();

        if(args != null) {
            showModel = new ShowMapper().deserializeModel(args.getString(SHOW_MODEL));
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_with_toolbar;
    }

    @Override
    public Fragment getMainFragment() {
        return ShowDetailFragment.newInstance(showModel);
    }
}
