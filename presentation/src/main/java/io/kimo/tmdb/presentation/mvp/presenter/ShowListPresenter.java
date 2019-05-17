package io.kimo.tmdb.presentation.mvp.presenter;


import java.util.List;

import io.kimo.tmdb.presentation.mvp.BasePresenter;
import io.kimo.tmdb.presentation.mvp.model.ShowModel;
import io.kimo.tmdb.presentation.mvp.view.ShowListView;

public class ShowListPresenter implements BasePresenter {

    private ShowListView view;
    private List<ShowModel> shows;

    public ShowListPresenter(ShowListView view, List<ShowModel> shows) {
        this.view = view;
        this.shows = shows;
    }

    @Override
    public void createView() {

        hideAllViews();

        view.showLoading();

        if(shows.isEmpty()) {
            view.showEmpty("There are no TV shows to show");
            view.hideLoading();
        } else {
            view.renderShows(shows);
            view.hideLoading();
            view.showView();
        }
    }

    @Override
    public void destroyView() {}

    private void hideAllViews() {
        view.hideView();
        view.hideEmpty();
        view.hideLoading();
        view.hideRetry();
    }
}
