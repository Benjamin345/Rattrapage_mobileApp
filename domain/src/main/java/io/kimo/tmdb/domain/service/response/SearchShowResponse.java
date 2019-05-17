package io.kimo.tmdb.domain.service.response;

import android.util.Log;

import java.util.List;

import io.kimo.tmdb.domain.entity.ShowEntity;

public class SearchShowResponse {
    private List<ShowEntity> results;

    public List<ShowEntity> getResults() {
        System.out.print("ouiiiiiiiiiii");
        return results;
    }

    public void setResults(List<ShowEntity> results) {
        this.results = results;
    }
}
