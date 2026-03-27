package com.example.movie_ticket_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_ticket_app.database.MovieDatabase;
import com.example.movie_ticket_app.database.entity.Theater;

import java.util.List;
import java.util.concurrent.Executors;

public class TheaterListActivity extends AppCompatActivity {

    private MovieDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        db = MovieDatabase.getInstance(this);
        int userId = getIntent().getIntExtra("userId", -1);

        TaskbarNavigator.setup(this, TaskbarNavigator.TAB_THEATER, userId);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Danh sach rap chieu");

        RecyclerView rv = findViewById(R.id.rvCategories);
        rv.setLayoutManager(new LinearLayoutManager(this));

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Theater> list = db.theaterDao().getAllTheaters();
            runOnUiThread(() -> rv.setAdapter(new TheaterListAdapter(list, theater -> {
                Intent i = new Intent(this, TheaterShowtimeActivity.class);
                i.putExtra("theaterId", theater.getTheaterId());
                i.putExtra("theaterName", theater.getTheaterName());
                i.putExtra("userId", userId);
                startActivity(i);
            })));
        });
    }

    // Simple inline adapter for full-width theater cards
    static class TheaterListAdapter extends RecyclerView.Adapter<TheaterListAdapter.VH> {
        private final List<Theater> items;
        private final OnClick listener;

        interface OnClick {
            void onClick(Theater t);
        }

        TheaterListAdapter(List<Theater> items, OnClick listener) {
            this.items = items;
            this.listener = listener;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_theater, parent, false);
            return new VH(v);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            Theater theater = items.get(position);
            holder.tvName.setText(theater.getTheaterName());
            holder.tvAddress.setText(theater.getAddress());
            holder.itemView.setOnClickListener(v -> listener.onClick(theater));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class VH extends RecyclerView.ViewHolder {
            TextView tvName, tvAddress;

            VH(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvTheaterName);
                tvAddress = itemView.findViewById(R.id.tvAddress);
            }
        }
    }
}
