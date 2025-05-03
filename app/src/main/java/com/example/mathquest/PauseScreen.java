package com.example.mathquest;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.getIntent;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class PauseScreen extends Dialog {

    private int player1Score;
    private int player2Score;
    private int currentQuestionIndex;
    private PauseScreenListener listener;

    private LinearLayout ll_continue1, ll_restart1, ll_home1;
    private LinearLayout ll_continue2, ll_restart2, ll_home2;

    public PauseScreen(Context context) {
        super(context);

        // Inflate the dialog layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.pause_dialog, null);

        // Set the layout for the dialog
        setContentView(dialogView);

        // Initialize views for Player 1 and Player 2 actions
        ll_continue1 = findViewById(R.id.ll_continue1);
        ll_restart1 = findViewById(R.id.ll_restart1);
        ll_home1 = findViewById(R.id.ll_home1);

        ll_continue2 = findViewById(R.id.ll_continue2);
        ll_restart2 = findViewById(R.id.ll_restart2);
        ll_home2 = findViewById(R.id.ll_home2);

        // Set click listeners for Player 1
        ll_continue1.setOnClickListener(v -> continueGame(context));
        ll_restart1.setOnClickListener(v -> restartGame(context));
        ll_home1.setOnClickListener(v -> goHome(context));

        // Set click listeners for Player 2
        ll_continue2.setOnClickListener(v -> continueGame(context));
        ll_restart2.setOnClickListener(v -> restartGame(context));
        ll_home2.setOnClickListener(v -> goHome(context));
    }
    public void setGameData(int player1Score, int player2Score, int currentQuestionIndex) {
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.currentQuestionIndex = currentQuestionIndex;
    }
    private void continueGame(Context context) {
        if (context instanceof PauseScreenListener) {
            ((PauseScreenListener) context).onGameStateUpdated(player1Score, player2Score, currentQuestionIndex);
            dismiss();  // Close the dialog
        } else {
            // Log an error if the context doesn't implement PauseScreenListener
            Log.e("PauseScreen", "Context does not implement PauseScreenListener");
            dismiss();  // Still dismiss the dialog even if the context isn't correct
        }
    }

    private void restartGame(Context context) {
        player1Score = 0;
        player2Score = 0;
        currentQuestionIndex = 1;

        if (context instanceof PauseScreenListener) {
            ((PauseScreenListener) context).onGameStateUpdated(player1Score, player2Score, currentQuestionIndex);
            Log.d("Restart", "Player1"+player1Score+player2Score);
        }

        dismiss();
    }

    private void goHome(Context context) {
        Intent homeIntent = new Intent(getContext(), MainActivity.class);
        context.startActivity(homeIntent);
        dismiss();
    }
    public void setListener(PauseScreenListener listener) {
        this.listener = listener;
    }

    public interface PauseScreenListener {
        void onGameStateUpdated(int player1Score, int player2Score, int currentQuestionIndex);
    }
}

