package com.example.imagepro;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {
    static {
        if (OpenCVLoader.initDebug()) {
            Log.d("MainActivity: ", "Opencv is loaded");
        } else {
            Log.d("MainActivity: ", "Opencv failed to load");
        }
    }

    private Button camera_button;
    private Button combine_letter_button;
    private Button about_app_button;
    private ImageView markImage, winmarkImage; // Developer images
    private Button users_manual_button; // ASL Dictionary button
    private Button fingerspelling_game_button; // Fingerspelling Game button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        combine_letter_button = findViewById(R.id.combine_letter_button);
        combine_letter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CombineLetterActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        // About the App Button
        about_app_button = findViewById(R.id.about_app_button);
        about_app_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutAppActivity.class));
            }
        });

        users_manual_button = findViewById(R.id.users_manual_button);
        users_manual_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ASLDictionaryActivity.class));
            }
        });

        fingerspelling_game_button = findViewById(R.id.fingerspelling_game_button);
        fingerspelling_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FingerspellingGameActivity.class));
            }
        });


        // Initialize developer image views
        markImage = findViewById(R.id.mark_image);
        winmarkImage = findViewById(R.id.winmark_image);

        // Click events to show developer info
        markImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MarkInfoActivity.class));
            }
        });

        winmarkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WinmarkInfoActivity.class));
            }
        });
    }
}
