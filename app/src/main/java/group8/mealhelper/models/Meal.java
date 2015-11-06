package group8.mealhelper.models;
import android.content.ContentValues;

import java.io.Serializable;
import java.util.List;

import group8.mealhelper.database.DbSchema;

/**
 * Created by curtis on 10/16/15.
 */
public class Meal implements Serializable {
    private String mId;
    private String mName;
    private String mPathToPic;
    private List<Ingredient> mIngredientList;
    private String mRecipe;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPathToPic() {
        return mPathToPic;
    }

    public void setPathToPic(String pathToPic) {
        mPathToPic = pathToPic;
    }

    public List<Ingredient> getIngredientList() {
        return mIngredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        mIngredientList = ingredientList;
    }

    public String getRecipe() {
        return mRecipe;
    }

    public void setRecipe(String recipe) {
        mRecipe = recipe;
    }
    public Meal(){

    }
    public boolean isValid(){
        if(mName != null && !mName.isEmpty()
                && mPathToPic != null && !mPathToPic.isEmpty()
                && mIngredientList != null && !mIngredientList.isEmpty()
                && mRecipe != null && !mRecipe.isEmpty()){
            return true;
        }
        return false;
    }
public ContentValues getContentValues(){
    ContentValues values = new ContentValues();
    if(getId()!= null){
        values.put(DbSchema.MealTable.Cols.MEAL_ID,getId());
    }
    values.put(DbSchema.MealTable.Cols.NAME, getName());
    values.put(DbSchema.MealTable.Cols.PIC_PATH, getPathToPic());
    values.put(DbSchema.MealTable.Cols.RECIPE, getRecipe());
    return values;
}

}
