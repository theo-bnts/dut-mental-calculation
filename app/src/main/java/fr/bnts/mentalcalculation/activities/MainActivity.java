package fr.bnts.mentalcalculation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import fr.bnts.mentalcalculation.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.button_play);
        playButton.setOnClickListener(view -> openGameActivity());

        Button scoresButton = findViewById(R.id.button_scores);
        scoresButton.setOnClickListener(view -> openScoresActivity());

        Button aboutButton = findViewById(R.id.button_about);
        aboutButton.setOnClickListener(view -> openAboutActivity());
    }

    private void openGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    private void openScoresActivity() {
        Intent intent = new Intent(this, ScoresActivity.class);
        startActivity(intent);
    }

    private void openAboutActivity() {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

}