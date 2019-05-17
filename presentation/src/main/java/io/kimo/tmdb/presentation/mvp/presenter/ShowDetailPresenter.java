package io.kimo.tmdb.presentation.mvp.presenter;

import android.text.TextUtils;

import io.kimo.tmdb.BuildConfig;
import io.kimo.tmdb.domain.entity.ShowDetailEntity;
import io.kimo.tmdb.domain.usecase.GetShowDetailUseCase;
import io.kimo.tmdb.presentation.TMDb;
import io.kimo.tmdb.presentation.mapper.ShowMapper;
import io.kimo.tmdb.presentation.mvp.BasePresenter;
import io.kimo.tmdb.presentation.mvp.model.ShowModel;
import io.kimo.tmdb.presentation.mvp.view.ShowDetailView;

public class ShowDetailPresenter implements BasePresenter {

    private ShowDetailView view;

    private ShowModel showModel;
    private String apiKey;

    public ShowDetailPresenter(ShowDetailView view, ShowModel showModel) {
        this.view = view;
        this.showModel = showModel;
        this.apiKey = BuildConfig.TMDB_API_KEY;
    }

    @Override
    public void createView() {
        hideAllViews();
        view.showLoading();

        downloadShowDetails();
    }

    @Override
    public void destroyView() {}

    public void onHomepageClicked() {
        if(!TextUtils.isEmpty(showModel.getHomepage())) {
            view.openShowWebsite(showModel.getHomepage());
        }
    }

    public void onGalleryClicked() {
        view.openGallery();
    }

    public void onMainViewScrolled() {
        view.updateToolbarColor();
    }

    private void hideAllViews() {
        view.hideView();
        view.hideLoading();
        view.hideRetry();
        view.hideEmpty();
    }

    private void downloadShowDetails() {
        TMDb.JOB_MANAGER.addJobInBackground(new GetShowDetailUseCase(apiKey, showModel.getId(), new GetShowDetailUseCase.GetShowDetailUseCaseCallback() {
            @Override
            public void onShowDetailLoaded(ShowDetailEntity showDetailEntity) {
                updateShowModel(showDetailEntity);

                view.updateBackground(showModel.getBigCover());
                view.updateTitle(showModel.getName());

                if (TextUtils.isEmpty(showModel.getSeasons())) {
                    view.hideSeasons();
                } else {
                    view.updateSeasons(showModel.getSeasons());
                }

                if (TextUtils.isEmpty(showModel.getHomepage())) {
                    view.hideHomepage();
                } else {
                    view.updateHomepage(showModel.getHomepage());
                }

                if (showModel.getCompanies().isEmpty()) {
                    view.hideCompanies();
                } else {
                    view.updateCompanies(showModel.getCompanies());
                }

                if (TextUtils.isEmpty(showModel.getTagline())) {
                    view.hideTagline();
                } else {
                    view.updateTagline(showModel.getTagline());
                }

                if (TextUtils.isEmpty(showModel.getOverview())) {
                    view.hideOverview();
                } else {
                    view.updateOverview(showModel.getOverview());
                }

                view.hideLoading();
                view.showView();
            }

            @Override
            public void onError(String reason) {
                view.showFeedback(reason);
                view.destroyItself();
            }
        }));
    }

    private void updateShowModel(ShowDetailEntity detailEntity) {
        showModel = new ShowMapper().addDetails(showModel, detailEntity);
    }
}
