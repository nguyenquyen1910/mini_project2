package com.example.mini_project2.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "showtimes",
        foreignKeys = {
                @ForeignKey(entity = Movie.class,
                        parentColumns = "movieId", childColumns = "movieId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Theater.class,
                        parentColumns = "theaterId", childColumns = "theaterId",
                        onDelete = ForeignKey.CASCADE)
        },
        indices = {@Index("movieId"), @Index("theaterId")})
public class Showtime {
    @PrimaryKey(autoGenerate = true)
    private int showtimeId;
    private int movieId;
    private int theaterId;
    private String showDate;
    private String showTime;
    private double price;
    private int availableSeats;

    public Showtime(int movieId, int theaterId, String showDate, String showTime, double price, int availableSeats) {
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.showDate = showDate;
        this.showTime = showTime;
        this.price = price;
        this.availableSeats = availableSeats;
    }

    public int getShowtimeId() { return showtimeId; }
    public void setShowtimeId(int showtimeId) { this.showtimeId = showtimeId; }
    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }
    public int getTheaterId() { return theaterId; }
    public void setTheaterId(int theaterId) { this.theaterId = theaterId; }
    public String getShowDate() { return showDate; }
    public void setShowDate(String showDate) { this.showDate = showDate; }
    public String getShowTime() { return showTime; }
    public void setShowTime(String showTime) { this.showTime = showTime; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int availableSeats) { this.availableSeats = availableSeats; }
}
