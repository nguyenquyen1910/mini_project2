package com.example.movie_ticket_app.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "theaters")
public class Theater {
    @PrimaryKey(autoGenerate = true)
    private int theaterId;
    private String theaterName;
    private String address;

    public Theater(String theaterName, String address) {
        this.theaterName = theaterName;
        this.address = address;
    }

    public int getTheaterId() { return theaterId; }
    public void setTheaterId(int theaterId) { this.theaterId = theaterId; }
    public String getTheaterName() { return theaterName; }
    public void setTheaterName(String theaterName) { this.theaterName = theaterName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
