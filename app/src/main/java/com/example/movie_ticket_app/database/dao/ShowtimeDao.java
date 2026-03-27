package com.example.movie_ticket_app.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.movie_ticket_app.database.entity.Showtime;
import com.example.movie_ticket_app.database.entity.ShowtimeWithDetails;

import java.util.List;

@Dao
public interface ShowtimeDao {
    @Insert
    void insert(Showtime showtime);

    @Update
    void update(Showtime showtime);

    @Query("SELECT * FROM showtimes")
    List<Showtime> getAllShowtimes();

    @Query("SELECT * FROM showtimes WHERE showtimeId = :showtimeId")
    Showtime getShowtimeById(int showtimeId);

    @Query("SELECT * FROM showtimes WHERE movieId = :movieId")
    List<Showtime> getShowtimesByMovie(int movieId);

    @Query("SELECT * FROM showtimes WHERE theaterId = :theaterId")
    List<Showtime> getShowtimesByTheater(int theaterId);

    @Transaction
    @Query("SELECT * FROM showtimes")
    List<ShowtimeWithDetails> getAllShowtimesWithDetails();

    @Transaction
    @Query("SELECT * FROM showtimes WHERE movieId = :movieId")
    List<ShowtimeWithDetails> getShowtimesWithDetailsByMovie(int movieId);

    @Transaction
    @Query("SELECT * FROM showtimes WHERE showtimeId = :showtimeId")
    ShowtimeWithDetails getShowtimeWithDetailsById(int showtimeId);
}
