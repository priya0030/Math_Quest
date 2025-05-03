package com.example.mathquest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.paperdb.Paper;

public class Score_Board_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvLabel;
    ImageView btnBack, ivDelete;
    ScoreAdapter scoreAdapter;
    List<ScoreModel> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board); // Ensure this matches your XML filename

        Paper.init(this);

        recyclerView = findViewById(R.id.recycler);
        tvLabel = findViewById(R.id.tv_lable);
        btnBack = findViewById(R.id.btn_back);
        ivDelete = findViewById(R.id.iv_delete);

        // Load score list from PaperDB
        scoreList = Paper.book().read("scoreList");

        if (scoreList != null && !scoreList.isEmpty()) {
            tvLabel.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            scoreAdapter = new ScoreAdapter(scoreList);
            recyclerView.setAdapter(scoreAdapter);
        } else {
            tvLabel.setVisibility(View.VISIBLE);
        }

        btnBack.setOnClickListener(v -> finish());

        ivDelete.setOnClickListener(v -> {
            Paper.book().delete("scoreList");
            if (scoreAdapter != null) scoreAdapter.clear();
            tvLabel.setVisibility(View.VISIBLE);
        });
    }
}