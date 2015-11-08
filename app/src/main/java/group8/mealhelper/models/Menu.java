package group8.mealhelper.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.MathContext;

import group8.mealhelper.database.DbHelper;
import group8.mealhelper.database.DbSchema;

/**
 * Created by curtis on 11/7/15.
 */
public class Menu {
    private static Menu sMenu;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static Menu get(Context context){
        if(sMenu == null){
            sMenu = new Menu(context);
        }
        return sMenu;
    }

    private Menu (Context context){
        mContext = context.getApplicationContext();
        mDatabase = new DbHelper(mContext).getWritableDatabase();
    }

    public Day getDay(String dayName){
        Cursor cursor = mDatabase.rawQuery("select * from " + DbSchema.MenuTable.NAME + " where " + DbSchema.MenuTable.Cols.DAY + " = " + "\"" + dayName + "\"",null);
        if(cursor.getCount()>0) {
            cursor.moveToFirst();
            Day day = getDayWithCursor(cursor);
            return day;
        }
        return null;
    }

    private Day getDayWithCursor(Cursor c){
        Day day = new Day();
        day.setName(c.getString(c.getColumnIndex(DbSchema.MenuTable.Cols.DAY)));
        day.setId(c.getString(c.getColumnIndex(DbSchema.MenuTable.Cols.Menu_ID)));
        CookBook cookBook = CookBook.get(mContext);
        String potentialId = c.getString(c.getColumnIndex(DbSchema.MenuTable.Cols.BREAKFAST));
        Meal meal = cookBook.getMealWithId(potentialId);
        if(meal !=null){
            day.setBreakfast(meal);
        }
        potentialId = c.getString(c.getColumnIndex(DbSchema.MenuTable.Cols.LUNCH));
        meal = cookBook.getMealWithId(potentialId);
        if(meal !=null){
            day.setLunch(meal);
        }
        potentialId = c.getString(c.getColumnIndex(DbSchema.MenuTable.Cols.DINNER));
        meal = cookBook.getMealWithId(potentialId);
        if(meal !=null){
            day.setDinner(meal);
        }
        return day;
    }
}
