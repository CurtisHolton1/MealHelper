package group8.mealhelper.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import group8.mealhelper.database.DbSchema.IngredientTable;
import group8.mealhelper.database.DbSchema.MealTable;

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
        String statement = "create table " + MealTable.NAME + "( " + MealTable.Cols.MEAL_ID + " integer primary key autoincrement, "
                + MealTable.Cols.NAME + ", "
                + MealTable.Cols.PIC_PATH + ", "
                + MealTable.Cols.RECIPE + ")" ;
        db.execSQL(statement);
        statement = "create table " + IngredientTable.NAME + "( id integer primary key autoincrement, "
                + IngredientTable.Cols.MEAL_ID + ", "
                + IngredientTable.Cols.NAME + ", "
                + IngredientTable.Cols.AMOUNT +", "
                + IngredientTable.Cols.UNIT + ")";
        db.execSQL(statement);
        statement = "create table " + MenuTable.NAME + "( id integer primary key autoincrement, "
                + MenuTable.Cols.MEAL + ", "
                + MenuTable.Cols.DAY + ")";
        db.execSQL(statement);
//        statement = "create table " + UnitTable.NAME + "( id integer primary key autoincrement, "
//                + UnitTable.Cols.UNIT_ID + ", "
//                + UnitTable.Cols.NAME + ")";
//        db.execSQL(statement);
//        initUnitTable(db);
    }

//    private void initUnitTable(SQLiteDatabase db){
//        ContentValues values = new ContentValues();
//        values.put(UnitTable.Cols.NAME, "Cup(s)");
//        db.insert(UnitTable.NAME, null, values);
//        values.put(UnitTable.Cols.NAME, "tsp(s)");
//        db.insert(UnitTable.NAME, null, values);
//        values.put(UnitTable.Cols.NAME, "Tbsp(s)");
//        db.insert(UnitTable.NAME, null, values);
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
