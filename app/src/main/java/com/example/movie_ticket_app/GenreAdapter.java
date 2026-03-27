<<<<<<< HEAD
package com.example.mini_project2;
=======
package com.example.movie_ticket_app;
>>>>>>> c1bdf99 (add ticker anh navigation)

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {

    private final List<String> genres;
    private final OnGenreClickListener listener;
    private int selectedPosition = 0;

    public interface OnGenreClickListener {
        void onGenreClick(String genre);
    }

    public GenreAdapter(List<String> genres, OnGenreClickListener listener) {
        this.genres = genres;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String genre = genres.get(position);
        holder.tvGenre.setText(genre);

        // Update background based on selection
        if (position == selectedPosition) {
            holder.tvGenre.setBackgroundResource(R.drawable.chip_bg_selected);
            holder.tvGenre.setTextColor(0xFFFFFFFF);
        } else {
            holder.tvGenre.setBackgroundResource(R.drawable.chip_bg_unselected);
            holder.tvGenre.setTextColor(0xFF333333);
        }

        holder.itemView.setOnClickListener(v -> {
            int prevSelected = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(prevSelected);
            notifyItemChanged(selectedPosition);
            listener.onGenreClick(genre);
        });
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvGenre;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGenre = itemView.findViewById(R.id.tvCategoryName);
        }
    }
}
