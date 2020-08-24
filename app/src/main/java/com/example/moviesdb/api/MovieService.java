package com.example.moviesdb.api;

import com.example.moviesdb.models.TopRatedMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MovieService {

    @GET("movie/top_rated")
    Call<TopRatedMovies> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("search/movie")
    Call<TopRatedMovies> getSearchMovies(
            @Query("api_key") String apiKey,
            @Query("query") String query
    );

}
