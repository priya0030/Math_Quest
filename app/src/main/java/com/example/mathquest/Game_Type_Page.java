package com.example.mathquest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;
import java.util.ArrayList;
import java.util.List;

public class Game_Type_Page extends AppCompatActivity {

    LinearLayout tv_easy, tv_medium, tv_hard, tv_complex, tv_play;
    CheckBox iv_add, iv_sub, iv_mul, iv_div;
    String selectedLevel = "Easy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game_type);

        Paper.init(this);
        tv_easy = findViewById(R.id.ll_easy);
        tv_medium = findViewById(R.id.ll_medium);
        tv_hard = findViewById(R.id.ll_hard);
        tv_complex = findViewById(R.id.ll_complex);
        tv_play = findViewById(R.id.tv_play);
        iv_add = findViewById(R.id.iv_add);
        iv_sub = findViewById(R.id.iv_sub);
        iv_mul = findViewById(R.id.iv_mul);
        iv_div = findViewById(R.id.iv_div);

        // Restore saved level and operations
        selectedLevel = Paper.book().read("selected_level", "Easy");
        iv_add.setChecked(Paper.book().read("add", false));
        iv_sub.setChecked(Paper.book().read("sub", false));
        iv_mul.setChecked(Paper.book().read("mul", false));
        iv_div.setChecked(Paper.book().read("div", false));

        tv_easy.setOnClickListener(v -> saveLevel("Easy"));
        tv_medium.setOnClickListener(v -> saveLevel("Medium"));
        tv_hard.setOnClickListener(v -> saveLevel("Hard"));
        tv_complex.setOnClickListener(v -> saveLevel("Complex"));

        iv_add.setOnClickListener(v -> saveSelectedSigns());
        iv_sub.setOnClickListener(v -> saveSelectedSigns());
        iv_mul.setOnClickListener(v -> saveSelectedSigns());
        iv_div.setOnClickListener(v -> saveSelectedSigns());

        tv_play.setOnClickListener(v -> {
            if (validateSelection()) {
                Intent intent = new Intent(Game_Type_Page.this, EasyLevelActivity.class);
                startActivity(intent);
            }
        });


    }

    private void saveLevel(String level) {
        selectedLevel = level;
        Paper.book().write("selected_level", level);
    }

    private void saveSelectedSigns() {
        List<String> selectedSigns = new ArrayList<>();
        if (iv_add.isChecked()) selectedSigns.add("+");
        if (iv_sub.isChecked()) selectedSigns.add("-");
        if (iv_mul.isChecked()) selectedSigns.add("*");
        if (iv_div.isChecked()) selectedSigns.add("/");

        // Save selected operations in PaperDB
        Paper.book().write("selected_signs", selectedSigns);
    }

    private boolean validateSelection() {
        List<String> selectedSigns = Paper.book().read("selected_signs", new ArrayList<>());

        if (selectedSigns.isEmpty()) {
            Toast.makeText(this, "Please select at least one operation!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
