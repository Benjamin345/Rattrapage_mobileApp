package io.kimo.tmdb.domain.usecase;

import java.util.List;

import io.kimo.tmdb.domain.BaseUseCase;
import io.kimo.tmdb.domain.BaseUseCaseCallback;
import io.kimo.tmdb.domain.entity.ImageEntity;
import io.kimo.tmdb.domain.service.API;
import io.kimo.tmdb.domain.service.response.GetShowImagesResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class  GetShowImagesUseCase extends BaseUseCase{

    public interface GetShowImagesUseCaseCallback extends BaseUseCaseCallback {
        void onImagesUrlsLoaded(List<ImageEntity> backdrops, List<ImageEntity> posters);
    }

    private String apiKey;
    private int showID;

    public GetShowImagesUseCase(String apiKey, int showID, GetShowImagesUseCaseCallback callback) {
        super(callback);
        this.showID = showID;
        this.apiKey = apiKey;
    }

    @Override
    public void onRun() throws Throwable {
        API.http().showImages(apiKey, showID, new Callback<GetShowImagesResponse>() {
            @Override
            public void success(GetShowImagesResponse getShowImagesResponse, Response response) {
                ((GetShowImagesUseCaseCallback)callback).onImagesUrlsLoaded(getShowImagesResponse.getBackdrops(), getShowImagesResponse.getPosters());
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
