package com.example.mini_project2.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mini_project2.database.entity.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    void insert(Movie movie);

    @Query("SELECT * FROM movies")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM movies WHERE movieId = :movieId")
    Movie getMovieById(int movieId);

    @Query("SELECT * FROM movies WHERE genre = :genre")
    List<Movie> getMoviesByGenre(String genre);

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :keyword || '%'")
    List<Movie> searchMovies(String keyword);
}
