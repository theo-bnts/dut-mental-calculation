package fr.bnts.mentalcalculation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Tools.replaceTextViewHint(this, R.id.textView_application_id, BuildConfig.APPLICATION_ID);
        Tools.replaceTextViewHint(this, R.id.textView_application_version, BuildConfig.VERSION_NAME);
    }
}