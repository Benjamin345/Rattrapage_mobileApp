package io.kimo.tmdb.presentation.mvp.view;

public interface ShowDetailView extends LoadDataView {

    void updateBackground(String value);

    void updateTitle(String value);

    void updateSeasons(String value);
    void hideSeasons();

    void updateHomepage(String value);
    void hideHomepage();

    void updateCompanies(String value);
    void hideCompanies();

    void updateTagline(String value);
    void hideTagline();

    void updateOverview(String value);
    void hideOverview();

    void openShowWebsite(String url);
    void openGallery();

    void updateToolbarColor();
}
