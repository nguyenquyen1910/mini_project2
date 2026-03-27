package com.example.movie_ticket_app.database.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tickets", foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "userId", childColumns = "userId", onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Showtime.class, parentColumns = "showtimeId", childColumns = "showtimeId", onDelete = ForeignKey.CASCADE)
}, indices = { @Index("userId"), @Index("showtimeId") })
public class Ticket {
        @PrimaryKey(autoGenerate = true)
        private int ticketId;
        private int userId;
        private int showtimeId;
        private String seatNumber;
        private String bookingDate;
        private double totalPrice;

        public Ticket(int userId, int showtimeId, String seatNumber, String bookingDate, double totalPrice) {
                this.userId = userId;
                this.showtimeId = showtimeId;
                this.seatNumber = seatNumber;
                this.bookingDate = bookingDate;
                this.totalPrice = totalPrice;
        }

        public int getTicketId() {
                return ticketId;
        }

        public void setTicketId(int ticketId) {
                this.ticketId = ticketId;
        }

        public int getUserId() {
                return userId;
        }

        public void setUserId(int userId) {
                this.userId = userId;
        }

        public int getShowtimeId() {
                return showtimeId;
        }

        public void setShowtimeId(int showtimeId) {
                this.showtimeId = showtimeId;
        }

        public String getSeatNumber() {
                return seatNumber;
        }

        public void setSeatNumber(String seatNumber) {
                this.seatNumber = seatNumber;
        }

        public String getBookingDate() {
                return bookingDate;
        }

        public void setBookingDate(String bookingDate) {
                this.bookingDate = bookingDate;
        }

        public double getTotalPrice() {
                return totalPrice;
        }

        public void setTotalPrice(double totalPrice) {
                this.totalPrice = totalPrice;
        }
}
