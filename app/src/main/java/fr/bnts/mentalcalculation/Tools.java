package fr.bnts.mentalcalculation;

import android.app.Activity;
import android.widget.TextView;

public class Tools {
    public static void replaceTextViewHint(Activity activity, int textViewId, String value) {
        final String replaceRegex = "[?]+";

        TextView textView = activity.findViewById(textViewId);
        String textViewHint = (String) textView.getHint();
        String textViewText = textViewHint.replaceFirst(replaceRegex, value);
        textView.setText(textViewText);
    }
}
