package com.example.movie_ticket_app.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.movie_ticket_app.database.entity.Ticket;

import java.util.List;

@Dao
public interface TicketDao {
    @Insert
    long insert(Ticket ticket);

    @Query("SELECT * FROM tickets WHERE userId = :userId")
    List<Ticket> getTicketsByUser(int userId);

    @Query("SELECT * FROM tickets WHERE ticketId = :ticketId")
    Ticket getTicketById(int ticketId);

    @Query("SELECT seatNumber FROM tickets WHERE showtimeId = :showtimeId")
    List<String> getBookedSeats(int showtimeId);
}
