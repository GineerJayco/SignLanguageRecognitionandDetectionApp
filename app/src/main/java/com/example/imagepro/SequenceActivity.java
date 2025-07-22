package com.example.imagepro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SequenceActivity extends AppCompatActivity {

    private ImageView signImage;
    private Button nextButton, replayButton, slowMotionButton;

    private List<Integer> imageSequence = new ArrayList<>();
    private int currentIndex = 0;
    private Handler handler = new Handler();
    private String selectedWord;
    private int currentScore = 0;  // Track score

    private void showCurrentImage() {
        signImage.setImageResource(imageSequence.get(currentIndex));
    }

    private void replaySequence(final boolean slowMode) {
        currentIndex = 0;
        nextButton.setEnabled(false);
        replayButton.setEnabled(false);
        slowMotionButton.setEnabled(false);

        final int delay = slowMode ? 1500 : 800;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentIndex < imageSequence.size()) {
                    signImage.setImageResource(imageSequence.get(currentIndex));
                    currentIndex++;
                    handler.postDelayed(this, delay);
                } else {
                    currentIndex = imageSequence.size() - 1;
                    nextButton.setEnabled(true);
                    replayButton.setEnabled(true);
                    slowMotionButton.setEnabled(true);
                }
            }
        }, 0);
    }

    private int getDrawableForChar(char c) {
        switch (Character.toLowerCase(c)) {
            case 'a': return R.drawable.a1;
            case 'b': return R.drawable.b1;
            case 'c': return R.drawable.c1;
            case 'd': return R.drawable.d1;
            case 'e': return R.drawable.e1;
            case 'f': return R.drawable.f1;
            case 'g': return R.drawable.g1;
            case 'h': return R.drawable.h1;
            case 'i': return R.drawable.i1;
            case 'j': return R.drawable.j1;
            case 'k': return R.drawable.k1;
            case 'l': return R.drawable.l1;
            case 'm': return R.drawable.m1;
            case 'n': return R.drawable.n1;
            case 'o': return R.drawable.o1;
            case 'p': return R.drawable.p1;
            case 'q': return R.drawable.q1;
            case 'r': return R.drawable.r1;
            case 's': return R.drawable.s1;
            case 't': return R.drawable.t1;
            case 'u': return R.drawable.u1;
            case 'v': return R.drawable.v1;
            case 'w': return R.drawable.w1;
            case 'x': return R.drawable.x1;
            case 'y': return R.drawable.y1;
            case 'z': return R.drawable.z1;
            default: return R.drawable.placeholder;
        }
    }

    private List<Integer> createImageSequenceFromWord(String word) {
        List<Integer> sequence = new ArrayList<>();
        for (char c : word.toCharArray()) {
            sequence.add(getDrawableForChar(c));
        }
        return sequence;
    }

    private String getRandomWord() {
        String[] words = {"hello", "hi", "zup", "peace", "cool", "yes", "no"};
        return words[new Random().nextInt(words.length)];
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence);

        signImage = findViewById(R.id.sign_image);
        nextButton = findViewById(R.id.next_button);
        replayButton = findViewById(R.id.replay_button);
        slowMotionButton = findViewById(R.id.slow_motion_button);

        // Get score from intent (from GuessActivity if returning)
        currentScore = getIntent().getIntExtra("score", 0);

        // Choose a new word and create the image sequence
        selectedWord = getRandomWord();
        imageSequence = createImageSequenceFromWord(selectedWord);

        showCurrentImage();

        replayButton.setOnClickListener(v -> replaySequence(false));
        slowMotionButton.setOnClickListener(v -> replaySequence(true));

        nextButton.setOnClickListener(v -> {
            Intent intent = new Intent(SequenceActivity.this, GuessActivity.class);
            intent.putExtra("selectedWord", selectedWord);
            intent.putExtra("score", currentScore); // pass the score
            startActivity(intent);
            finish(); // finish so user can't return with back button
        });

        // Start animation automatically
        replaySequence(false);
    }
}
