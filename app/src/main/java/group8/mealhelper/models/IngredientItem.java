package group8.mealhelper.models;

/**
 * Created by thomasdail on 11/8/15.
 */
public class IngredientItem {
    private Ingredient mIngredient;
    private double mAmount = 0;

    public IngredientItem(Ingredient i) {
        mIngredient = i;
        mAmount = i.getAmount();
    }

    public void incrementAmount(double d) {
        mAmount += d;
    }

    public String getName() {
        return mIngredient.getName();
    }

    public String getUnits() {
        return mIngredient.getUnits();
    }

    public double getAmount() {
        return mAmount;
    }
}
