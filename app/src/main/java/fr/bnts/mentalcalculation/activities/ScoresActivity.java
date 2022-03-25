package fr.bnts.mentalcalculation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import fr.bnts.mentalcalculation.R;
import fr.bnts.mentalcalculation.entities.Score;
import fr.bnts.mentalcalculation.services.DatabaseHelper;
import fr.bnts.mentalcalculation.services.ScoresStorage;

public class ScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scores);

        ScoresStorage scoresStorage = new ScoresStorage(new DatabaseHelper(this));

        if (scoresStorage.getCount() > 0) {
            List<Score> scores = scoresStorage.getAll();
            displayScores(scores);
        }
    }

    private void displayScores(List<Score> scores) {
        TableLayout table = findViewById(R.id.tableLayout_scores);

        TableRow hint = findViewById(R.id.tableRow_hint);
        table.removeView(hint);

        for (Score score : scores) {
            TableRow row = new TableRow(this);

            TextView nickname = new TextView(this);
            nickname.setText(score.getNickname());
            row.addView(nickname);

            TextView scoreValue = new TextView(this);
            scoreValue.setText(String.valueOf(score.getScore()));
            row.addView(scoreValue);

            TextView remainingLives = new TextView(this);
            remainingLives.setText(String.valueOf(score.getRemainingLives()));
            row.addView(remainingLives);

            table.addView(row);
        }
    }
}