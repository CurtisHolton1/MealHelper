package group8.mealhelper.models;

import android.content.Context;

/**
 * Created by curtis on 10/16/15.
 */
public class CookBook {
    private static CookBook sCookBook;
    
    public  static CookBook get(Context context){
        if(sCookBook == null) {
            sCookBook = new CookBook(context);
        }
        return  sCookBook;
    }

    private CookBook(Context context){

    }
}
