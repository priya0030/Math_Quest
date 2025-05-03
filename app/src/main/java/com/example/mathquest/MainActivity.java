package com.example.mathquest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    ImageView iv_privacy;
    LinearLayout tv_play, tv_score, tv_share, tv_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Paper.init(this);

        iv_privacy = findViewById(R.id.iv_policy);
        tv_play = findViewById(R.id.tv_play);
        tv_score = findViewById(R.id.tv_score);
        tv_rate = findViewById(R.id.tv_rate);
        tv_share = findViewById(R.id.tv_share);

        tv_play.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Game_Type_Page.class);
            startActivity(intent);
        });

    }
}