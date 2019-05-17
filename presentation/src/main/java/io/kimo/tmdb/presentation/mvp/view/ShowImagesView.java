package io.kimo.tmdb.presentation.mvp.view;

import java.util.List;

import io.kimo.tmdb.presentation.mvp.model.ImageModel;

public interface ShowImagesView extends LoadDataView {
    void renderTabs(List<ImageModel> backdrops, List<ImageModel> posters);
}
