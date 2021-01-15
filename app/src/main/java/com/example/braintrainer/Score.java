package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class Score extends AppCompatActivity {
    private int score;
    private int maxScore;
    private TextView textViewScore;
    private TextView textViewTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        textViewScore = findViewById(R.id.textViewScore);
        textViewTop = findViewById(R.id.textViewMaxScore);

        Intent intent = getIntent();
        if(intent!=null && intent.hasExtra("score")){
            score = intent.getIntExtra("score",0);
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        maxScore = preferences.getInt("top",0);
        if(score>maxScore) {
            preferences.edit().putInt("top", score).apply();
            textViewTop.setText(String.format("Лучший результат %s",score));

        }else {
            textViewTop.setText(String.format("Лучший результат %s",maxScore));

        }



        textViewScore.setText(String.format("Ваш результат  %s",score));
    }

    public void onClickNewGamePressed(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}