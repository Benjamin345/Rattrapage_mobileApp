package io.kimo.tmdb.domain.usecase;


import java.util.List;

import io.kimo.tmdb.domain.BaseUseCase;
import io.kimo.tmdb.domain.BaseUseCaseCallback;
import io.kimo.tmdb.domain.entity.ShowEntity;
import io.kimo.tmdb.domain.service.API;
import io.kimo.tmdb.domain.service.response.SearchShowResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchShowUseCase extends BaseUseCase {

    public interface SearchShowUseCaseCallback extends BaseUseCaseCallback {
        void onShowsSearched(List<ShowEntity> showEntities);
    }

    private String apiKey;
    private String query;

    public SearchShowUseCase(String apiKey, String query, SearchShowUseCaseCallback callback) {
        super(callback);
        this.apiKey = apiKey;
        this.query = query;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().search(apiKey, query, new Callback<SearchShowResponse>() {
            @Override
            public void success(SearchShowResponse searchShowResponse, Response response) {
                ((SearchShowUseCaseCallback) callback).onShowsSearched(searchShowResponse.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getKind() == RetrofitError.Kind.NETWORK) {
                    errorReason = NETWORK_ERROR;
                } else {
                    errorReason = error.getResponse().getReason();
                }
                onCancel();
            }
        });
    }
}
