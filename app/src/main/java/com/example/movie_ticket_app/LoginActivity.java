package com.example.movie_ticket_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movie_ticket_app.database.MovieDatabase;
import com.example.movie_ticket_app.database.entity.User;

import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private com.google.android.material.textfield.TextInputEditText etUsername, etPassword;
    private TextView tvError;
    private MovieDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = MovieDatabase.getInstance(this);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvError = findViewById(R.id.tvError);

        // Back button
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Login
        findViewById(R.id.btnLogin).setOnClickListener(v -> login());

        // Go to Register
        findViewById(R.id.tvGoRegister)
                .setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }

    private void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Vui long nhap day du thong tin");
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            User user = db.userDao().login(username, password);
            runOnUiThread(() -> {
                if (user != null) {
                    Toast.makeText(this, "Dang nhap thanh cong!", Toast.LENGTH_SHORT).show();
                    Intent result = new Intent();
                    result.putExtra("userId", user.getUserId());
                    result.putExtra("fullName", user.getFullName());
                    setResult(RESULT_OK, result);
                    finish();
                } else {
                    showError("Sai ten dang nhap hoac mat khau");
                }
            });
        });
    }

    private void showError(String msg) {
        tvError.setText(msg);
        tvError.setVisibility(android.view.View.VISIBLE);
    }
}
