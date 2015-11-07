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
                + MealTable.Cols.RECIPE + ", "
                + "unique(" + MealTable.Cols.MEAL_ID + "))" ;
        db.execSQL(statement);
        statement = "create table " + IngredientTable.NAME + "( " +IngredientTable.Cols.INGREDIENT_ID + " integer primary key autoincrement, "
                + IngredientTable.Cols.MEAL_ID + ", "
                + IngredientTable.Cols.NAME + ", "
                + IngredientTable.Cols.AMOUNT +", "
                + IngredientTable.Cols.UNIT + ", foreign key(" +IngredientTable.Cols.MEAL_ID +") references " + MealTable.NAME+ "("+MealTable.Cols.MEAL_ID + "), "
                 + "unique(" + IngredientTable.Cols.INGREDIENT_ID + "))";
        db.execSQL(statement);
        statement = "create table " + MenuTable.NAME + "( " + MenuTable.Cols.Menu_ID + " integer primary key autoincrement, "
                + MenuTable.Cols.DAY + ", "
                + MenuTable.Cols.MEAL_TYPE + ", "
                + MenuTable.Cols.MEAL_ID + ", "
                + "unique(" + MenuTable.Cols.Menu_ID + "))";
        db.execSQL(statement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
}
