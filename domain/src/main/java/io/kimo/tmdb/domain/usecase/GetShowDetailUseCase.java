package io.kimo.tmdb.domain.usecase;

import io.kimo.tmdb.domain.BaseUseCase;
import io.kimo.tmdb.domain.BaseUseCaseCallback;
import io.kimo.tmdb.domain.entity.ShowDetailEntity;
import io.kimo.tmdb.domain.service.API;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GetShowDetailUseCase extends BaseUseCase {

    public interface GetShowDetailUseCaseCallback extends BaseUseCaseCallback {
        void onShowDetailLoaded(ShowDetailEntity showDetailEntity);
    }

    private int showId;
    private String apiKey;

    public GetShowDetailUseCase(String apiKey, int showId, GetShowDetailUseCaseCallback callback) {
        super(callback);
        this.showId = showId;
        this.apiKey = apiKey;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().showDetails(apiKey, showId, new Callback<ShowDetailEntity>() {
            @Override
            public void success(ShowDetailEntity showDetailEntity, Response response) {
                ((GetShowDetailUseCaseCallback)callback).onShowDetailLoaded(showDetailEntity);
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
