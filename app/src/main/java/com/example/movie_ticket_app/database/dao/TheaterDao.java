package com.example.movie_ticket_app.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movie_ticket_app.database.entity.Theater;

import java.util.List;

@Dao
public interface TheaterDao {
    @Insert
    void insert(Theater theater);

    @Query("SELECT * FROM theaters")
    List<Theater> getAllTheaters();

    @Query("SELECT * FROM theaters WHERE theaterId = :theaterId")
    Theater getTheaterById(int theaterId);
}
