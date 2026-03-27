<<<<<<< HEAD
package com.example.mini_project2.database.dao;
=======
package com.example.movie_ticket_app.database.dao;
>>>>>>> c1bdf99 (add ticker anh navigation)

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

<<<<<<< HEAD
import com.example.mini_project2.database.entity.Movie;
=======
import com.example.movie_ticket_app.database.entity.Movie;
>>>>>>> c1bdf99 (add ticker anh navigation)

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
