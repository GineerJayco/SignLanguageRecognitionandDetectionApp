package com.example.imagepro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class GuessActivity extends AppCompatActivity {

    private EditText guessInput;
    private Button checkButton;
    private TextView resultText, scoreText, highScoreText;

    private TextToSpeech textToSpeech;
    private String correctAnswer;
    private int currentScore;
    private int highScore;

    private static final String PREFS_NAME = "HighScorePrefs";
    private static final String HIGH_SCORE_KEY = "high_score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess);

        guessInput = findViewById(R.id.guess_input);
        checkButton = findViewById(R.id.check_button);
        resultText = findViewById(R.id.result_text);
        scoreText = findViewById(R.id.score_text);
        highScoreText = findViewById(R.id.high_score_text);

        correctAnswer = getIntent().getStringExtra("selectedWord");
        currentScore = getIntent().getIntExtra("score", 0);

        // Load saved high score
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        highScore = prefs.getInt(HIGH_SCORE_KEY, 0);

        updateScoreUI();

        textToSpeech = new TextToSpeech(this, status -> {
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale.US);
            }
        });

        checkButton.setOnClickListener(v -> {
            String userGuess = guessInput.getText().toString().trim().toLowerCase();
            String actualAnswer = correctAnswer.toLowerCase();

            if (userGuess.isEmpty()) {
                Toast.makeText(this, "Please enter your guess!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userGuess.equals(actualAnswer)) {
                currentScore++;
                if (currentScore > highScore) {
                    highScore = currentScore;
                    // Save the new high score
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt(HIGH_SCORE_KEY, highScore);
                    editor.apply();
                }

                resultText.setText("Correct!");
                resultText.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                speakText("Correct!");

                updateScoreUI();
                goToNextRound();

            } else {
                resultText.setText("Game Over!");
                resultText.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                speakText("Game Over!");
                showGameOverDialog();
            }
        });
    }

    private void goToNextRound() {
        resultText.postDelayed(() -> {
            Intent intent = new Intent(GuessActivity.this, SequenceActivity.class);
            intent.putExtra("score", currentScore);
            startActivity(intent);
            finish();
        }, 2000);
    }

    private void showGameOverDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Your final score: " + currentScore + "\nHigh Score: " + highScore + "\nTry again?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, which) -> {
                    Intent intent = new Intent(GuessActivity.this, SequenceActivity.class);
                    intent.putExtra("score", 0);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> finish())
                .show();
    }

    private void updateScoreUI() {
        scoreText.setText("Score: " + currentScore);
        highScoreText.setText("High Score: " + highScore);
    }

    private void speakText(String text) {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
