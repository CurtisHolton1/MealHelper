package group8.mealhelper.models;

/**
 * Created by curtis on 10/16/15.
 */
public class Ingredient {
    private String mName;
    private double mAmount;
    private String mUnits;

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
}
