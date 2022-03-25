package fr.bnts.mentalcalculation.services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.bnts.mentalcalculation.entities.Score;

public class ScoresStorage {

    private final SQLiteDatabase database;

    public ScoresStorage(DatabaseHelper helper) {
        this.database = helper.getReadableDatabase();
    }

    public void add(Score score) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.nicknameKey, score.getNickname());
        values.put(DatabaseHelper.scoreKey, score.getScore());
        values.put(DatabaseHelper.remainingLivesKey, score.getRemainingLives());
        values.put(DatabaseHelper.dateKey, score.getDate());

        database.insert(DatabaseHelper.tableName, null, values);
    }

    public List<Score> getAll() {
        Cursor cursor = database.query(
                DatabaseHelper.tableName,
                new String[] {
                        DatabaseHelper.nicknameKey,
                        DatabaseHelper.scoreKey,
                        DatabaseHelper.remainingLivesKey,
                        DatabaseHelper.dateKey
                },
                null,
                null,
                null,
                null,
                null
        );

        List<Score> scores = new ArrayList<>();

        int nicknameIndex = cursor.getColumnIndex(DatabaseHelper.nicknameKey);
        int scoreIndex = cursor.getColumnIndex(DatabaseHelper.scoreKey);
        int remainingLivesIndex = cursor.getColumnIndex(DatabaseHelper.remainingLivesKey);
        int dateIndex = cursor.getColumnIndex(DatabaseHelper.dateKey);

        while (cursor.moveToNext()) {
            scores.add(new Score(
                    cursor.getString(nicknameIndex),
                    cursor.getInt(scoreIndex),
                    cursor.getInt(remainingLivesIndex),
                    cursor.getString(dateIndex)
            ));
        }

        cursor.close();
        return scores;
    }

    public int getCount() {
        Cursor cursor = database.query(
                DatabaseHelper.tableName,
                new String[] { "COUNT(*)" },
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

}