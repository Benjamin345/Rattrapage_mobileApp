package io.kimo.tmdb.presentation.mvp.presenter;

import java.util.List;

import io.kimo.tmdb.BuildConfig;
import io.kimo.tmdb.domain.entity.ImageEntity;
import io.kimo.tmdb.domain.usecase.GetShowImagesUseCase;
import io.kimo.tmdb.presentation.TMDb;
import io.kimo.tmdb.presentation.mapper.ImageMapper;
import io.kimo.tmdb.presentation.mvp.BasePresenter;
import io.kimo.tmdb.presentation.mvp.view.ShowImagesView;

public class ShowImagesPresenter implements BasePresenter {

    private ShowImagesView view;
    private int showId;

    public ShowImagesPresenter(ShowImagesView view, int showId) {
        this.showId = showId;
        this.view = view;
    }

    @Override
    public void createView() {
        hideAllViews();

        view.showLoading();

        downloadShowImages();
    }

    @Override
    public void destroyView() {}

    private void hideAllViews() {
        view.hideView();
        view.hideEmpty();
        view.hideLoading();
        view.hideRetry();
    }

    private void downloadShowImages() {
        TMDb.JOB_MANAGER.addJobInBackground(new GetShowImagesUseCase(BuildConfig.TMDB_API_KEY, showId, new GetShowImagesUseCase.GetShowImagesUseCaseCallback() {
            @Override
            public void onImagesUrlsLoaded(List<ImageEntity> backdrops, List<ImageEntity> posters) {
                view.renderTabs(new ImageMapper("w780").toModels(backdrops), new ImageMapper("w500").toModels(posters));
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
}
