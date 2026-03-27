package com.example.movie_ticket_app;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public final class TaskbarNavigator {

    public static final int TAB_HOME = 0;
    public static final int TAB_THEATER = 1;
    public static final int TAB_TICKET = 2;

    private TaskbarNavigator() {
    }

    public static void setup(AppCompatActivity activity, int selectedTab, int userId) {
        View tabHome = activity.findViewById(R.id.tabHome);
        View tabTheater = activity.findViewById(R.id.tabCategory);
        View tabTicket = activity.findViewById(R.id.tabOrder);

        if (tabHome == null || tabTheater == null || tabTicket == null) {
            return;
        }

        int activeColor = ContextCompat.getColor(activity, R.color.taskbar_active);
        int inactiveColor = ContextCompat.getColor(activity, R.color.taskbar_inactive);

        // Set colors based on selected tab
        setTabColor(activity, R.id.iconHome, R.id.textHome, selectedTab == TAB_HOME ? activeColor : inactiveColor);
        setTabColor(activity, R.id.iconCategory, R.id.textCategory,
                selectedTab == TAB_THEATER ? activeColor : inactiveColor);
        setTabColor(activity, R.id.iconOrder, R.id.textOrder, selectedTab == TAB_TICKET ? activeColor : inactiveColor);

        // Set click listeners
        tabHome.setOnClickListener(v -> {
            if (selectedTab != TAB_HOME) {
                Intent intent = new Intent(activity, MainActivity.class);
                intent.putExtra("userId", userId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        });

        tabTheater.setOnClickListener(v -> {
            if (selectedTab != TAB_THEATER) {
                Intent intent = new Intent(activity, TheaterListActivity.class);
                intent.putExtra("userId", userId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        });

        tabTicket.setOnClickListener(v -> {
            if (selectedTab != TAB_TICKET) {
                Intent intent = new Intent(activity, MyTicketsActivity.class);
                intent.putExtra("userId", userId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            }
        });
    }

    private static void setTabColor(AppCompatActivity activity, int iconId, int textId, int color) {
        ImageView icon = activity.findViewById(iconId);
        TextView text = activity.findViewById(textId);
        if (icon != null) {
            icon.setImageTintList(ColorStateList.valueOf(color));
        }
        if (text != null) {
            text.setTextColor(color);
        }
    }
}
