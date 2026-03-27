package com.example.movie_ticket_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_ticket_app.database.MovieDatabase;
import com.example.movie_ticket_app.database.entity.ShowtimeWithDetails;
import com.example.movie_ticket_app.database.entity.Ticket;

import java.util.List;
import java.util.concurrent.Executors;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private final List<Ticket> tickets;
    private final MovieDatabase db;
    private final OnTicketClickListener listener;

    public interface OnTicketClickListener {
        void onTicketClick(Ticket ticket);
    }

    public TicketAdapter(List<Ticket> tickets, MovieDatabase db, OnTicketClickListener listener) {
        this.tickets = tickets;
        this.db = db;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);

        holder.tvBookingDate.setText("Ngay dat: " + ticket.getBookingDate());
        holder.tvSeats.setText("Ghe: " + ticket.getSeatNumber());
        holder.tvTotal.setText(String.format("%,.0f VND", ticket.getTotalPrice()));

        // Load showtime details in background
        Executors.newSingleThreadExecutor().execute(() -> {
            ShowtimeWithDetails details = db.showtimeDao().getShowtimeWithDetailsById(ticket.getShowtimeId());
            holder.itemView.post(() -> {
                if (details != null) {
                    holder.tvMovieTitle.setText(details.movie.getTitle());
                    holder.tvShowInfo.setText(details.theater.getTheaterName() +
                            " | " + details.showtime.getShowDate() + " " + details.showtime.getShowTime());
                }
            });
        });

        holder.itemView.setOnClickListener(v -> listener.onTicketClick(ticket));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle, tvShowInfo, tvBookingDate, tvSeats, tvTotal;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvShowInfo = itemView.findViewById(R.id.tvShowInfo);
            tvBookingDate = itemView.findViewById(R.id.tvBookingDate);
            tvSeats = itemView.findViewById(R.id.tvSeats);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
}
