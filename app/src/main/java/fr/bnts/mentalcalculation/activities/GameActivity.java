package fr.bnts.mentalcalculation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import fr.bnts.mentalcalculation.Operation;
import fr.bnts.mentalcalculation.R;

public class GameActivity extends AppCompatActivity {

    private Context context;
    private long answer = 0;
    private int correctAnswer;
    private int score = 0;
    private int wrongAnswerCount = 0;

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
            button.setOnClickListener(view -> incrementValue(button));
        }

        Button deleteLastCharacterButton = findViewById(R.id.button_delete);
        deleteLastCharacterButton.setOnClickListener(view -> decrementValue());

        Button validateButton = findViewById(R.id.button_validate);
        validateButton.setOnClickListener(view -> validateAnswer());

        String calculation = createCalculation();
        updateQuestion(calculation);
    }

    private int getRandom() {
        int randomMinimumValue = 2;
        int randomMaximumValue = 10;

        return ThreadLocalRandom.current().nextInt(randomMinimumValue, randomMaximumValue + 1);
    }

    private int computeCalculation(int firstInteger, int secondInteger, Operation operationType) {
        switch (operationType) {
            case ADD:
                return firstInteger + secondInteger;
            case SUBSTRACT:
                return firstInteger - secondInteger;
            case MULTIPLY:
                return firstInteger * secondInteger;
            case DIVIDE:
                return firstInteger / secondInteger;
            default:
                return -1;
        }
    }

    private void updateQuestion(String value) {
        TextView question = findViewById(R.id.textView_question);
        question.setText(value);
    }

    private String createCalculation() {
        int firstRandom = getRandom();
        int secondRandom = getRandom();

        String serializedFirstRandom = Integer.toString(firstRandom);
        String serializedSecondRandom = Integer.toString(secondRandom);

        Operation[] operationTypes = { Operation.ADD, Operation.SUBSTRACT, Operation.MULTIPLY, Operation.DIVIDE };

        int randomIndex = new Random().nextInt(operationTypes.length);
        Operation operationType = operationTypes[randomIndex];

        this.correctAnswer = computeCalculation(firstRandom, secondRandom, operationType);

        return serializedFirstRandom +  ' ' + operationType.getSymbol() + ' ' + serializedSecondRandom;
    }

    private void updateAnswer(long value) {
        TextView answer = findViewById(R.id.textView_answer);

        if (this.answer > 0) {
            String serializedAnswer = Long.toString(value);
            answer.setText(serializedAnswer);
        } else {
            answer.setText("");
        }
    }

    private void incrementValue(Button pressedButton) {
        int pressedNumber = Integer.parseInt((String) pressedButton.getText());
        this.answer = this.answer * 10 + pressedNumber;
        updateAnswer(this.answer);
    }

    private void decrementValue() {
        if (this.answer >= 10) {
            this.answer /= 10;
            updateAnswer(this.answer);
        } else if (this.answer > 0) {
            this.answer = 0;
            updateAnswer(this.answer);
        } else {
            Toast toast = Toast.makeText(context, R.string.unauthorized_action, 3 * 1000);
            toast.show();
        }
    }

    private void validateAnswer() {
        if (this.answer == this.correctAnswer) {
            Toast toast = Toast.makeText(context, R.string.correct_answer, 3 * 1000);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context, R.string.wrong_answer, 3 * 1000);
            toast.show();
        }

        String calculation = createCalculation();
        updateQuestion(calculation);

        this.answer = 0;
        updateAnswer(this.answer);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);

        MenuItem emptyMenuItem = menu.findItem(R.id.menuItem_empty);
        emptyMenuItem.setOnMenuItemClickListener(menuItem -> emptyComputeTextView());

        MenuItem computeMenuItem = menu.findItem(R.id.menuItem_compute);
        computeMenuItem.setOnMenuItemClickListener(menuItem -> compute());

        return super.onCreateOptionsMenu(menu);
    }

    private boolean compute() {
        String numbersRegex = "([0-9]+)";
        String symbolsRegex = "([^0-9]+)";

        String currentText = this.getCurrentText();

        if (Tools.endsWithNumber(currentText)) {
            Pattern numbersPattern = Pattern.compile(numbersRegex);
            Matcher numbersMatcher = numbersPattern.matcher(currentText);

            Pattern symbolsPattern = Pattern.compile(symbolsRegex);
            Matcher symbolsMatcher = symbolsPattern.matcher(currentText);

            numbersMatcher.find();
            String firstNumberGroup = numbersMatcher.group();
            long result = Integer.parseInt(firstNumberGroup);

            while (numbersMatcher.find() && symbolsMatcher.find()) {
                String numberGroup = numbersMatcher.group();
                int number = Integer.parseInt(numberGroup);

                String symbolGroup = symbolsMatcher.group();
                char symbol = symbolGroup.charAt(0);

                switch (symbol) {
                    case '+':
                        result += number;
                        break;
                    case '-':
                        result -= number;
                        break;
                    case 'x':
                        result *= number;
                        break;
                    case '/':
                        result /= number;
                        break;
                }
            }

            Intent intent = new Intent(this, ResultsActivity.class);
            intent.putExtra("compute_result", result);
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, R.string.unauthorized_action, 3 * 1000);
            toast.show();
        }

        return true;
    }
    */
}