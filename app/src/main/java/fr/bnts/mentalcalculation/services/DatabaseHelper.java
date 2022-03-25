package fr.bnts.mentalcalculation.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "mentalCalculation.db";

    public static final String tableName = "scores";
    public static final String nicknameKey = "nickname";
    public static final String scoreKey = "score";
    public static final String remainingLivesKey = "remaining_lives";
    public static final String dateKey = "date";

    public static final String createTableQuery = "CREATE TABLE IF NOT EXISTS `" + tableName + "` ("
            + "`id` INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "`" + nicknameKey + "` TEXT NOT NULL,"
            + "`" + scoreKey + "` INTEGER NOT NULL,"
            + "`" + remainingLivesKey + "` INTEGER NOT NULL,"
            + "`" + dateKey + "` TEXT NOT NULL"
            + ");";
    public static final String dropTableQuery = "DROP TABLE IF EXISTS `" + tableName + "`;";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(dropTableQuery);
        onCreate(database);
    }

    @Override
    public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        onUpgrade(database, oldVersion, newVersion);
    }

}