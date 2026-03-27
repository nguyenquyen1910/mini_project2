package com.example.movie_ticket_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_ticket_app.database.entity.ShowtimeWithDetails;

import java.util.List;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ViewHolder> {

    private final List<ShowtimeWithDetails> showtimes;
    private final OnShowtimeClickListener listener;

    public interface OnShowtimeClickListener {
        void onShowtimeClick(ShowtimeWithDetails showtime);
    }

    public ShowtimeAdapter(List<ShowtimeWithDetails> showtimes, OnShowtimeClickListener listener) {
        this.showtimes = showtimes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_showtime, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowtimeWithDetails item = showtimes.get(position);
        holder.tvMovieTitle.setText(item.movie.getTitle());
        holder.tvTheater.setText(item.theater.getTheaterName());
        holder.tvDateTime.setText(item.showtime.getShowDate() + " - " + item.showtime.getShowTime());
        holder.tvPrice.setText(String.format("%,.0f VND", item.showtime.getPrice()));
        holder.tvSeats.setText("Con " + item.showtime.getAvailableSeats() + " ghe");

        holder.itemView.setOnClickListener(v -> listener.onShowtimeClick(item));
    }

    @Override
    public int getItemCount() {
        return showtimes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieTitle, tvTheater, tvDateTime, tvPrice, tvSeats;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovieTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvTheater = itemView.findViewById(R.id.tvTheater);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvSeats = itemView.findViewById(R.id.tvSeats);
        }
    }
}
