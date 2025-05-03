package com.example.mathquest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private List<ScoreModel> scoreList;

    public ScoreAdapter(List<ScoreModel> scoreList) {
        this.scoreList = scoreList;
    }

    public void clear() {
        scoreList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        ScoreModel score = scoreList.get(position);

        holder.tvName1.setText(score.getPlayer1Name());
        holder.tvScore1.setText("Score: " + score.getPlayer1Score());
        holder.tvName2.setText(score.getPlayer2Name());
        holder.tvScore2.setText("Score: " + score.getPlayer2Score());
        holder.tvDate.setText(score.getDate());

        // Decide winner
        if (score.getPlayer1Score() > score.getPlayer2Score()) {
            holder.tvResult1.setText("Winner 🏆");
            holder.tvResult2.setText("Better luck");
        } else if (score.getPlayer2Score() > score.getPlayer1Score()) {
            holder.tvResult2.setText("Winner 🏆");
            holder.tvResult1.setText("Better luck");
        } else {
            holder.tvResult1.setText("Draw 🤝");
            holder.tvResult2.setText("Draw 🤝");
        }

        // Optional: set emoji based on score or identity (static fallback used here)
        holder.ivEmoji1.setImageResource(R.drawable.e1);
        holder.ivEmoji2.setImageResource(R.drawable.e2);
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }

    static class ScoreViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView ivEmoji1, ivEmoji2;
        TextView tvName1, tvScore1, tvResult1;
        TextView tvName2, tvScore2, tvResult2;
        TextView tvDate;

        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            ivEmoji1 = itemView.findViewById(R.id.iv_emoji1);
            ivEmoji2 = itemView.findViewById(R.id.iv_emoji2);
            tvName1 = itemView.findViewById(R.id.tv_name1);
            tvScore1 = itemView.findViewById(R.id.tv_score1);
            tvResult1 = itemView.findViewById(R.id.tv_result1);
            tvName2 = itemView.findViewById(R.id.tv_name2);
            tvScore2 = itemView.findViewById(R.id.tv_score2);
            tvResult2 = itemView.findViewById(R.id.tv_result2);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}
