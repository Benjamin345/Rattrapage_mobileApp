package io.kimo.tmdb.presentation.mvp.view;

import java.util.List;

import io.kimo.tmdb.presentation.mvp.model.ShowModel;

public interface SearchShowsView extends LoadDataView {

    void renderShowsList(List<ShowModel> shows);
    void removeShowsList();

    void cleanTimer();
}
