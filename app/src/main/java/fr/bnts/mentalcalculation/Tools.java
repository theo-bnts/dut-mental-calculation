package fr.bnts.mentalcalculation;

import android.app.Activity;
import android.widget.TextView;

public class Tools {
    public static void replaceTextViewHint(Activity activity, int textViewId, String value) {
        TextView textView = activity.findViewById(textViewId);
        String textViewHint = (String) textView.getHint();
        String textViewText = replaceInterrogation(textViewHint, value);
        textView.setText(textViewText);
    }

    public static String replaceInterrogation(String hint, String value) {
        final String replaceRegex = "[?]+";
        return hint.replaceFirst(replaceRegex, value);
    }

    public static String replaceInterrogation(String hint, int value) {
        return replaceInterrogation(hint, Integer.toString(value));
    }
}
