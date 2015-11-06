package group8.mealhelper.models;

import android.content.ContentValues;

import java.io.Serializable;

import group8.mealhelper.database.DbSchema;

/**
 * Created by curtis on 10/16/15.
 */
public class Ingredient implements Serializable{
    private String mIngredientId;
    private String mMealId;
    private String mName;
    private double mAmount;
    private String mUnits;

    public String getMealId() {
        return mMealId;
    }

    public void setMealId(String mealId) {
        mMealId = mealId;
    }

    public String getIngredientId() {
        return mIngredientId;
    }

    public void setIngredientId(String ingredientId) {
        mIngredientId = ingredientId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getAmount() {
        return mAmount;
    }

    public void setAmount(double amount) {
        mAmount = amount;
    }

    public String getUnits() {
        return mUnits;
    }

    public void setUnits(String units) {
        mUnits = units;
    }

    public  Ingredient(){

    }
    public boolean isValid(){
        if (mName!= null && !mName.isEmpty() && mAmount != 0 && mUnits != null && !mUnits.isEmpty()){
        return true;
        }
        return false;
    }
    public ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(DbSchema.IngredientTable.Cols.NAME, getName());
        values.put(DbSchema.IngredientTable.Cols.AMOUNT,getAmount());
        values.put(DbSchema.IngredientTable.Cols.UNIT,getUnits());
        values.put(DbSchema.IngredientTable.Cols.MEAL_ID, getMealId());
        return values;
    }
}
