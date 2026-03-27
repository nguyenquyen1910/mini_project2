package com.example.mini_project2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mini_project2.database.MovieDatabase;
import com.example.mini_project2.database.entity.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private MovieDatabase db;
    private int loggedInUserId = -1;
    private MovieAdapter movieAdapter;
    private GenreAdapter genreAdapter;
    private List<Movie> allMovies = new ArrayList<>();
    private TextView tvWelcome, tvSectionTitle;
    private TextView btnLogin;
    private ActivityResultLauncher<Intent> loginLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = MovieDatabase.getInstance(this);
        loggedInUserId = getIntent().getIntExtra("userId", -1);

        tvWelcome = findViewById(R.id.tvWelcome);
        tvSectionTitle = findViewById(R.id.tvSectionTitle);
        btnLogin = findViewById(R.id.btnLogin);
        EditText etSearch = findViewById(R.id.etSearch);

        tvSectionTitle.setText("Phim dang chieu");

        loginLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        loggedInUserId = result.getData().getIntExtra("userId", -1);
                        String fullName = result.getData().getStringExtra("fullName");
                        tvWelcome.setText(fullName);
                        tvWelcome.setVisibility(View.VISIBLE);
                        btnLogin.setVisibility(View.GONE);
                        TaskbarNavigator.setup(this, TaskbarNavigator.TAB_HOME, loggedInUserId);
                    }
                });

        btnLogin.setOnClickListener(v -> loginLauncher.launch(new Intent(this, LoginActivity.class)));

        RecyclerView rvMovies = findViewById(R.id.rvProducts);
        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
        movieAdapter = new MovieAdapter(new ArrayList<>(), movie -> {
            Intent i = new Intent(this, ShowtimeListActivity.class);
            i.putExtra("movieId", movie.getMovieId());
            i.putExtra("movieTitle", movie.getTitle());
            i.putExtra("userId", loggedInUserId);
            startActivity(i);
        });
        rvMovies.setAdapter(movieAdapter);

        // Genre RecyclerView - horizontal chips
        RecyclerView rvGenres = findViewById(R.id.rvCategories);
        rvGenres.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Search filter
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterBySearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        TaskbarNavigator.setup(this, TaskbarNavigator.TAB_HOME, loggedInUserId);
        loadData(rvGenres);
    }

    private void loadData(RecyclerView rvGenres) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Movie> movies = db.movieDao().getAllMovies();

            // Lay danh sach genre tu movies
            List<String> genres = new ArrayList<>();
            genres.add("Tat ca");
            for (Movie m : movies) {
                if (!genres.contains(m.getGenre())) {
                    genres.add(m.getGenre());
                }
            }

            runOnUiThread(() -> {
                allMovies.clear();
                allMovies.addAll(movies);
                movieAdapter.updateList(new ArrayList<>(allMovies));

                genreAdapter = new GenreAdapter(genres, genre -> {
                    if (genre.equals("Tat ca")) {
                        tvSectionTitle.setText("Phim dang chieu");
                        movieAdapter.updateList(new ArrayList<>(allMovies));
                    } else {
                        tvSectionTitle.setText("Phim " + genre);
                        filterByGenre(genre);
                    }
                });
                rvGenres.setAdapter(genreAdapter);
            });
        });
    }

    private void filterByGenre(String genre) {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Movie> filtered = db.movieDao().getMoviesByGenre(genre);
            runOnUiThread(() -> movieAdapter.updateList(filtered));
        });
    }

    private void filterBySearch(String query) {
        if (query.trim().isEmpty()) {
            movieAdapter.updateList(new ArrayList<>(allMovies));
            return;
        }
        String lower = query.toLowerCase().trim();
        List<Movie> filtered = new ArrayList<>();
        for (Movie m : allMovies) {
            if (m.getTitle().toLowerCase().contains(lower)) {
                filtered.add(m);
            }
        }
        movieAdapter.updateList(filtered);
    }
}
