package com.example.movie_ticket_app.database.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class TicketWithDetails {
    @Embedded
    public Ticket ticket;

    @Relation(parentColumn = "showtimeId", entityColumn = "showtimeId", entity = Showtime.class)
    public ShowtimeWithDetails showtimeWithDetails;
}
