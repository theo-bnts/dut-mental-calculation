package fr.bnts.mentalcalculation.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fr.bnts.mentalcalculation.BuildConfig;
import fr.bnts.mentalcalculation.R;
import fr.bnts.mentalcalculation.Tools;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Tools.replaceTextViewHint(this, R.id.textView_application_id, BuildConfig.APPLICATION_ID);
        Tools.replaceTextViewHint(this, R.id.textView_application_version, BuildConfig.VERSION_NAME);
    }
}