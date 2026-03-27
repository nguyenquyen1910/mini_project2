package com.example.movie_ticket_app.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movies")
public class Movie {
  @PrimaryKey(autoGenerate = true)
  private int movieId;
  private String title;
  private String description;
  private int duration; // phút
  private String imageName;
  private String genre;

  public Movie(String title, String description, int duration, String imageName, String genre) {
    this.title = title;
    this.description = description;
    this.duration = duration;
    this.imageName = imageName;
    this.genre = genre;
  }

  public int getMovieId() {
    return movieId;
  }

  public void setMovieId(int movieId) {
    this.movieId = movieId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public String getImageName() {
    return imageName;
  }

  public void setImageName(String imageName) {
    this.imageName = imageName;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }
}
