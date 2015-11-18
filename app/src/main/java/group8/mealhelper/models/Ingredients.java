package group8.mealhelper.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import group8.mealhelper.database.DbHelper;
import group8.mealhelper.database.DbSchema;

/**
 * Created by thomasdail on 11/8/15.
 */
public class Ingredients {
    private List<IngredientItem> mIngredients;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public List<IngredientItem> getIngredients() {
        return mIngredients;
    }

    public Ingredients(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DbHelper(mContext)
                .getWritableDatabase();
        mIngredients = new ArrayList<IngredientItem>();

        queryShoppingList();
    }

    private void queryShoppingList() {

        String q = "SELECT * From " + DbSchema.ShoppingListTable.NAME;
        Cursor cursor = mDatabase.rawQuery(q, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(
                        DbSchema.ShoppingListTable.Cols.SHOPPINGLIST_ID));
                String name = cursor.getString(
                        cursor.getColumnIndex(DbSchema.ShoppingListTable.Cols.NAME));
                double amount = cursor.getDouble(
                        cursor.getColumnIndex(DbSchema.ShoppingListTable.Cols.AMOUNT));
                String unit = cursor.getString(
                        cursor.getColumnIndex(DbSchema.ShoppingListTable.Cols.UNIT));
                String bought = cursor.getString(
                        cursor.getColumnIndex(DbSchema.ShoppingListTable.Cols.BOUGHT));
                boolean b;
                if (bought.equals("TRUE")) {
                    b = true;
                } else {
                    b = false;
                }
                IngredientItem i = new IngredientItem(id, name, amount, unit, b);

                mIngredients.add(i);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
    }
}
