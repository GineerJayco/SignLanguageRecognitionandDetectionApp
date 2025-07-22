package com.example.imagepro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FingerspellingGameActivity extends AppCompatActivity {

    private Button startGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerspelling_game);

        startGameButton = findViewById(R.id.start_game_button);
        startGameButton.setOnClickListener(v -> {
            // Start the game sequence activity when tapped
            Intent intent = new Intent(FingerspellingGameActivity.this, SequenceActivity.class);
            startActivity(intent);
        });
    }
}

