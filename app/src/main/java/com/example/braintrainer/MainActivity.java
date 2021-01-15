package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView textViewLiveScore;
    private TextView textViewTimer;
    private TextView textViewQuestion;
    private TextView textViewAnswer1;
    private TextView textViewAnswer2;
    private TextView textViewAnswer3;
    private TextView textViewAnswer4;

    private int numberOfRightAnswer;
    private int rightAnswer;
    private String question;
    //private boolean isPositive;
    private int minOfNumber=5;
    private int maxOfNumber=30;
    private int counterOfQuestions;
    private int counterOfRightAnswers;
    private Boolean isGameOver;

    ArrayList<TextView> answers = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewLiveScore = findViewById(R.id.textViewLiveScore);

        textViewTimer = findViewById(R.id.textViewTimer);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewAnswer1 = findViewById(R.id.textViewAnswer1);
        textViewAnswer2 = findViewById(R.id.textViewAnswer2);
        textViewAnswer3 = findViewById(R.id.textViewAnswer3);
        textViewAnswer4 = findViewById(R.id.textViewAnswer4);

        answers.add(textViewAnswer1);
        answers.add(textViewAnswer2);
        answers.add(textViewAnswer3);
        answers.add(textViewAnswer4);
        counterOfQuestions=0;
        counterOfRightAnswers=0;
        isGameOver=false;
        playGame();


        CountDownTimer timer = new CountDownTimer(15000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textViewTimer.setText(getTime(millisUntilFinished));
                if(millisUntilFinished<10000){
                    textViewTimer.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            }

            @Override
            public void onFinish() {
                isGameOver=true;
                textViewTimer.setText("00:00");
                Intent intent = new Intent(MainActivity.this,Score.class);
                intent.putExtra("score",counterOfRightAnswers);
                startActivity(intent);


            }
        };
        timer.start();




    }

    private  void generateQuestion(){
        int a = (int) (Math.random()*maxOfNumber+1)+minOfNumber;
        int b = (int) (Math.random()*maxOfNumber+1)+minOfNumber;
        int mark = (int) (Math.random()*2);
        Boolean isPositive = mark==1;
        if(isPositive){
            question=String.format("%s + %s",a,b);
            rightAnswer=a+b;
        }else{
            question=String.format("%s - %s",a,b);
            rightAnswer = a-b;
        }
        numberOfRightAnswer = (int) (Math.random()*4);

    }
    private int generateWrongAnswer(){
        int answ;
        do{
            answ = (int)(Math.random()*maxOfNumber*2)-maxOfNumber;
        }while (answ==rightAnswer);
        return answ;
    }

    private void playGame(){
        generateQuestion();
        textViewQuestion.setText(question);
        for(int i=0;i<answers.size();i++){
            if(i==numberOfRightAnswer){
                answers.get(i).setText(Integer.toString(rightAnswer));
            }else {
                answers.get(i).setText(Integer.toString(generateWrongAnswer()));
            }
        }

        textViewLiveScore.setText(String.format("%s/%s",counterOfRightAnswers,counterOfQuestions));
        counterOfQuestions++;


    }


    public void onClickAnswerPressed(View view) {
        if(!isGameOver) {
            TextView textViewAnswer = (TextView) view;
            String answer = textViewAnswer.getText().toString();
            if (Integer.toString(rightAnswer).equals(answer)) {
                Toast.makeText(this, "Верно", Toast.LENGTH_SHORT).show();
                counterOfRightAnswers++;
            } else {
                Toast.makeText(this, "Не верно", Toast.LENGTH_SHORT).show();
            }

            playGame();
        }

    }
    private String getTime(long milliseconds){
        int seconds = (int) (milliseconds/1000);
        int minutes = seconds/60;
        seconds = seconds%60;
        return String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);

    }


}