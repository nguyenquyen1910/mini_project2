package com.example.movie_ticket_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movie_ticket_app.database.MovieDatabase;
import com.example.movie_ticket_app.database.entity.ShowtimeWithDetails;
import com.example.movie_ticket_app.database.entity.Ticket;

import java.util.concurrent.Executors;

public class TicketDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        MovieDatabase db = MovieDatabase.getInstance(this);
        int ticketId = getIntent().getIntExtra("ticketId", -1);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        TextView tvMovieTitle = findViewById(R.id.tvMovieTitle);
        TextView tvTheater = findViewById(R.id.tvTheater);
        TextView tvShowtime = findViewById(R.id.tvShowtime);
        TextView tvSeats = findViewById(R.id.tvSeats);
        TextView tvBookingDate = findViewById(R.id.tvBookingDate);
        TextView tvTotal = findViewById(R.id.tvTotal);

        Executors.newSingleThreadExecutor().execute(() -> {
            Ticket ticket = db.ticketDao().getTicketById(ticketId);
            if (ticket != null) {
                ShowtimeWithDetails details = db.showtimeDao().getShowtimeWithDetailsById(ticket.getShowtimeId());

                runOnUiThread(() -> {
                    if (details != null) {
                        tvMovieTitle.setText(details.movie.getTitle());
                        tvTheater.setText(details.theater.getTheaterName());
                        tvShowtime.setText(details.showtime.getShowDate() + " - " + details.showtime.getShowTime());
                    }
                    tvSeats.setText(ticket.getSeatNumber());
                    tvBookingDate.setText(ticket.getBookingDate());
                    tvTotal.setText(String.format("%,.0f VND", ticket.getTotalPrice()));
                });
            }
        });
    }
}
