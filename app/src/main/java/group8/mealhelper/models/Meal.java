package group8.mealhelper.models;
import java.util.List;

/**
 * Created by curtis on 10/16/15.
 */
public class Meal {
    private String mId;
    private String mName;
    private String mPathToPic;
    private List<Ingredient> mIngredientList;

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

    public Meal(){

    }
}
