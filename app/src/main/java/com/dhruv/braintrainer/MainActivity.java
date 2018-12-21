package com.dhruv.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button playAgainButton;
    TextView resultTextView;
    TextView timerTextView;
    TextView scoreTextView;
    TextView questionTextView;
    GridLayout gridLayout;

    int correctAnswers;
    int totalQuestions;
    int currentAnswer;

    boolean gameActive;

    public void reset () {
        resultTextView.setText("");
        timerTextView.setText("30s");
        scoreTextView.setText("0/0");
        correctAnswers = 0;
        totalQuestions = 0;
        gameActive = true;
    }

    public int getRandom () {
        int max = 100, min = 1;
        Random rand=new Random();

        return rand.nextInt((max - min) + 1) + min;
    }

    public void newQuestion () {

        int max = 50, min = 1;

        Random rand=new Random();
        int num1 = rand.nextInt((max - min) + 1) + min;
        int num2 = rand.nextInt((max - min) + 1) + min;
        currentAnswer = num1 + num2;
        Log.i("Answer", Integer.toString(currentAnswer));

        questionTextView.setText(Integer.toString(num1) + " + " + Integer.toString(num2) + " = " + Integer.toString(currentAnswer));

        int correctOption = rand.nextInt(4);

        int count = gridLayout.getChildCount();
        for(int i = 0 ; i <count ; i++){

            Button child =(Button) gridLayout.getChildAt(i);
            if (i == correctOption) {

                child.setText(Integer.toString(currentAnswer));
                child.setTag(currentAnswer);
            } else {

                int randomNumber = getRandom();
                child.setText(Integer.toString(randomNumber));
                child.setTag(randomNumber);
            }
        }
    }

    public void optionChoosed (View view) {

        if (!gameActive) {
            return;
        }

        totalQuestions++;

        if ((int) view.getTag() == currentAnswer) {
            correctAnswers++;
            resultTextView.setText("CORRECT!");
        } else {
            resultTextView.setText("WRONG!");
        }

        scoreTextView.setText(Integer.toString(correctAnswers) + "/" + Integer.toString(totalQuestions));
        newQuestion();
    }

    public void startGame (View view) {

        reset();
        playAgainButton.setVisibility(View.INVISIBLE);

        newQuestion();

        new CountDownTimer(30000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf(millisUntilFinished/1000) + "s");
            }

            @Override
            public void onFinish() {

                gameActive = false;
                playAgainButton.setVisibility(View.VISIBLE);
                playAgainButton.setText("Play Again!");


            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playAgainButton = (Button) findViewById(R.id.playAgainButton);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        playAgainButton.setText("Play!");
        gameActive = false;
        reset();
    }
}
