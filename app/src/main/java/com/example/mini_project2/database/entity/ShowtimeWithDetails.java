package com.example.mini_project2.database.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ShowtimeWithDetails {
    @Embedded
    public Showtime showtime;

    @Relation(parentColumn = "movieId", entityColumn = "movieId")
    public Movie movie;

    @Relation(parentColumn = "theaterId", entityColumn = "theaterId")
    public Theater theater;
}
