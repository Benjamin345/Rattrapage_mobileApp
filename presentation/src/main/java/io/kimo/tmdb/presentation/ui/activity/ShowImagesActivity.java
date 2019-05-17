package io.kimo.tmdb.presentation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import io.kimo.tmdb.R;
import io.kimo.tmdb.presentation.ui.BaseActivity;
import io.kimo.tmdb.presentation.ui.fragment.ShowImagesFragment;

public class ShowImagesActivity extends BaseActivity {

    public static final String TAG = ShowImagesActivity.class.getSimpleName();
    public static final String SHOW_ID = TAG + ".SHOW_ID";

    private int showID;

    public static Intent getCallingIntent(Context context, int showID) {
        Intent intentToBeCalled = new Intent(context, ShowImagesActivity.class);
        intentToBeCalled.putExtra(SHOW_ID, showID);

        return intentToBeCalled;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle args = getIntent().getExtras();

        if(args != null) {
            showID = args.getInt(SHOW_ID);
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResource() {
        return R.layout.activity_with_toolbar;
    }

    @Override
    public Fragment getMainFragment() {
        return ShowImagesFragment.newInstance(showID);
    }
}
