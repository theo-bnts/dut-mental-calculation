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

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import fr.bnts.mentalcalculation.model.Operation;
import fr.bnts.mentalcalculation.R;
import fr.bnts.mentalcalculation.Tools;

public class GameActivity extends AppCompatActivity {

    private Context context;
    private Menu menu;

    private int remainingQuestions = 10;
    private int score = 0;
    private int wrongAnswersCredit = 3;

    private long answer = 0;
    private float correctAnswer;
    private String serializedCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = getApplicationContext();

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
            button.setOnClickListener(view -> incrementAnswer(button));
        }

        Button deleteLastCharacterButton = findViewById(R.id.button_delete);
        deleteLastCharacterButton.setOnClickListener(view -> decrementAnswer());

        Button validateButton = findViewById(R.id.button_validate);
        validateButton.setOnClickListener(view -> validateAnswer());

        createCalculationWithPositiveIntegerResult();
        updateQuestionTextView(this.serializedCalculation);
    }

    private int getRandom() {
        int randomMinimumValue = 2;
        int randomMaximumValue = 10;

        return ThreadLocalRandom.current().nextInt(randomMinimumValue, randomMaximumValue + 1);
    }

    private float computeCalculation(int firstInteger, int secondInteger, Operation operationType) {
        switch (operationType) {
            case ADD:
                return firstInteger + secondInteger;
            case SUBSTRACT:
                return firstInteger - secondInteger;
            case MULTIPLY:
                return firstInteger * secondInteger;
            case DIVIDE:
                return (float)firstInteger / secondInteger;
            default:
                return -1;
        }
    }

    private void updateQuestionTextView(String value) {
        TextView question = findViewById(R.id.textView_question);
        question.setText(value);
    }

    private void createCalculation() {
        int firstRandom = getRandom();
        int secondRandom = getRandom();

        String serializedFirstRandom = Integer.toString(firstRandom);
        String serializedSecondRandom = Integer.toString(secondRandom);

        Operation[] operationTypes = { Operation.ADD, Operation.SUBSTRACT, Operation.MULTIPLY, Operation.DIVIDE };

        int randomIndex = new Random().nextInt(operationTypes.length);
        Operation operationType = operationTypes[randomIndex];

        this.correctAnswer = computeCalculation(firstRandom, secondRandom, operationType);

        this.serializedCalculation = serializedFirstRandom +  ' ' + operationType.getSymbol() + ' ' + serializedSecondRandom;
    }

    private void createCalculationWithPositiveIntegerResult() {
        do {
            createCalculation();
        } while (!Tools.isInteger(this.correctAnswer) || this.correctAnswer < 0);
    }

    private void updateAnswerTextView(long value) {
        TextView answer = findViewById(R.id.textView_answer);

        if (value > 0) {
            String serializedAnswer = Long.toString(value);
            answer.setText(serializedAnswer);
        } else {
            answer.setText("");
        }
    }

    private void incrementAnswer(Button pressedButton) {
        int pressedNumber = Integer.parseInt((String) pressedButton.getText());
        this.answer = this.answer * 10 + pressedNumber;
        updateAnswerTextView(this.answer);
    }

    private void decrementAnswer() {
        if (this.answer >= 10) {
            this.answer /= 10;
            updateAnswerTextView(this.answer);
        } else if (this.answer > 0) {
            this.answer = 0;
            updateAnswerTextView(this.answer);
        } else {
            Toast toast = Toast.makeText(context, R.string.unauthorized_action, 3 * 1000);
            toast.show();
        }
    }

    private void displayLives() {
        String livesHint = getString(R.string.lives);
        String livesText = Tools.replaceInterrogation(livesHint, this.wrongAnswersCredit);

        MenuItem livesMenu = this.menu.findItem(R.id.menuItem_lives);
        livesMenu.setTitle(livesText);
    }

    private void decrementLives() {
        this.wrongAnswersCredit--;
        displayLives();
    }

    private void displayRemainingQuestions() {
        String remainingQuestionsHint = getString(R.string.questions);
        String remainingQuestionText = Tools.replaceInterrogation(remainingQuestionsHint, this.remainingQuestions);

        MenuItem remainingQuestionsMenu = this.menu.findItem(R.id.menuItem_remaining_questions);
        remainingQuestionsMenu.setTitle(remainingQuestionText);
    }

    private void decrementRemainingQuestions() {
        this.remainingQuestions--;
        displayRemainingQuestions();
    }

    private void newQuestion() {
        createCalculationWithPositiveIntegerResult();
        updateQuestionTextView(this.serializedCalculation);

        this.answer = 0;
        updateAnswerTextView(this.answer);

        decrementRemainingQuestions();
    }

    private void openScoresActivity() {
        Intent intent = new Intent(this, ScoresActivity.class);
        startActivity(intent);
    }

    private void endGame() {
        // TODO: Save scores

        finish();
        openScoresActivity();
    }

    private void validateAnswer() {
        if (this.answer == this.correctAnswer) {
            Toast toast = Toast.makeText(context, R.string.correct_answer, 3 * 1000);
            toast.show();

            this.score++;
        } else {
            Toast toast = Toast.makeText(context, R.string.wrong_answer, 3 * 1000);
            toast.show();

            if (this.wrongAnswersCredit > 1) {
                decrementLives();
            } else {
                endGame();
            }
        }

        if (this.remainingQuestions > 0) {
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