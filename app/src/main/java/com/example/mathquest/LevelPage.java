package com.example.mathquest;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import io.paperdb.Paper;

public class LevelPage extends AppCompatActivity implements PauseScreen.PauseScreenListener{

    ImageView emoji1, emoji2, iv_play;
    TextView tv_player1, tv_player2, tv_no1, tv_que1, tv_op1, tv_op2, tv_op3, tv_score1,
            tv_no2, tv_que2, tv2_op1, tv2_op2, tv2_op3, tv_score2;

    Question currentQuestion;
    int currentQuestionIndex = 1;
    int totalQuestions = 10;

    int player1Score = 0;
    int player2Score = 0;
    private boolean isPaused = false;
    String no = "Q:"+currentQuestionIndex+"/"+totalQuestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_level_page);

        Paper.init(this);
        emoji1 = findViewById(R.id.iv_emoji1);
        emoji2 = findViewById(R.id.iv_emoji2);
        tv_no1 = findViewById(R.id.tv_no1);
        tv_no2 = findViewById(R.id.tv_no2);
        tv_player1 = findViewById(R.id.tv_name1);
        tv_player2 = findViewById(R.id.tv_name2);
        tv_que1 = findViewById(R.id.tv_que1);
        tv_op1 = findViewById(R.id.tv_op1);
        tv_op2 = findViewById(R.id.tv_op2);
        tv_op3 = findViewById(R.id.tv_op3);
        tv_score1 = findViewById(R.id.tv_score1);
        tv_que2 = findViewById(R.id.tv_que2);
        tv2_op1 = findViewById(R.id.tv2_op1);
        tv2_op2 = findViewById(R.id.tv2_op2);
        tv2_op3 = findViewById(R.id.tv2_op3);
        tv_score2 = findViewById(R.id.tv_score2);
        iv_play = findViewById(R.id.iv_play);

        tv_player1.setText(Paper.book().read("player1", "Player 1"));
        tv_player2.setText(Paper.book().read("player2", "Player 2"));
        totalQuestions = Paper.book().read("Number of Que.", 10);
        emoji1.setImageResource(Paper.book().read("Player1Emoji", R.drawable.e1));
        emoji2.setImageResource(Paper.book().read("Player2Emoji", R.drawable.e2));
        tv_no1.setText(no);
        tv_no2.setText(no);

        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPaused = true;
                PauseScreen pauseDialog = new PauseScreen(LevelPage.this);
                pauseDialog.setGameData(player1Score, player2Score, currentQuestionIndex);
                pauseDialog.setListener((PauseScreen.PauseScreenListener) LevelPage.this);
                pauseDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                pauseDialog.show();
            }
        });

        setupClickListeners();
        loadNextQuestion();
    }
    @Override
    public void onGameStateUpdated(int player1Score, int player2Score, int currentQuestionIndex) {
        // Update the game state with the new values
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.currentQuestionIndex = currentQuestionIndex;
        tv_score1.setText(String.valueOf(player1Score));
        tv_score2.setText(String.valueOf(player2Score));
        tv_no1.setText(no);
        tv_no2.setText(no);
        Log.d("Que","Q:"+currentQuestionIndex+"/"+totalQuestions);
    }

    private void setupClickListeners() {
        View.OnClickListener player1Click = v -> handleAnswer((TextView) v, 1);
        View.OnClickListener player2Click = v -> handleAnswer((TextView) v, 2);

        tv_op1.setOnClickListener(player1Click);
        tv_op2.setOnClickListener(player1Click);
        tv_op3.setOnClickListener(player1Click);

        tv2_op1.setOnClickListener(player2Click);
        tv2_op2.setOnClickListener(player2Click);
        tv2_op3.setOnClickListener(player2Click);
    }

    private void handleAnswer(TextView selectedView, int playerNumber) {
        int selectedAnswer = Integer.parseInt(selectedView.getText().toString());

        if (selectedAnswer == currentQuestion.correctAnswer) {
            selectedView.setTextColor(getColor(android.R.color.holo_green_light));
            if (playerNumber == 1) player1Score++;
            else player2Score++;
            new Handler().postDelayed(this::loadNextQuestion, 500);
        } else {
            selectedView.setTextColor(getColor(android.R.color.holo_red_light));
            if (playerNumber == 1 && player1Score > 0) player1Score--;
            if (playerNumber == 2 && player2Score > 0) player2Score--;
            new Handler().postDelayed(this::resetOptionColors, 500);
        }

        tv_score1.setText("Score: " + player1Score);
        tv_score2.setText("Score: " + player2Score);
    }

    private void loadNextQuestion() {
        if (currentQuestionIndex > totalQuestions) {
            Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
            return;
        }

        String level = Paper.book().read("selected_level", "easy");
        List<String> selectedSigns = Paper.book().read("selected_signs", Arrays.asList("+"));
        currentQuestion = Questions.getQuestion(selectedSigns,level);
        currentQuestionIndex++;

        String questionText = currentQuestion.getQuestionText();
        tv_que1.setText(questionText);
        tv_que2.setText(questionText);

        List<Integer> options = currentQuestion.options;
        tv_op1.setText(String.valueOf(options.get(0)));
        tv_op2.setText(String.valueOf(options.get(1)));
        tv_op3.setText(String.valueOf(options.get(2)));

        tv2_op1.setText(String.valueOf(options.get(0)));
        tv2_op2.setText(String.valueOf(options.get(1)));
        tv2_op3.setText(String.valueOf(options.get(2)));

        resetOptionColors();
        tv_no1.setText(no);
        tv_no2.setText(no);
    }

    private void resetOptionColors() {
        tv_op1.setTextColor(getColor(android.R.color.white));
        tv_op2.setTextColor(getColor(android.R.color.white));
        tv_op3.setTextColor(getColor(android.R.color.white));

        tv2_op1.setTextColor(getColor(android.R.color.white));
        tv2_op2.setTextColor(getColor(android.R.color.white));
        tv2_op3.setTextColor(getColor(android.R.color.white));
    }
}
