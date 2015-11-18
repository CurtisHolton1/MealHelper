package group8.mealhelper.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import group8.mealhelper.database.DbHelper;
import group8.mealhelper.database.DbSchema;

/**
 * Created by thomasdail on 11/8/15.
 */
public class IngredientItem {
    private int mId;
    private String mName;
    private double mAmount;
    private String mUnit;
    private boolean mBought;

    public IngredientItem(int id, String name, double amount, String unit, boolean bought) {
        mId = id;
        mName = name;
        mAmount = amount;
        mUnit = unit;
        mBought = bought;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }

    public void incrementAmount(double d) {
        mAmount += d;
    }

    public String getName() {
        return mName;
    }

    public String getUnits() {
        return mUnit;
    }

    public boolean isBought() {
        return mBought;
    }

    public double getAmount() {
        return mAmount;
    }

    public void toggleBought(Context context) {
        Context c = context.getApplicationContext();
        SQLiteDatabase database = new DbHelper(c).getWritableDatabase();

        ContentValues values;

        if (mBought == false) {
            values = getContentValues("TRUE");
        } else {
            values = getContentValues("FALSE");
        }

        database.update(DbSchema.ShoppingListTable.NAME, values,
                DbSchema.ShoppingListTable.Cols.SHOPPINGLIST_ID + " = ?",
                new String[]{Integer.toString(mId)});
    }

    private ContentValues getContentValues(String s) {
        ContentValues values = new ContentValues();
        values.put(DbSchema.ShoppingListTable.Cols.SHOPPINGLIST_ID, mId);
        values.put(DbSchema.ShoppingListTable.Cols.NAME, mName);
        values.put(DbSchema.ShoppingListTable.Cols.AMOUNT, mAmount);
        values.put(DbSchema.ShoppingListTable.Cols.UNIT, mUnit);
        values.put(DbSchema.ShoppingListTable.Cols.BOUGHT, s);
        return values;
    }
}
