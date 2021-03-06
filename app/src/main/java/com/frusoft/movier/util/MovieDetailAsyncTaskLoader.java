package com.frusoft.movier.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;

import com.frusoft.movier.model.Movie;
import com.frusoft.movier.model.MovieReview;
import com.frusoft.movier.model.MovieVideo;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;


/**
 * Created by nfrugoni on 9/10/17.
 */

public class MovieDetailAsyncTaskLoader extends AsyncTaskLoader<Movie> {

    private MovieDetailsLoaderHandler loaderHandler;

    public static final String EXTRA_KEY_MOVIE_ID = "movieID";

    private Bundle aBundle;

    public MovieDetailAsyncTaskLoader(Context context, Bundle args, MovieDetailsLoaderHandler callbackHandler) {
        super(context);
        aBundle = args;
        loaderHandler = callbackHandler;
    }

    public interface MovieDetailsLoaderHandler{
        void onIsMovieSavedInFavorites(boolean isInFavorites);
    }

    @Override
    public Movie loadInBackground() {
        Movie movie = null;
        try {
            int id = aBundle.getInt(EXTRA_KEY_MOVIE_ID, -1);
            if (id == -1) return null;
            movie = MoviesNetworkUtils.getMovieWithId(id);
            List<MovieReview> movieReviewFormMovie = MoviesNetworkUtils.getMovieReviewFormMovie(movie);
            movie.setMovieReviews(movieReviewFormMovie);
            List<MovieVideo> movieVideosFormMovie = MoviesNetworkUtils.getMovieVideosFormMovie(movie);
            movie.setMovieVideos(movieVideosFormMovie);

            loaderHandler.onIsMovieSavedInFavorites(MoviesDBUtils.isMovieInFavorites(movie, getContext()));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return movie;
    }


}
