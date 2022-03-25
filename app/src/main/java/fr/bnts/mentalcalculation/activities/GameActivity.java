package fr.bnts.mentalcalculation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import fr.bnts.mentalcalculation.entities.Calculation;
import fr.bnts.mentalcalculation.R;
import fr.bnts.mentalcalculation.Tools;
import fr.bnts.mentalcalculation.entities.Score;
import fr.bnts.mentalcalculation.services.DatabaseHelper;
import fr.bnts.mentalcalculation.services.ScoresStorage;

public class GameActivity extends AppCompatActivity {

    private Context context;
    private Menu menu;
    private ScoresStorage scoresStorage;

    private int remainingQuestions = 10;
    private int wrongAnswersCredit = 3;
    private int score = 0;

    private Calculation calculation;
    private long userAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();

        this.scoresStorage = new ScoresStorage(new DatabaseHelper(this));

        setContentView(R.layout.activity_game);

        List<Button> buttons = Arrays.asList(
                findViewById(R.id.button0),
                findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3),
                findViewById(R.id.button4),
                findViewById(R.id.button5),
                findViewById(R.id.button6),
                findViewById(R.id.button7),
                findViewById(R.id.button8),
                findViewById(R.id.button9)
        );

        for (Button button: buttons) {
            button.setOnClickListener(view -> incrementUserAnswer(button));
        }

        Button deleteLastCharacterButton = findViewById(R.id.button_delete);
        deleteLastCharacterButton.setOnClickListener(view -> decrementUserAnswer());

        Button validateButton = findViewById(R.id.button_validate);
        validateButton.setOnClickListener(view -> validateAnswer());

        this.calculation = new Calculation(true);
        updateQuestionTextView();
    }

    private void updateQuestionTextView() {
        TextView question = findViewById(R.id.textView_question);
        question.setText(this.calculation.serialize());
    }

    private void updateUserAnswerTextView() {
        TextView answer = findViewById(R.id.textView_answer);

        if (this.userAnswer > 0) {
            String serializedAnswer = Long.toString(this.userAnswer);
            answer.setText(serializedAnswer);
        } else {
            answer.setText("");
        }
    }

    private void incrementUserAnswer(Button pressedButton) {
        int pressedNumber = Integer.parseInt((String) pressedButton.getText());
        this.userAnswer = this.userAnswer * 10 + pressedNumber;
        updateUserAnswerTextView();
    }

    private void decrementUserAnswer() {
        if (this.userAnswer >= 10) {
            this.userAnswer /= 10;
            updateUserAnswerTextView();
        } else if (this.userAnswer > 0) {
            this.userAnswer = 0;
            updateUserAnswerTextView();
        } else {
            Toast toast = Toast.makeText(context, R.string.unauthorized_action, 3 * 1000);
            toast.show();
        }
    }

    private void displayLives() {
        String livesHint = getString(R.string.lives_interrogation);
        String livesText = Tools.replaceInterrogation(livesHint, this.wrongAnswersCredit);

        MenuItem livesMenu = this.menu.findItem(R.id.menuItem_lives);
        livesMenu.setTitle(livesText);
    }

    private void decrementLives() {
        this.wrongAnswersCredit--;
        displayLives();
    }

    private void displayRemainingQuestions() {
        String remainingQuestionsHint = getString(R.string.questions_interrogation);
        String remainingQuestionText = Tools.replaceInterrogation(remainingQuestionsHint, this.remainingQuestions);

        MenuItem remainingQuestionsMenu = this.menu.findItem(R.id.menuItem_remaining_questions);
        remainingQuestionsMenu.setTitle(remainingQuestionText);
    }

    private void decrementRemainingQuestions() {
        this.remainingQuestions--;
        displayRemainingQuestions();
    }

    private void newQuestion() {
        this.calculation = new Calculation(true);
        updateQuestionTextView();

        this.userAnswer = 0;
        updateUserAnswerTextView();

        decrementRemainingQuestions();
    }

    private void openScoresActivity() {
        Intent intent = new Intent(this, ScoresActivity.class);
        startActivity(intent);
    }

    private void saveScore() {
        String pattern = "yyyy-MM-dd HH:mm:ss.SSS";

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime now = LocalDateTime.now();

        String date = dtf.format(now);

        String nickname = getIntent().getStringExtra("NICKNAME");
        Score score = new Score(nickname, this.score, this.wrongAnswersCredit, date);
        scoresStorage.add(score);
    }

    private void endGame() {
        this.saveScore();

        openScoresActivity();
        finish();
    }

    private void validateAnswer() {
        if (this.userAnswer == this.calculation.getResult()) {
            Toast toast = Toast.makeText(context, R.string.correct_answer, 3 * 1000);
            toast.show();

            this.score++;
        } else {
            Toast toast = Toast.makeText(context, R.string.wrong_answer, 3 * 1000);
            toast.show();

            decrementLives();

            if (this.wrongAnswersCredit == 0) {
                endGame();
            }
        }

        if (this.remainingQuestions > 1) {
            newQuestion();
        } else {
            endGame();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);

        displayLives();
        displayRemainingQuestions();

        return super.onCreateOptionsMenu(menu);
    }

}