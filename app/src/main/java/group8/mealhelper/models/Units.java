package group8.mealhelper.models;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import group8.mealhelper.database.DbHelper;

/**
 * Created by curtis on 10/23/15.
 */
public class Units {
    private static Units sUnits;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static Units get(Context context) {
        if (sUnits == null) {
            sUnits = new Units(context);
        }
        return sUnits;
    }

    private Units(Context context) {
        context.getApplicationContext();
        mDatabase = new DbHelper(mContext).getWritableDatabase();

    }
}

