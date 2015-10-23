package group8.mealhelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import group8.mealhelper.database.DbSchema.IngredientTable;
import group8.mealhelper.database.DbSchema.MealTable;
import group8.mealhelper.database.DbSchema.UnitTable;

import static group8.mealhelper.database.DbSchema.*;

/**
 * Created by curtis on 10/16/15.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "mealHelper.db";

    public  DbHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String statement = "create table " + MealTable.NAME + "( _id integer primary key autoincrement, " + MealTable.Cols.MEAL_ID + ", " + MealTable.Cols.NAME + ", " + MealTable.Cols.PIC_PATH + ")" ;
        db.execSQL(statement);
        statement = "create table " + UnitTable.NAME + "( _id integer primary key autoincrement, " + UnitTable.Cols.UNIT_ID + ", " + UnitTable.Cols.NAME + ")";
        db.execSQL(statement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
