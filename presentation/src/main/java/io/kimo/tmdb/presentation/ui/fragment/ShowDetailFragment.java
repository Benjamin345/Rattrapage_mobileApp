package io.kimo.tmdb.presentation.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;

import io.kimo.tmdb.R;
import io.kimo.tmdb.presentation.TMDb;
import io.kimo.tmdb.presentation.Utils;
import io.kimo.tmdb.presentation.mapper.ShowMapper;
import io.kimo.tmdb.presentation.mvp.model.ShowModel;
import io.kimo.tmdb.presentation.mvp.presenter.ShowDetailPresenter;
import io.kimo.tmdb.presentation.mvp.view.ShowDetailView;
import io.kimo.tmdb.presentation.ui.BaseActivity;
import io.kimo.tmdb.presentation.ui.BaseFragment;
import io.kimo.tmdb.presentation.ui.activity.ShowImagesActivity;

public class ShowDetailFragment extends BaseFragment implements ShowDetailView {

    public static final String TAG = ShowDetailFragment.class.getSimpleName();
    public static final String SHOW_MODEL = TAG + ".SHOW_MODEL";

    private View mainView, loadingView, taglineContainer, overviewContainer, blackMask, galleryButton;

    private ImageView coverImage;
    private TextView title, year, homepage, companies, tagline, overview,number_of_seasons;

    private ShowModel showModel;

    private ShowDetailPresenter presenter;

    public static ShowDetailFragment newInstance(ShowModel showModel) {
        ShowDetailFragment fragment = new ShowDetailFragment();

        Bundle args = new Bundle();
        args.putString(SHOW_MODEL, new ShowMapper().serializeModel(showModel));

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle args = getArguments();

        if (args != null) {
            showModel = new ShowMapper().deserializeModel(args.getString(SHOW_MODEL));
        }

        super.onCreate(savedInstanceState);
    }

    @Override
    public void instantiatePresenter() {
        presenter = new ShowDetailPresenter(this, showModel);
    }

    @Override
    public void initializePresenter() {
        presenter.createView();
    }

    @Override
    public void finalizePresenter() {
        presenter.destroyView();
    }

    @Override
    public int getLayoutResource() {
        return R.layout.fragment_show_detail;
    }

    @Override
    public void mapGUI(View view) {
        mainView = view.findViewById(R.id.main_container);
        loadingView = view.findViewById(R.id.view_loading);

        title = (TextView) mainView.findViewById(R.id.title);
        coverImage = (ImageView) view.findViewById(R.id.cover);
        blackMask = view.findViewById(R.id.black_mask);
        homepage = (TextView) mainView.findViewById(R.id.homepage);
        companies = (TextView) mainView.findViewById(R.id.companies);
        taglineContainer = mainView.findViewById(R.id.tagline_container);
        tagline = (TextView) mainView.findViewById(R.id.tagline);
        overviewContainer = mainView.findViewById(R.id.overview_container);
        overview = (TextView) mainView.findViewById(R.id.overview);
        galleryButton = view.findViewById(R.id.fab);
        number_of_seasons = mainView.findViewById(R.id.seasons);
    }

    @Override
    public void configureGUI() {
        getActivity().setTitle(showModel.getName());
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onHomepageClicked();
            }
        });
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onGalleryClicked();
            }
        });

        Utils.setContentBehindToolbar((BaseActivity) getActivity());
        configureScrollFade();
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.onMainViewScrolled();
    }

    @Override
    public void updateBackground(String value) {
        TMDb.PICASSO.load(value).into(coverImage, new Callback() {
            @Override
            public void onSuccess() {
                blackMask.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {
            }
        });
    }

    @Override
    public void updateTitle(String value) {
        title.setText(value);
    }

    @Override
    public void updateSeasons(String value) {
        number_of_seasons.setText(value);
    }

    public void hideSeasons(){number_of_seasons.setVisibility((View.GONE));}

    @Override
    public void updateHomepage(String value) {
        homepage.setText(value);
        homepage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideHomepage() {
        homepage.setVisibility(View.GONE);
    }

    @Override
    public void updateCompanies(String value) {
        companies.setText(value);
        companies.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCompanies() {
        companies.setVisibility(View.GONE);
    }

    @Override
    public void updateTagline(String value) {
        tagline.setText(value);
        taglineContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTagline() {
        taglineContainer.setVisibility(View.GONE);
    }

    @Override
    public void updateOverview(String value) {
        overview.setText(value);
        overviewContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideOverview() {
        overviewContainer.setVisibility(View.GONE);
    }

    @Override
    public void openShowWebsite(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        browserIntent.setData(Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void openGallery() {
        startActivity(ShowImagesActivity.getCallingIntent(getActivity(), showModel.getId()));
    }

    @Override
    public void updateToolbarColor() {

        Toolbar toolbar = ((BaseActivity)getActivity()).getToolbar();

        int scrollY = mainView.getScrollY();
        ColorDrawable background = (ColorDrawable) toolbar.getBackground();
        int padding = mainView.getPaddingTop();
        double alpha = (1 - (((double) padding - (double) scrollY) / (double) padding)) * 255.0;
        alpha = alpha < 0 ? 0 : alpha;
        alpha = alpha > 255 ? 255 : alpha;

        background.setAlpha((int) alpha);

        float scrollRatio = (float) (alpha / 255f);
        int titleColor = Utils.getAlphaColor(Color.WHITE, scrollRatio);
        toolbar.setTitleTextColor(titleColor);
    }

    @Override
    public void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showRetry(String msg) {}

    @Override
    public void hideRetry() {}

    @Override
    public void showEmpty(String msg) {}

    @Override
    public void hideEmpty() {}

    @Override
    public void showView() {
        mainView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        mainView.setVisibility(View.GONE);
    }

    @Override
    public void showFeedback(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void destroyItself() {
        getActivity().finish();
    }

    private void configureScrollFade() {

        ((BaseActivity)getActivity()).setShouldFadeToolbar(true);

        presenter.onMainViewScrolled();

        mainView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                presenter.onMainViewScrolled();
            }
        });
    }
}
