package com.example.mini_project2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mini_project2.database.MovieDatabase;
import com.example.mini_project2.database.entity.ShowtimeWithDetails;

import java.util.List;
import java.util.concurrent.Executors;

public class TheaterShowtimeActivity extends AppCompatActivity {

    private MovieDatabase db;
    private int loggedInUserId = -1;
    private ActivityResultLauncher<Intent> loginLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime_list);

        db = MovieDatabase.getInstance(this);
        loggedInUserId = getIntent().getIntExtra("userId", -1);
        int theaterId = getIntent().getIntExtra("theaterId", -1);
        String theaterName = getIntent().getStringExtra("theaterName");

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(theaterName != null ? theaterName : "Lich chieu tai rap");

        loginLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        loggedInUserId = result.getData().getIntExtra("userId", -1);
                    }
                });

        RecyclerView rv = findViewById(R.id.rvShowtimes);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Executors.newSingleThreadExecutor().execute(() -> {
            List<ShowtimeWithDetails> list = db.showtimeDao().getAllShowtimesWithDetails();
            // Filter by theaterId
            list.removeIf(s -> s.theater.getTheaterId() != theaterId);

            runOnUiThread(() -> rv.setAdapter(new ShowtimeAdapter(list, showtime -> {
                if (loggedInUserId == -1) {
                    Toast.makeText(this, "Vui long dang nhap de dat ve", Toast.LENGTH_SHORT).show();
                    loginLauncher.launch(new Intent(this, LoginActivity.class));
                } else {
                    Intent i = new Intent(this, SeatSelectionActivity.class);
                    i.putExtra("showtimeId", showtime.showtime.getShowtimeId());
                    i.putExtra("userId", loggedInUserId);
                    startActivity(i);
                }
            })));
        });
    }
}
