package io.kimo.tmdb.domain.service;


import io.kimo.tmdb.domain.entity.ShowDetailEntity;
import io.kimo.tmdb.domain.service.response.GetImageConfigurationResponse;
import io.kimo.tmdb.domain.service.response.GetShowImagesResponse;
import io.kimo.tmdb.domain.service.response.SearchShowResponse;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public class API {

    public static final String BASE_URL = "http://api.themoviedb.org/3";

    public interface ROUTES {

        //CONFIGURATIONS
        @GET("/configuration")
        void configurations(@Query("api_key") String apiKey, Callback<GetImageConfigurationResponse> callback);

        //SHOW SEARCH AUTOCOMPLETE
        @GET("/search/tv")
        void search(@Query("api_key") String apiKey, @Query("query") String query, Callback<SearchShowResponse> callback);

        //SHOW DETAIL
        @GET("/tv/{id}")
        void showDetails(@Query("api_key") String apiKey, @Path("id") int showID, Callback<ShowDetailEntity> callback);

        //SHOW IMAGES
        @GET("/tv/{id}/images")
        void showImages(@Query("api_key") String apiKey, @Path("id") int showID, Callback<GetShowImagesResponse> callback);

    }

    public static ROUTES http() {
        return new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build()
                .create(ROUTES.class);
    }

}
