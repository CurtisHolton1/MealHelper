package group8.mealhelper.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import group8.mealhelper.database.DbHelper;

/**
 * Created by curtis on 10/16/15.
 */
public class CookBook {
    private static CookBook sCookBook;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public  static CookBook get(Context context){
        if(sCookBook == null) {
            sCookBook = new CookBook(context);
        }
        return  sCookBook;
    }

    private CookBook(Context context){
        context.getApplicationContext();
        mDatabase = new DbHelper(mContext)
                .getWritableDatabase();

    }

    public void addMeal(Meal m){

    }

}
