package io.kimo.tmdb.presentation.mvp.view;

import java.util.List;

import io.kimo.tmdb.presentation.mvp.model.ShowModel;

public interface ShowListView extends LoadDataView {

    void renderShows(List<ShowModel> shows);
    void clearShows();

}
