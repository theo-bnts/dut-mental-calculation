package fr.bnts.mentalcalculation.database;

import android.content.Context;

public class ComputeBaseHelper extends DataBaseHelper {


    public ComputeBaseHelper(Context context) {
        super(context, "Calcul", 1);
    }

    @Override
    protected String getCreationSql() {
        return "CREATE TABLE IF NOT EXISTS historique ("+
                "id" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                CalculDao.clePremierElement + " INTEGER NOT NULL," +
                CalculDao.cleDeuxiemeElement + " INTEGER NOT NULL," +
                CalculDao.cleSymbol + " VARCHAR(255) NOT NULL," +
                CalculDao.cleResultat + " INTEGER NOT NULL" +
                ")";
    }

    @Override
    protected String getDeleteSql() {
        return null;
    }
}
