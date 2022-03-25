package fr.bnts.mentalcalculation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import fr.bnts.mentalcalculation.R;

public class NicknameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContentView(R.layout.activity_nickname);

        TextInputEditText nicknameInput = findViewById(R.id.textInput_nickname);
        nicknameInput.setOnEditorActionListener((v, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                doneButtonPressed(nicknameInput);
                return true;
            }
            return false;
        });
    }


    private void doneButtonPressed(TextInputEditText nicknameInput) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("NICKNAME", Objects.requireNonNull(nicknameInput.getText()).toString());
        startActivity(intent);

        finish();
    }
}