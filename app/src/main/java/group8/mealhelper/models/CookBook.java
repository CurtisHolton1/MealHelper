package group8.mealhelper.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        mDatabase = new DbHelper(mContext).getWritableDatabase();
    }
    public List<Meal> getAllMeals(){
        Cursor c = queryMeals(null,null);
        List<Meal> mealList = new ArrayList<Meal>();
        if(c.getCount()>0) {
            c.moveToFirst();
            mealList.add(getMealWithCursor(c));
            while(!c.isLast()) {
                c.moveToNext();
                mealList.add(getMealWithCursor(c));
            }
        }
        c.close();
        return mealList;
    }

    private Meal getMealWithCursor(Cursor c){
        Meal meal = new Meal();
        meal.setId(c.getString(c.getColumnIndex(DbSchema.MealTable.Cols.MEAL_ID)));
        meal.setName(c.getString(c.getColumnIndex(DbSchema.MealTable.Cols.NAME)));
        meal.setRecipe(c.getString(c.getColumnIndex(DbSchema.MealTable.Cols.RECIPE)));
        meal.setPathToPic(c.getString(c.getColumnIndex(DbSchema.MealTable.Cols.PIC_PATH)));
        return meal;
    }

    private Meal getMealWithId(String id){
        Meal meal = new Meal();
        Cursor c = mDatabase.rawQuery("select * from " + DbSchema.MealTable.NAME + " where " + DbSchema.MealTable.Cols.MEAL_ID + " = " + id,null);
        if(c.getCount() >0) {
            c.moveToFirst();
            meal.setId(c.getString(c.getColumnIndex(DbSchema.MealTable.Cols.MEAL_ID)));
            meal.setName(c.getString(c.getColumnIndex(DbSchema.MealTable.Cols.NAME)));
            meal.setRecipe(c.getString(c.getColumnIndex(DbSchema.MealTable.Cols.RECIPE)));
            meal.setPathToPic(c.getString(c.getColumnIndex(DbSchema.MealTable.Cols.PIC_PATH)));
        }
        c.close();
        return meal;
    }

    public Void deleteMeal(String id) {
        Meal meal = getMealWithId(id);
        File file = new File(meal.getPathToPic());
        if(file.exists())
            file.delete();
        mDatabase.delete(DbSchema.MealTable.NAME, DbSchema.MealTable.Cols.MEAL_ID + " = " + id, null);
        return null;
    }
    public Cursor queryMeals(String whereClause, String [] whereArgs){
    Cursor cursor = mDatabase.query(DbSchema.MealTable.NAME,null,whereClause,whereArgs,null,null,null);
        return cursor;
    }


}
