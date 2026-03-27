package com.example.movie_ticket_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movie_ticket_app.database.MovieDatabase;
import com.example.movie_ticket_app.database.entity.ShowtimeWithDetails;
import com.example.movie_ticket_app.database.entity.Ticket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class SeatSelectionActivity extends AppCompatActivity {

    private MovieDatabase db;
    private int showtimeId;
    private int userId;
    private ShowtimeWithDetails showtimeDetails;
    private List<String> selectedSeats = new ArrayList<>();
    private List<String> bookedSeats = new ArrayList<>();
    private double pricePerSeat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        db = MovieDatabase.getInstance(this);
        showtimeId = getIntent().getIntExtra("showtimeId", -1);
        userId = getIntent().getIntExtra("userId", -1);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        TextView tvMovieInfo = findViewById(R.id.tvMovieInfo);
        TextView tvShowtimeInfo = findViewById(R.id.tvShowtimeInfo);
        TextView tvSelectedSeats = findViewById(R.id.tvSelectedSeats);
        TextView tvTotalPrice = findViewById(R.id.tvTotalPrice);
        GridLayout gridSeats = findViewById(R.id.gridSeats);
        Button btnConfirm = findViewById(R.id.btnConfirm);

        Executors.newSingleThreadExecutor().execute(() -> {
            showtimeDetails = db.showtimeDao().getShowtimeWithDetailsById(showtimeId);
            bookedSeats = db.ticketDao().getBookedSeats(showtimeId);
            pricePerSeat = showtimeDetails.showtime.getPrice();

            runOnUiThread(() -> {
                tvMovieInfo.setText(showtimeDetails.movie.getTitle());
                tvShowtimeInfo.setText(showtimeDetails.theater.getTheaterName() +
                        " | " + showtimeDetails.showtime.getShowDate() +
                        " " + showtimeDetails.showtime.getShowTime());

                // Tao grid ghe 5x6
                String[] rows = { "A", "B", "C", "D", "E" };
                for (String row : rows) {
                    for (int col = 1; col <= 6; col++) {
                        String seatCode = row + col;
                        Button btnSeat = new Button(this);
                        btnSeat.setText(seatCode);
                        btnSeat.setTextSize(10);

                        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                        params.width = 0;
                        params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                        params.setMargins(4, 4, 4, 4);
                        btnSeat.setLayoutParams(params);

                        if (bookedSeats.contains(seatCode)) {
                            btnSeat.setEnabled(false);
                            btnSeat.setBackgroundColor(0xFF888888);
                        } else {
                            btnSeat.setBackgroundColor(0xFF4CAF50);
                            btnSeat.setOnClickListener(v -> {
                                if (selectedSeats.contains(seatCode)) {
                                    selectedSeats.remove(seatCode);
                                    btnSeat.setBackgroundColor(0xFF4CAF50);
                                } else {
                                    selectedSeats.add(seatCode);
                                    btnSeat.setBackgroundColor(0xFFFF9800);
                                }
                                updateSelection(tvSelectedSeats, tvTotalPrice);
                            });
                        }
                        gridSeats.addView(btnSeat);
                    }
                }
            });
        });

        btnConfirm.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Vui long chon it nhat 1 ghe", Toast.LENGTH_SHORT).show();
                return;
            }
            bookTickets();
        });
    }

    private void updateSelection(TextView tvSelectedSeats, TextView tvTotalPrice) {
        if (selectedSeats.isEmpty()) {
            tvSelectedSeats.setText("Chua chon ghe");
            tvTotalPrice.setText("0 VND");
        } else {
            tvSelectedSeats.setText("Ghe: " + String.join(", ", selectedSeats));
            double total = selectedSeats.size() * pricePerSeat;
            tvTotalPrice.setText(String.format("%,.0f VND", total));
        }
    }

    private void bookTickets() {
        Executors.newSingleThreadExecutor().execute(() -> {
            String seatStr = String.join(", ", selectedSeats);
            String bookingDate = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
            double totalPrice = selectedSeats.size() * pricePerSeat;

            Ticket ticket = new Ticket(userId, showtimeId, seatStr, bookingDate, totalPrice);
            long ticketId = db.ticketDao().insert(ticket);

            runOnUiThread(() -> {
                Toast.makeText(this, "Dat ve thanh cong!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, TicketDetailActivity.class);
                i.putExtra("ticketId", (int) ticketId);
                i.putExtra("userId", userId);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            });
        });
    }
}
