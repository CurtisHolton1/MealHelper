package group8.mealhelper.models;

import java.io.Serializable;

/**
 * Created by curtis on 11/7/15.
 */
public class Day implements Serializable{
    private String mName;
    private Meal mBreakfast;
    private Meal mLunch;
    private Meal mDinner;
    private String mId;

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

    public Meal getBreakfast() {
        return mBreakfast;
    }

    public void setBreakfast(Meal breakfast) {
        mBreakfast = breakfast;
    }

    public Meal getLunch() {
        return mLunch;
    }

    public void setLunch(Meal lunch) {
        mLunch = lunch;
    }

    public Meal getDinner() {
        return mDinner;
    }

    public void setDinner(Meal dinner) {
        mDinner = dinner;
    }

    public Day(){

    }


}
