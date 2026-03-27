package com.example.mini_project2.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.mini_project2.database.entity.Theater;

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
