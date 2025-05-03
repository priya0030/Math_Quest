package com.example.mathquest;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class EasyLevelActivity extends AppCompatActivity {

    ImageView emoji1, emoji2, back;
    EditText ev_no, ev_player1, ev_player2;
    LinearLayout tv_start;
    Integer no_of_Que = 10;
    String player1Name, player2Name;
    int selectedEmoji1ResId, selectedEmoji2ResId;  // To store selected emoji for each player

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_easy_level);

        Paper.init(this);
        emoji1 = findViewById(R.id.iv_emoji1);
        emoji2 = findViewById(R.id.iv_emoji2);
        ev_no = findViewById(R.id.ev_no);
        ev_player1 = findViewById(R.id.ev_player1);
        ev_player2 = findViewById(R.id.ev_player2);
        tv_start = findViewById(R.id.tv_start);
        back = findViewById(R.id.btn_back);

        loadSavedData();

        //emoji1.setOnClickListener(v -> showEmojiSelectionDialog(1));

        //emoji2.setOnClickListener(v -> showEmojiSelectionDialog(2));

        ev_no.setOnEditorActionListener((v, actionId, event) -> {
            String numText = ev_no.getText().toString().trim();
            if (!numText.isEmpty()) {
                no_of_Que = Integer.parseInt(numText);
                Paper.book().write("NumberOfQuestions", no_of_Que);
            }
            return false;
        });

        // Save player names when edited
        ev_player1.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                player1Name = s.toString();
                Paper.book().write("Player1Name", player1Name);
            }
        });

        ev_player2.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                player2Name = s.toString();
                Paper.book().write("Player2Name", player2Name);
            }
        });

        tv_start.setOnClickListener(v -> {
            Intent intent = new Intent(EasyLevelActivity.this, LevelPage.class);
            startActivity(intent);
        });
        back.setOnClickListener(v -> finish());
    }


    private void loadSavedData() {
        no_of_Que = Paper.book().read("NumberOfQuestions", 10);
        ev_no.setText(String.valueOf(no_of_Que));

        player1Name = Paper.book().read("Player1Name", "Player 1");
        player2Name = Paper.book().read("Player2Name", "Player 2");
        ev_player1.setText(player1Name);
        ev_player2.setText(player2Name);

        selectedEmoji1ResId = Paper.book().read("Player1Emoji", R.drawable.e1);
        selectedEmoji2ResId = Paper.book().read("Player2Emoji", R.drawable.e2);
        if (selectedEmoji1ResId != R.drawable.e1) emoji1.setImageResource(selectedEmoji1ResId);
        if (selectedEmoji2ResId != R.drawable.e2) emoji2.setImageResource(selectedEmoji2ResId);
    }

    abstract class SimpleTextWatcher implements TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override public abstract void afterTextChanged(Editable s);
    }
}
