package com.example.movie_ticket_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movie_ticket_app.database.MovieDatabase;
import com.example.movie_ticket_app.database.entity.User;

import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    private com.google.android.material.textfield.TextInputEditText etFullName, etUsername, etPassword;
    private TextView tvError;
    private MovieDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = MovieDatabase.getInstance(this);
        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvError = findViewById(R.id.tvError);

        // Back button
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Go to Login
        findViewById(R.id.tvGoLogin).setOnClickListener(v -> {
            finish(); // Quay ve Login
        });

        // Register
        findViewById(R.id.btnRegister).setOnClickListener(v -> register());
    }

    private void register() {
        String fullName = etFullName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showError("Vui long nhap day du thong tin");
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            User existing = db.userDao().getByUsername(username);
            runOnUiThread(() -> {
                if (existing != null) {
                    showError("Ten dang nhap da ton tai");
                } else {
                    Executors.newSingleThreadExecutor().execute(() -> {
                        db.userDao().insert(new User(username, password, fullName));
                        runOnUiThread(() -> {
                            Toast.makeText(this, "Dang ky thanh cong! Han dang nhap.", Toast.LENGTH_SHORT).show();
                            finish(); // Quay ve Login
                        });
                    });
                }
            });
        });
    }

    private void showError(String msg) {
        tvError.setText(msg);
        tvError.setVisibility(android.view.View.VISIBLE);
    }
}
