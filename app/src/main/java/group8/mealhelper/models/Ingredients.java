package group8.mealhelper.models;

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

        queryIngredients("breakfast");
        queryIngredients("lunch");
        queryIngredients("dinner");
    }

    private void queryIngredients(String meal) {
        String q = "SELECT ingredients.name, ingredients.unit, ingredients.amount " +
                "FROM ingredients, meals, menu " +
                "WHERE ingredients.mealId = meals.id AND meals.id = menu."+meal+"Id";
        Cursor cursor = mDatabase.rawQuery(q, null);

        Ingredient i;
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(
                        cursor.getColumnIndex(DbSchema.IngredientTable.Cols.NAME));
                String amount = cursor.getString(
                        cursor.getColumnIndex(DbSchema.IngredientTable.Cols.AMOUNT));
                String unit = cursor.getString(
                        cursor.getColumnIndex(DbSchema.IngredientTable.Cols.UNIT));
                i = new Ingredient();
                i.setName(name);
                i.setAmount(Integer.parseInt(amount));
                i.setUnits(unit);
                IngredientItem a = new IngredientItem(i);
                int index = indexOfIngredient(mIngredients, a);
                if (index >= 0){
                    IngredientItem duplicate = mIngredients.get(index);
                    duplicate.incrementAmount(a.getAmount());
                } else {
                    mIngredients.add(a);
                }
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
    }

    private static int indexOfIngredient(List<IngredientItem> l, IngredientItem i) {
        int index = -1;
        for (IngredientItem next : l) {
            if (next.getName().equals(i.getName())) {
                index = l.indexOf(next);
            }
        }
        return index;
    }
}
