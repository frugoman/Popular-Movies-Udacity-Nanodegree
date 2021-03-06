package com.frusoft.movier.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.frusoft.movier.R;
import com.frusoft.movier.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by nfrugoni on 8/10/17.
 */

@SuppressWarnings("DefaultFileTemplate")
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private List<Movie> moviesList;
    private final MoviesAdapterOnClickHandler onClickHandler;

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_preview, parent, false);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {
        holder.bind(holder.itemView.getContext(), moviesList.get(position));
    }

    @Override
    public int getItemCount() {
        if (moviesList == null) return 0;
        return moviesList.size();
    }

    public void setMoviesList(List<Movie> movies) {
        moviesList = movies;
        notifyDataSetChanged();
    }

    public interface MoviesAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MoviesAdapter(MoviesAdapterOnClickHandler onClickHandler) {
        this.onClickHandler = onClickHandler;
    }

    class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final ImageView mPosterImageView;
        final TextView mTitleTextView;
        final TextView mVoteAverageTextView;


        public MoviesAdapterViewHolder(View itemView) {
            super(itemView);
            mPosterImageView = itemView.findViewById(R.id.iv_preview_poster);
            mTitleTextView = itemView.findViewById(R.id.tv_movie_card_title);
            mVoteAverageTextView = itemView.findViewById(R.id.tv_movie_card_vote_average);
            itemView.setOnClickListener(this);
        }

        void bind(Context context, Movie movie) {
            if (movie != null) {
                mTitleTextView.setText(movie.getTitle());
                mVoteAverageTextView.setText(String.format(context.getString(R.string.vote_average_title), movie.getVoteAverage()));
                Picasso.with(context).load(movie.getPosterPathUrl()).placeholder(R.drawable.movie_poster_placeholder).error(R.drawable.movie_poster_error_placeholder).into(mPosterImageView);
            }
        }

        @Override
        public void onClick(View v) {
            int itemClickPosition = getAdapterPosition();
            Movie movie = moviesList.get(itemClickPosition);
            onClickHandler.onClick(movie);
        }
    }
}
