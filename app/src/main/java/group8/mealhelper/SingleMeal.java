package group8.mealhelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by curtis on 11/7/15.
 */
public class SingleMeal extends RelativeLayout {

    public SingleMeal(Context context, AttributeSet attrs){
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.single_meal, this);
    }



}
