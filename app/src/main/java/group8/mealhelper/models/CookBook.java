package group8.mealhelper.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import group8.mealhelper.database.DbHelper;
import group8.mealhelper.database.DbSchema;

/**
 * Created by curtis on 10/16/15.
 */
public class CookBook {
    private static CookBook sCookBook;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public  static CookBook get(Context context){
        if(sCookBook == null) {
            sCookBook = new CookBook(context);
        }
        return  sCookBook;
    }

    private CookBook(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new DbHelper(context).getWritableDatabase();
    }

    public void addMeal(Meal m){

    }

    private Cursor queryMeals(String whereClause, String [] whereArgs){
    Cursor cursor = mDatabase.query(DbSchema.MealTable.NAME,null,whereClause,whereArgs,null,null,null);
    return cursor;
    }
}
