package com.example.movie_ticket_app.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.movie_ticket_app.database.dao.*;
import com.example.movie_ticket_app.database.entity.*;

@Database(entities = { User.class, Movie.class, Theater.class, Showtime.class,
        Ticket.class }, version = 3, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static volatile MovieDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract MovieDao movieDao();

    public abstract TheaterDao theaterDao();

    public abstract ShowtimeDao showtimeDao();

    public abstract TicketDao ticketDao();

    public static MovieDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (MovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieDatabase.class, "movie_db")
                            .fallbackToDestructiveMigration()
                            .addCallback(seedData)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final Callback seedData = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Users
            db.execSQL("INSERT INTO users (username, password, fullName) VALUES ('admin', 'admin', 'Admin')");
            db.execSQL("INSERT INTO users (username, password, fullName) VALUES ('user1', '1234', 'Nguyen Van A')");

            // Movies
            db.execSQL(
                    "INSERT INTO movies (title, description, duration, imageName, genre) VALUES ('Avengers: Endgame', 'Biet doi sieu anh hung chong lai Thanos', 181, 'avengers', 'Action')");
            db.execSQL(
                    "INSERT INTO movies (title, description, duration, imageName, genre) VALUES ('Spider-Man: No Way Home', 'Nguoi nhen doi mat da vu tru', 148, 'spiderman', 'Action')");
            db.execSQL(
                    "INSERT INTO movies (title, description, duration, imageName, genre) VALUES ('The Batman', 'Hiep si bong dem bao ve Gotham', 176, 'batman', 'Action')");
            db.execSQL(
                    "INSERT INTO movies (title, description, duration, imageName, genre) VALUES ('Dune', 'Cuoc phieu luu tren hanh tinh cat', 155, 'dune', 'Sci-Fi')");
            db.execSQL(
                    "INSERT INTO movies (title, description, duration, imageName, genre) VALUES ('Parasite', 'Ky sinh trung - phim Han Quoc dat giai Oscar', 132, 'parasite', 'Drama')");

            // Theaters
            db.execSQL(
                    "INSERT INTO theaters (theaterName, address) VALUES ('CGV Vincom', '72 Le Thanh Ton, Q.1, TP.HCM')");
            db.execSQL(
                    "INSERT INTO theaters (theaterName, address) VALUES ('Galaxy Nguyen Du', '116 Nguyen Du, Q.1, TP.HCM')");
            db.execSQL(
                    "INSERT INTO theaters (theaterName, address) VALUES ('Lotte Cinema', '469 Nguyen Huu Tho, Q.7, TP.HCM')");

            // Showtimes
            db.execSQL(
                    "INSERT INTO showtimes (movieId, theaterId, showDate, showTime, price, availableSeats) VALUES (1, 1, '2026-03-28', '10:00', 90000, 50)");
            db.execSQL(
                    "INSERT INTO showtimes (movieId, theaterId, showDate, showTime, price, availableSeats) VALUES (1, 1, '2026-03-28', '14:00', 90000, 50)");
            db.execSQL(
                    "INSERT INTO showtimes (movieId, theaterId, showDate, showTime, price, availableSeats) VALUES (1, 2, '2026-03-28', '19:00', 85000, 40)");
            db.execSQL(
                    "INSERT INTO showtimes (movieId, theaterId, showDate, showTime, price, availableSeats) VALUES (2, 1, '2026-03-28', '11:00', 95000, 50)");
            db.execSQL(
                    "INSERT INTO showtimes (movieId, theaterId, showDate, showTime, price, availableSeats) VALUES (2, 3, '2026-03-28', '16:00', 80000, 60)");
            db.execSQL(
                    "INSERT INTO showtimes (movieId, theaterId, showDate, showTime, price, availableSeats) VALUES (3, 2, '2026-03-28', '13:00', 90000, 45)");
            db.execSQL(
                    "INSERT INTO showtimes (movieId, theaterId, showDate, showTime, price, availableSeats) VALUES (4, 1, '2026-03-28', '20:00', 100000, 50)");
            db.execSQL(
                    "INSERT INTO showtimes (movieId, theaterId, showDate, showTime, price, availableSeats) VALUES (5, 3, '2026-03-28', '18:00', 75000, 55)");
        }
    };
}
