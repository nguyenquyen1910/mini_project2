package com.example.movie_ticket_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movie_ticket_app.database.MovieDatabase;
import com.example.movie_ticket_app.database.entity.Ticket;

import java.util.List;
import java.util.concurrent.Executors;

public class MyTicketsActivity extends AppCompatActivity {

    private int userId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);

        userId = getIntent().getIntExtra("userId", -1);

        RecyclerView rvTickets = findViewById(R.id.rvTickets);
        LinearLayout layoutEmpty = findViewById(R.id.layoutEmpty);

        rvTickets.setLayoutManager(new LinearLayoutManager(this));

        TaskbarNavigator.setup(this, TaskbarNavigator.TAB_TICKET, userId);

        if (userId == -1) {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvTickets.setVisibility(View.GONE);
            Toast.makeText(this, "Vui long dang nhap de xem ve", Toast.LENGTH_SHORT).show();
            return;
        }

        loadTickets(rvTickets, layoutEmpty);
    }

    private void loadTickets(RecyclerView rvTickets, LinearLayout layoutEmpty) {
        MovieDatabase db = MovieDatabase.getInstance(this);

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Ticket> tickets = db.ticketDao().getTicketsByUser(userId);

            runOnUiThread(() -> {
                if (tickets == null || tickets.isEmpty()) {
                    layoutEmpty.setVisibility(View.VISIBLE);
                    rvTickets.setVisibility(View.GONE);
                } else {
                    layoutEmpty.setVisibility(View.GONE);
                    rvTickets.setVisibility(View.VISIBLE);
                    rvTickets.setAdapter(new TicketAdapter(tickets, db, ticket -> {
                        Intent intent = new Intent(this, TicketDetailActivity.class);
                        intent.putExtra("ticketId", ticket.getTicketId());
                        intent.putExtra("userId", userId);
                        startActivity(intent);
                    }));
                }
            });
        });
    }
}
